package com.kolyo.exchange.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolyo.exchange.app.config.RateLimited;
import com.kolyo.exchange.app.dto.*;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.service.ExchangeService;
import com.kolyo.exchange.app.util.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;

    private ObjectMapper objectMapper;

    @Operation(summary = "Get a Exchange Rate", description = "Returns a exchange rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The rate was not found")
    })
    @RateLimited
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

    @Operation(summary = "Currency Conversion", description = "Returns converted amount in the target currency and a unique transaction\n" +
            "identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The rate was not found")
    })
    @RateLimited
    @PostMapping("/api/convert")
    public ResponseEntity<TransactionResponseDTO> currencyConversion(@RequestParam(name = "fromCurrency",required = false) String fromCurrency,
                                                                            @RequestParam(name = "amount",required = false) BigDecimal amount,
                                                                            @RequestParam(name = "toCurrency",required = false) String toCurrency) {
        Validator.validCurrency(fromCurrency);
        Validator.validCurrency(toCurrency);
        Validator.validAmount(amount);

       return ResponseEntity.ok(exchangeService.currencyConversion(fromCurrency, amount, toCurrency));
    }

    @Operation(summary = "Get a transaction by id", description = "Returns a transaction with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @RateLimited
    @GetMapping("/api/get/transaction")
    public ResponseEntity<TransactionResponseDTO> findTransactionById(@RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO transaction = exchangeService.findTransactionById(request.getTransactionId());

        return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Get a transaction by date", description = "Returns a transaction from date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The transaction was not found")
    })
    @RateLimited
    @GetMapping("/api/all-from-date")
    public ResponseEntity<List<TransactionResponseDTO>> findAllByDate(@RequestBody TransactionsByDateRequestDTO request) {
        List<TransactionResponseDTO> transactionEntities = exchangeService.getAllTransactionsByDate(request.getDate());

        return ResponseEntity.ok(transactionEntities);
    }

}
