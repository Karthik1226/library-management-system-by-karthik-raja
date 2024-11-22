package com.onedata.coding.exception;

public class BookBorrowNotAvailableException extends RuntimeException{
    public BookBorrowNotAvailableException(String message){
        super(message);
    }
}
