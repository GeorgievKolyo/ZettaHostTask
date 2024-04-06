package com.kolyo.exchange.app.controller;

import com.kolyo.exchange.app.dto.*;
import com.kolyo.exchange.app.exception.InvalidCurrencyException;
import com.kolyo.exchange.app.model.Transaction;
import com.kolyo.exchange.app.service.ExchangeService;
import com.kolyo.exchange.app.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExchangeController {


    private ExchangeService exchangeService;

    @GetMapping("api/exchange-rate")
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


    @GetMapping("api/convert")
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

}
