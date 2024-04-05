package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.controller.ConvertDTO;
import com.kolyo.exchange.app.controller.CurrencyConversionResponse;
import com.kolyo.exchange.app.controller.LatestRateDTO;
import com.kolyo.exchange.app.controller.Transaction;

import java.math.BigDecimal;

public interface ExchangeService {

    LatestRateDTO exchangeRate(String fromCurrency, String toCurrency);

    Transaction currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency);
}
