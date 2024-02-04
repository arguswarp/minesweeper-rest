package com.argus.minesweeperrest.exception;

public class ErrorResponseException extends RuntimeException{
    public ErrorResponseException(String message) {
        super(message);
    }
}
