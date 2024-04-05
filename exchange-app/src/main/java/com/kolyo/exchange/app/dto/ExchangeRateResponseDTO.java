package com.kolyo.exchange.app.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRateResponseDTO {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rates;
}
