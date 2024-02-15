package com.Scaler.userService.controllers;

import com.Scaler.userService.Exceptions.UserAlreadyPresentException;
import com.Scaler.userService.Exceptions.UserNotFoundException;
import com.Scaler.userService.dtos.*;
import com.Scaler.userService.models.Session;
import com.Scaler.userService.models.SessionStatus;
import com.Scaler.userService.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyPresentException {
       UserDto userDto= authService.signUp(request.getEmail(),request.getPassword());

       return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws UserNotFoundException {
       LoginResponseDto loginResponseDto= authService.login(request.getEmail(),request.getPassword());
        MultiValueMap<String,String> headers= new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,"auth-token: "+loginResponseDto.getToken());


       return new ResponseEntity<>(loginResponseDto,headers,HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
      SessionStatus sessionStatus= authService.validateToken(request.getUserId(),request.getToken());
      return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto request){
        authService.logout(request.getToken(),request.getUserId());
        return new ResponseEntity<>(new String("Logged Out Successfully"),HttpStatus.OK);
    }
}
