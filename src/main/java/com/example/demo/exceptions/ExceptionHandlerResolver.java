package com.example.demo.exceptions;

import com.fasterxml.jackson.core.JacksonException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandlerResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData onArticleNotFoundException(ArticleNotFoundException exception) {
        return new ExceptionData(exception.getClass().getSimpleName(), exception.getMessage());
    }

    @ExceptionHandler(JacksonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData onJacksonException(JacksonException exception) {
        return new ExceptionData(exception.getClass().getName(), exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData onIOException(IOException exception) {
        return new ExceptionData(exception.getClass().getName(), exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData onRuntimeException(RuntimeException exception) {
        return new ExceptionData(exception.getClass().getName(), exception.getMessage());
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionData {
        private String exceptionType;
        private String exceptionMsg;

    }
}
