package com.kolyo.exchange.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class ExchangeController {

    private FixerAPIClient fixerAPIClient;

    @GetMapping("api/exchange-rate")
    public ResponseEntity<ExchangeRateResponse> getExchangeRate(@RequestBody ExchangeRateRequest request) {
        LatestRateDTO response = fixerAPIClient.latestRate(request.getToCurrency(), request.getFromCurrency());

        //TODO validate request
        //TODO add exception
        if (response != null) {
            return ResponseEntity.ok(ExchangeRateResponse.builder()
                            .fromCurrency(request.getFromCurrency())
                            .toCurrency(request.getToCurrency())
                            .rates(response.getRates().get(request.getFromCurrency()))
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
