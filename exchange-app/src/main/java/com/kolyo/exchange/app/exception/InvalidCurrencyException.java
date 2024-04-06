package com.kolyo.exchange.app.exception;

public class InvalidCurrencyException extends RuntimeException {

    public InvalidCurrencyException() {
    }

    public InvalidCurrencyException(String message) {
        super(message);
    }

    public InvalidCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
