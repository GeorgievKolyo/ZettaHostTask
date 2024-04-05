package com.kolyo.exchange.app.controller;

import com.kolyo.exchange.app.dto.*;
import com.kolyo.exchange.app.model.Transaction;
import com.kolyo.exchange.app.service.ExchangeService;
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
        LatestRateDTO response = exchangeService.exchangeRate(request.getToCurrency(), request.getFromCurrency());

        //TODO validate request
        //TODO add exception
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
        Transaction response = exchangeService.currencyConversion(request.getFrom(),request.getAmount(), request.getTo());
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
