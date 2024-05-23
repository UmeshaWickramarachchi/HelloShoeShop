package com.example.SSMS.exception;

import com.example.SSMS.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobleExceptionHandler extends RuntimeException{
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleRecordNotFoundException(RecordNotFoundException ex) {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleAlreadyExistRecordsException(RecordAlreadyExistException ex) {
        return new ErrorResponseDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleUnauthorizedAccessException(UnAuthorizedException ex) {
        return new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerErrorException(UnAuthorizedException ex) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleBadRequestErrorException(BadRequestException ex) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponseDTO handleNoContentErrorException(NoContentException ex) {
        return new ErrorResponseDTO(HttpStatus.NO_CONTENT.value(), ex.getMessage());
    }
}
