package com.ignacio.weatherhistory.exception;

public class CiudadNotFoundException extends RuntimeException {
    public CiudadNotFoundException(String message) {
        super(message);
    }
}
