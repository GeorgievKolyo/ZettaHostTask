package com.kolyo.exchange.app.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TransactionResponseDTO {

    private Long id;

    private Date date;

    private String fromCurrency;

    private BigDecimal amount;

    private String toCurrency;

    private BigDecimal result;
}
