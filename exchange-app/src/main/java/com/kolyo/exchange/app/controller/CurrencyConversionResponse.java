package com.kolyo.exchange.app.controller;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyConversionResponse {

    private Long id;
    private String from;
    private BigDecimal amount;
    private String to;

    private BigDecimal result;
}
