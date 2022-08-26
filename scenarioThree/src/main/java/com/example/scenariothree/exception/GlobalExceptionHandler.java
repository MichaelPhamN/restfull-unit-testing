package com.example.scenariothree.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(code = HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> DataNotFoundException(DataNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(InvalidDataRequestException.class)
    public ResponseEntity<ErrorMessage> InvalidDataRequestException(InvalidDataRequestException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorMessage> DataAccessException(DataAccessException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
