package com.onedata.coding.exception;

public class MemberBorrowLimitExceedException extends RuntimeException{
    public MemberBorrowLimitExceedException(String message){
        super(message);
    }
}
