package com.kolyo.exchange.app.controller;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRateResponse {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rates;
}
