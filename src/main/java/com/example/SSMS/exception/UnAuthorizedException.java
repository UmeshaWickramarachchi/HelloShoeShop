package com.example.SSMS.exception;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
