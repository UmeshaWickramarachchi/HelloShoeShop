package com.example.SSMS.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String message) {
        super(message);
    }
}
