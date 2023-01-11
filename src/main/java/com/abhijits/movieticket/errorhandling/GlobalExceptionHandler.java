package com.abhijits.movieticket.errorhandling;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by   : Abhijit Singh
 * On           : 27 October, 2022
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        ErrorResponse response = ErrorResponse.createInstance();
        e.getConstraintViolations()
         .forEach(constraintViolation -> response.addMessage(constraintViolation.getPropertyPath().toString() + " : " + constraintViolation.getMessage()));

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.createInstance();

        e.getBindingResult().getFieldErrors()
         .forEach(fieldError -> response.addMessage(fieldError.getField() + " : " + fieldError.getDefaultMessage()));

        return response;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleBindException(BindException e) {
        ErrorResponse response = ErrorResponse.createInstance();

        e.getBindingResult().getFieldErrors()
                 .forEach(fieldError -> response.addMessage(fieldError.getField() + " : " + fieldError.getDefaultMessage()));

        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleException(DataIntegrityViolationException e) {
        ErrorResponse response = ErrorResponse.createInstance();

        response.addMessage(e.getMessage());

        return response;
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleException(RuntimeException e) {
        ErrorResponse response = ErrorResponse.createInstance();

        response.addMessage(e.getMessage());

        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleException(Exception e) {
        ErrorResponse response = ErrorResponse.createInstance();

        response.addMessage(e.getMessage());
//        response.addMessage(getStackTrace(e));

        return response;
    }

    private String getStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
