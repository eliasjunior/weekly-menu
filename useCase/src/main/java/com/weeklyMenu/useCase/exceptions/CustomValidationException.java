package com.weeklyMenu.useCase.exceptions;

public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    public CustomValidationException(String message) {
        super(message);
    }
}
