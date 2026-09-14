package com.ignacio.weatherhistory.exception;

public class PaisNotFoundException extends RuntimeException {
    public PaisNotFoundException(String message) {
        super(message);
    }
}
