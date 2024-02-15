package com.Scaler.userService.services;

import com.Scaler.userService.Exceptions.UserAlreadyPresentException;
import com.Scaler.userService.Exceptions.UserNotFoundException;
import com.Scaler.userService.dtos.LoginResponseDto;
import com.Scaler.userService.dtos.UserDto;
import com.Scaler.userService.models.Session;
import com.Scaler.userService.models.SessionStatus;
import com.Scaler.userService.models.User;
import com.Scaler.userService.repositories.SessionRepository;
import com.Scaler.userService.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;

    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        secretKey = Jwts.SIG.HS256.key().build();
    }

    public UserDto signUp(String email, String password) throws UserAlreadyPresentException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyPresentException("User is already present");
        }
        User user = new User();
        user.setEmail(email);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public LoginResponseDto login(String email, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("email id is not valid");
        }
        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("username/password does not match");
        }

        // RandomStringUtils.randomAlphabetic(30);
        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("email", email);
        jwtData.put("createdAt", new Date());
        jwtData.put("expiryAt", new Date(System.currentTimeMillis() + 1000 * 60 * 5));

        String jwtToken = Jwts.builder()
                .header().keyId("aKeyId").and()
                .claims(jwtData)
                .signWith(secretKey)
                .compact();


        Session session = new Session();
        session.setToken(jwtToken);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,5);
        session.setExpiryAt(instance.getTime());
        Session savedSession = sessionRepository.save(session);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setMessage("User successfully logged in");
        loginResponseDto.setToken(savedSession.getToken());

        User user1 = savedSession.getUser();
        UserDto userDto = UserDto.from(user1);
        loginResponseDto.setUser(userDto);
        return loginResponseDto;
    }

    public SessionStatus validateToken(Long userId, String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (optionalSession.isEmpty()) {
            return SessionStatus.ENDED;
        }
        Session session = optionalSession.get();
        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return SessionStatus.ENDED;
        }

        String email = (String) claimsJws.getPayload().get("email");
        Long expiryAt = (Long) claimsJws.getPayload().get("expiryAt");

        if (session.getExpiryAt().before(new Date())) {
            session.setSessionStatus(SessionStatus.ENDED);
            Session savedSession = sessionRepository.save(session);
            return savedSession.getSessionStatus();
        }

        return SessionStatus.ACTIVE;
    }

    public void logout(String token, Long userId) {

        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token, userId);
        if (optionalSession.isEmpty()) {
            throw new RuntimeException("No session available");
        }
        Session session = optionalSession.get();
        if (session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            session.setSessionStatus(SessionStatus.ENDED);
        } else {
            throw new RuntimeException("No session available");
        }
        sessionRepository.save(session);
    }
}
