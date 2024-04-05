package com.kolyo.exchange.app.dto;

import lombok.Data;

@Data
public class ExchangeRateRequestDTO {

    private String fromCurrency;

    private String toCurrency;
}
