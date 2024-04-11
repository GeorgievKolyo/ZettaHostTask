package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.TransactionResponseDTO;
import com.kolyo.exchange.app.model.RateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {

    RateEntity exchangeRate(String fromCurrency, String toCurrency);

    TransactionResponseDTO currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency);

    TransactionResponseDTO findTransactionById(Long transactionId);

    List<TransactionResponseDTO> getAllTransactionsByDate(LocalDate date);
}
