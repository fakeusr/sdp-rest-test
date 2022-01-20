package com.globallogic.vehicle.registry.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RegistryExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RegistryException.class)
    public ResponseEntity<ErrorResponse> handleRegistryException(RegistryException ex, WebRequest request) {
        ErrorResponse errorMessage = new ErrorResponse();
        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setError(ex.getMessage());
        final ResponseStatus[] annotationsByType = ex.getClass().getAnnotationsByType(ResponseStatus.class);
        return new ResponseEntity<>(errorMessage, annotationsByType[0].value());
    }
}