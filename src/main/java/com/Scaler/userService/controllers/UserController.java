package com.Scaler.userService.controllers;

import com.Scaler.userService.Exceptions.UserNotFoundException;
import com.Scaler.userService.dtos.SetUserRolesDto;
import com.Scaler.userService.dtos.UserDto;
import com.Scaler.userService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable ("id") Long userId) throws UserNotFoundException {
       UserDto userDto=userService.getUserDetails(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesDto request) throws UserNotFoundException {
           UserDto userDto= userService.setUserRoles(userId,request.getRoleIds());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }


}
