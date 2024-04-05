package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.ConvertDTO;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.exception.InvalidDataException;
import com.kolyo.exchange.app.model.Transaction;
import com.kolyo.exchange.app.provider.ExchangeProvider;
import com.kolyo.exchange.app.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private ExchangeProvider exchangeProvider;
    private TransactionRepository repository;

    @Override
    public LatestRateDTO exchangeRate(String fromCurrency, String toCurrency) {
        return exchangeProvider.latestRate(toCurrency, fromCurrency);
    }

    @Override
    public Transaction currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency) {
        ConvertDTO convertDTO = exchangeProvider.convert(fromCurrency, amount, toCurrency);

        if (convertDTO.getResult() == null) {
            throw new InvalidDataException("Missing result!");
        }
        Transaction transaction = Transaction.builder()
                .date(convertDTO.getDate())
                .fromCurrency(fromCurrency)
                .amount(amount)
                .toCurrency(toCurrency)
                .result(convertDTO.getResult())
                .build();
        repository.save(transaction);

        return transaction;
    }
}
