package com.Scaler.userService.Exceptions;

public class UserAlreadyPresentException extends Exception{
    public UserAlreadyPresentException(String message) {
        super(message);
    }
}
