package com.kolyo.exchange.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolyo.exchange.app.dto.*;
import com.kolyo.exchange.app.model.Transaction;
import com.kolyo.exchange.app.service.ExchangeService;
import com.kolyo.exchange.app.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;

    private ObjectMapper objectMapper;

    @GetMapping("/api/exchange-rate")
    public ResponseEntity<ExchangeRateResponseDTO> exchangeRate(@RequestBody ExchangeRateRequestDTO request) {
        Validator.validCurrency(request.getToCurrency());
        Validator.validCurrency(request.getFromCurrency());

        LatestRateDTO response = exchangeService.exchangeRate(request.getToCurrency(), request.getFromCurrency());

        if (response != null) {
            return ResponseEntity.ok(ExchangeRateResponseDTO.builder()
                    .fromCurrency(request.getFromCurrency())
                    .toCurrency(request.getToCurrency())
                    .rates(response.getRates().get(request.getFromCurrency()))
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/api/convert")
    public ResponseEntity<CurrencyConversionResponseDTO> currencyConversion(@RequestBody CurrencyConversionRequestDTO request) {
        Validator.validCurrency(request.getFrom());
        Validator.validCurrency(request.getTo());

        Transaction response = exchangeService.currencyConversion(request.getFrom(), request.getAmount(), request.getTo());
        System.out.println(response.toString());
        return ResponseEntity.ok(CurrencyConversionResponseDTO.builder()
                .id(response.getId())
                .from(request.getFrom())
                .amount(request.getAmount())
                .to(request.getTo())
                .result(response.getResult())
                .build());

    }

    @GetMapping("/api/get/transaction")
    public ResponseEntity<TransactionResponseDTO> findTransactionById(@RequestBody TransactionRequestDTO request) {
        Optional<Transaction> transaction = exchangeService.findTransactionById(request.getTransactionId());

        if (transaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Transaction response = transaction.get();
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
        List<Transaction> transactions = exchangeService.getAllTransactionsByDate(request.getDate());

        List<TransactionResponseDTO> response =  transactions.stream().map(e -> objectMapper.convertValue(e, TransactionResponseDTO.class))
                .toList();

        return ResponseEntity.ok(response);
    }


}
