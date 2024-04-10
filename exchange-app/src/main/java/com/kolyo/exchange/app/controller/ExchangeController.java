package com.kolyo.exchange.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolyo.exchange.app.dto.*;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.model.TransactionEntity;
import com.kolyo.exchange.app.service.ExchangeService;
import com.kolyo.exchange.app.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;

    private ObjectMapper objectMapper;

    @GetMapping("/api/exchange-rate")
    public ResponseEntity<ExchangeRateResponseDTO> exchangeRate(@RequestParam(name = "fromCurrency",required = false) String fromCurrency,
                                                                @RequestParam(name = "toCurrency",required = false) String toCurrency) {
        Validator.validCurrency(toCurrency);
        Validator.validCurrency(fromCurrency);

        RateEntity response = exchangeService.exchangeRate(fromCurrency, toCurrency);

        if (response != null) {
            return ResponseEntity.ok(ExchangeRateResponseDTO.builder()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .rates(response.getRate())
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/api/convert")
    public ResponseEntity<CurrencyConversionResponseDTO> currencyConversion(@RequestParam(name = "fromCurrency",required = false) String fromCurrency,
                                                                            @RequestParam(name = "amount",required = false) BigDecimal amount,
                                                                            @RequestParam(name = "toCurrency",required = false) String toCurrency) {
        Validator.validCurrency(fromCurrency);
        Validator.validCurrency(toCurrency);
        Validator.validAmount(amount);

        TransactionEntity response = exchangeService.currencyConversion(fromCurrency, amount, toCurrency);
        return ResponseEntity.ok(CurrencyConversionResponseDTO.builder()
                .id(response.getId())
                .from(fromCurrency)
                .amount(amount)
                .to(toCurrency)
                .result(response.getResult())
                .build());

    }

    @GetMapping("/api/get/transaction")
    public ResponseEntity<TransactionResponseDTO> findTransactionById(@RequestBody TransactionRequestDTO request) {
        Optional<TransactionEntity> transaction = exchangeService.findTransactionById(request.getTransactionId());

        if (transaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        TransactionEntity response = transaction.get();
        return ResponseEntity.ok(TransactionResponseDTO.builder()
                .id(response.getId())
                .date(response.getDate())
                .fromCurrency(response.getFromCurrency())
                .amount(response.getAmount())
                .toCurrency(response.getToCurrency())
                .result(response.getResult())
                .build());
    }

    @GetMapping("/api/all-from-date")
    public ResponseEntity<List<TransactionResponseDTO>> findAllByDate(@RequestBody TransactionsByDateRequestDTO request) {
        List<TransactionEntity> transactionEntities = exchangeService.getAllTransactionsByDate(request.getDate());

        List<TransactionResponseDTO> response =  transactionEntities.stream().map(e -> objectMapper.convertValue(e, TransactionResponseDTO.class))
                .toList();

        return ResponseEntity.ok(response);
    }


}
