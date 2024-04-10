package com.kolyo.exchange.app.controller;


import com.kolyo.exchange.app.exception.InvalidCurrencyException;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;


    @Test
    void exchangeRate_should_return_ExchangeRateResponseDTO() throws Exception {

        // Mocked data
        String fromCurrency = "EUR";
        String toCurrency = "GBP";

        // Mocked RateEntity
        RateEntity entity = RateEntity.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .date(LocalDate.now())
                .rate(BigDecimal.valueOf(0.856859))
                .build();

        // Mock the exchangeService.exchangeRate() method
        when(exchangeService.exchangeRate(fromCurrency, toCurrency)).thenReturn(entity);

        // Perform the GET request

        mockMvc.perform(get("/api/exchange-rate")
                        .param("fromCurrency", fromCurrency)
                        .param("toCurrency", toCurrency)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency", is(fromCurrency)))
                .andExpect(jsonPath("$.toCurrency", is(toCurrency)))
                .andExpect(jsonPath("$.rates", is(0.856859)));

    }

    @Test
    void exchangeRate_should_return_exception_when_fromCurrency_is_invalid() throws Exception {

        String fromCurrency = "aaaa";

        mockMvc.perform(get("/api/exchange-rate")
                            .param("fromCurrency", fromCurrency)
                            .param("toCurrency", "EUR")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertInstanceOf(InvalidCurrencyException.class, result.getResolvedException()))
                    .andExpect(result -> assertEquals("Invalid currency: " + fromCurrency, Objects.requireNonNull(result.getResolvedException()).getMessage()));;

    }

    @Test
    void exchangeRate_should_return_exception_when_fromCurrency_is_null() throws Exception {

        String fromCurrency = null;
        mockMvc.perform(get("/api/exchange-rate")
                        .param("fromCurrency", fromCurrency)
                        .param("toCurrency", "EUR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(InvalidCurrencyException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Currency is null", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }



}
