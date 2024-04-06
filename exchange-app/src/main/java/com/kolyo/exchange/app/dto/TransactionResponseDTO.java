package com.kolyo.exchange.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private Long id;

    private Date date;

    private String fromCurrency;

    private BigDecimal amount;

    private String toCurrency;

    private BigDecimal result;
}
