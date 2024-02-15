package com.Scaler.userService.services;

import com.Scaler.userService.Exceptions.UserNotFoundException;
import com.Scaler.userService.dtos.SetUserRolesDto;
import com.Scaler.userService.dtos.UserDto;
import com.Scaler.userService.models.Role;
import com.Scaler.userService.models.User;
import com.Scaler.userService.repositories.RoleRepository;
import com.Scaler.userService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
    }

    public UserDto getUserDetails(Long userId) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id "+userId+" is not available");
        }
        User user= userOptional.get();
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
    @Transactional
    public UserDto setUserRoles(Long userId, List<Long> roleIds) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);


        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id "+userId+" is not available");
        }

        User user= userOptional.get();
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}
