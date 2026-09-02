package com.ignacio.weatherhistory.exception;

import com.ignacio.weatherhistory.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClimaExceptionHandler {

    @ExceptionHandler(CiudadNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> manejarCiudadNoEncontrada(CiudadNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), "Not Found"));
    }
}