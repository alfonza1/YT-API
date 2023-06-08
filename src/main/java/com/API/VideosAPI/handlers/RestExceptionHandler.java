package com.API.VideosAPI.handlers;

import com.API.VideosAPI.errors.ErrorDetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.API.VideosAPI.errors.ValidationError;
import com.API.VideosAPI.exceptions.MissingPropertyException;
import com.API.VideosAPI.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus((short)HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(resourceNotFoundException.getMessage());
        errorDetail.setDeveloperMessage(resourceNotFoundException.getClass().getName());
        return (new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MissingPropertyException.class)
    public ResponseEntity<?> handleUnrecognizedPropertyException(MissingPropertyException missingPropertyException) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus((short)HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Missing Property");
        errorDetail.setDetail(missingPropertyException.getMessage());
        errorDetail.setDeveloperMessage(missingPropertyException.getClass().getName());
        return (new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus((short)HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail(methodArgumentNotValidException.getMessage());
        errorDetail.setDeveloperMessage(methodArgumentNotValidException.getClass().getName());
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            List<ValidationError> validationErrors = errorDetail.getErrors().get(fieldError.getField());
            if (validationErrors == null) {
                validationErrors = new ArrayList<>();
                errorDetail.getErrors().put(fieldError.getField(), validationErrors);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(this.messageSource.getMessage(fieldError, null));
            validationErrors.add(validationError);
        }
        return super.handleMethodArgumentNotValid(methodArgumentNotValidException, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException httpMessageNotReadableException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus((short)status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDeveloperMessage(httpMessageNotReadableException.getClass().getName());
        return super.handleExceptionInternal(httpMessageNotReadableException, errorDetail, headers, status, request);
    }
}
