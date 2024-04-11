package com.kolyo.exchange.app.exception;

public class ExchangeNotFoundException extends RuntimeException {

    public ExchangeNotFoundException() {
    }

    public ExchangeNotFoundException(String message) {
        super(message);
    }

    public ExchangeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
