package com.kolyo.exchange.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.dto.TransactionResponseDTO;
import com.kolyo.exchange.app.exception.ExchangeNotFoundException;
import com.kolyo.exchange.app.exception.TransactionNotFoundException;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.model.TransactionEntity;
import com.kolyo.exchange.app.provider.ExchangeProvider;
import com.kolyo.exchange.app.repository.RateRepository;
import com.kolyo.exchange.app.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"exchanges"})
@AllArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {

    private ExchangeProvider exchangeProvider;
    private TransactionRepository transactionRepository;
    private RateRepository rateRepository;
    private ObjectMapper objectMapper;

    @CachePut(key = "#result.id")
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
            throw new ExchangeNotFoundException("rest problem");
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
    public TransactionResponseDTO currencyConversion(String fromCurrency, BigDecimal amount, String toCurrency) {
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

        return TransactionResponseDTO.builder()
                .id(transactionEntity.getId())
                .date(transactionEntity.getDate())
                .fromCurrency(transactionEntity.getFromCurrency())
                .amount(transactionEntity.getAmount())
                .toCurrency(transactionEntity.getToCurrency())
                .result(transactionEntity.getResult())
                .build();
    }

    @CachePut(key = "#result.id")
    @Override
    public TransactionResponseDTO findTransactionById(Long transactionId) {
        Optional<TransactionEntity> entity = transactionRepository.findById(transactionId);

        if (entity.isEmpty()) {
            throw new TransactionNotFoundException("transaction with id " + transactionId + " not found");
        }
        return TransactionResponseDTO.builder()
                .id(entity.get().getId())
                .date(entity.get().getDate())
                .fromCurrency(entity.get().getFromCurrency())
                .amount(entity.get().getAmount())
                .toCurrency(entity.get().getToCurrency())
                .result(entity.get().getResult())
                .build();
    }

    @Override
    public List<TransactionResponseDTO> getAllTransactionsByDate(LocalDate date) {
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByDate(date);
        if (transactionEntities.isEmpty()) {
            throw new TransactionNotFoundException("transaction from date " + date.toString() + " not found");
        }
        return transactionEntities.stream()
                .map(e -> objectMapper.convertValue(e, TransactionResponseDTO.class))
                .toList();
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "${exchange-api.cache-ttl}")
    public void clearCache(){
        log.info("Caches are cleared");
    }

}
