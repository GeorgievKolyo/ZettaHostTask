package com.kolyo.exchange.app.controller;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class CurrencyConversionRequest {

    private String from;
    private BigDecimal amount;
    private String to;
}
