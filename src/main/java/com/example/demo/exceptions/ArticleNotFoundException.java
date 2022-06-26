package com.example.demo.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super();
    }
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
