package com.chabndheanee.gorodapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleObjectAlreadyExistsException(final ObjectAlreadyExistsException e) {
        return Collections.singletonMap("Exception while creating object", e.getMessage());
    }

    @ExceptionHandler(ServiceDeleteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleServiceDeleteException(final ServiceDeleteException e) {
        return Collections.singletonMap("Exception while deleting service", e.getMessage());
    }
}
