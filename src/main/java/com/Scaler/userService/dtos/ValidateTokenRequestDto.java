package com.Scaler.userService.dtos;

import lombok.Data;

@Data
public class ValidateTokenRequestDto {
    private Long userId;
    private String token;
}
