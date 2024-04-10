package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.model.TransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeService {

    RateEntity exchangeRate(String fromCurrency, String toCurrency);

    TransactionEntity currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency);

    Optional<TransactionEntity> findTransactionById(Long transactionId);

    List<TransactionEntity> getAllTransactionsByDate(LocalDate date);
}
