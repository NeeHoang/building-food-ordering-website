package com.food.exception;

public class WebException extends RuntimeException{

    public WebException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {return errorCode;}

    public void setErrorCode(ErrorCode errorCode) {this.errorCode = errorCode;}
}

