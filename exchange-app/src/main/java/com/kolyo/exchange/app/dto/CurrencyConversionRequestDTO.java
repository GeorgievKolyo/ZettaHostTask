package com.kolyo.exchange.app.dto;

import lombok.Data;

@Data

public class CurrencyConversionRequestDTO {

    private String from;

    private Double amount;

    private String to;
}
