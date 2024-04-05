package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.model.Transaction;

import java.math.BigDecimal;

public interface ExchangeService {

    LatestRateDTO exchangeRate(String fromCurrency, String toCurrency);

    Transaction currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency);
}
