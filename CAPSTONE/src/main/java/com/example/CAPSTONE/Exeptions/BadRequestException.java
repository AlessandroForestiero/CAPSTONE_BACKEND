package com.example.CAPSTONE.Exeptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message)
    {
        super(message);
    }
}
