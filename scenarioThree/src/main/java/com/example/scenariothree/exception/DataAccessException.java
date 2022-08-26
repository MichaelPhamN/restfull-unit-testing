package com.example.scenariothree.exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String description) {
        super(description);
    }
}
