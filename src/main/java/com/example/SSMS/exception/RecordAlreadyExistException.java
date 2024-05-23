package com.example.SSMS.exception;

public class RecordAlreadyExistException extends RuntimeException{
    public RecordAlreadyExistException(String message) {
        super(message);
    }
}
