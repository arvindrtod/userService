package com.Scaler.userService.dtos;

import lombok.Data;

@Data
public class LogoutRequestDto {
    private String token;
    private Long userId;
}
