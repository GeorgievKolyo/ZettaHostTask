package com.kolyo.exchange.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class CurrencyConversionRequestDTO {

    private String from;
    private BigDecimal amount;
    private String to;
}
