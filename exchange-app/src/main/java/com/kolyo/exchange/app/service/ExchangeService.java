package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.model.Transaction;
import org.springframework.expression.spel.ast.OpAnd;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeService {

    LatestRateDTO exchangeRate(String fromCurrency, String toCurrency);

    Transaction currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency);

    Optional<Transaction> findTransactionById(Long transactionId);
}
