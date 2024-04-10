package com.kolyo.exchange.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateResponseDTO {

    private String fromCurrency;

    private String toCurrency;

    private Double rates;
}
