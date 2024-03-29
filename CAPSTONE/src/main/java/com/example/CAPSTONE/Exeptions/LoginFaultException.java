package com.example.CAPSTONE.Exeptions;

public class LoginFaultException extends RuntimeException {
    public LoginFaultException(String message) {
        super(message);
    }
}
