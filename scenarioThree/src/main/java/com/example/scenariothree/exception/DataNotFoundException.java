package com.example.scenariothree.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String description) {
        super(description);
    }
}
