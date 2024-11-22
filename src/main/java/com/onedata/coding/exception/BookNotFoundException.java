package com.onedata.coding.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException (String message) {
        super(message);
    }
}
