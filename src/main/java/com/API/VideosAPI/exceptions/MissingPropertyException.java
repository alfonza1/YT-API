package com.API.VideosAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingPropertyException extends RuntimeException {


    @Serial
    private static final long serialVersionUUID = 1L;


    public MissingPropertyException() {}
    public MissingPropertyException(String message) {
        super(message);
    }
    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}