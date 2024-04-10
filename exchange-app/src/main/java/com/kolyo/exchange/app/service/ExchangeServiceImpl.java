package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.model.TransactionEntity;
import com.kolyo.exchange.app.provider.ExchangeProvider;
import com.kolyo.exchange.app.repository.RateRepository;
import com.kolyo.exchange.app.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {

    private ExchangeProvider exchangeProvider;
    private TransactionRepository transactionRepository;
    private RateRepository rateRepository;

    @Override
    public RateEntity exchangeRate(String fromCurrency, String toCurrency) {

        Optional<RateEntity> rateEntity = rateRepository
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency);

        if (rateEntity.isPresent()) {
            log.info("Rte from DB");
            return rateEntity.get();
        }
        LatestRateDTO latestRateDTO = exchangeProvider.latestRate(fromCurrency, toCurrency);

        if (!latestRateDTO.isSuccess()) {
            throw new RuntimeException("rest problem");
        }
        RateEntity result = RateEntity.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .date(LocalDate.now())
                .rate(latestRateDTO.getRates().get(toCurrency))
                .build();
        return rateRepository.save(result);
    }

    @Override
    public TransactionEntity currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency) {
//        ConvertDTO convertDTO = exchangeProvider.convert(fromCurrency, amount, toCurrency);

        RateEntity rate = exchangeRate(fromCurrency,toCurrency);
        BigDecimal convertedAmount = rate.getRate().multiply(amount);

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .date(LocalDate.now())
                .fromCurrency(fromCurrency)
                .amount(amount)
                .toCurrency(toCurrency)
                .result(convertedAmount)
                .build();
        transactionRepository.save(transactionEntity);

        return transactionEntity;
    }

    @Override
    public Optional<TransactionEntity> findTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public List<TransactionEntity> getAllTransactionsByDate(LocalDate date) {
        return transactionRepository.findAllByDate(date);
    }

}
