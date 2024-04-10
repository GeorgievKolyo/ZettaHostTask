package com.kolyo.exchange.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeRateRequestDTO {

    private String fromCurrency;

    private List<String> toCurrency;
}
