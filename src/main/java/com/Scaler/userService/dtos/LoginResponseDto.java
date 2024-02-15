package com.Scaler.userService.dtos;

import com.Scaler.userService.models.User;
import lombok.Data;

@Data
public class LoginResponseDto {

    private String message;
    private String token;
    private UserDto user;

}
