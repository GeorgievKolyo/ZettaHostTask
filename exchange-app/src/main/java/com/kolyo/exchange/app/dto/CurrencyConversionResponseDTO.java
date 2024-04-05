package com.kolyo.exchange.app.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyConversionResponseDTO {

    private Long id;
    private String from;
    private BigDecimal amount;
    private String to;

    private BigDecimal result;
}
