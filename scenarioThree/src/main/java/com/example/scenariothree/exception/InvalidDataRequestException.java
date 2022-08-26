package com.example.scenariothree.exception;

public class InvalidDataRequestException extends RuntimeException{
    public InvalidDataRequestException(String description) {
        super(description);
    }
}
