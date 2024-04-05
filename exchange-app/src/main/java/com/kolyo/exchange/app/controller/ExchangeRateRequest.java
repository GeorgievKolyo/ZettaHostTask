package com.kolyo.exchange.app.controller;

import lombok.Data;

@Data
public class ExchangeRateRequest {

    private String fromCurrency;

    private String toCurrency;
}
