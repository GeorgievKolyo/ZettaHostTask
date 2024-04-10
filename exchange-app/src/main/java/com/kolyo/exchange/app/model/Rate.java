package com.kolyo.exchange.app.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Rate {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal rates;
}
