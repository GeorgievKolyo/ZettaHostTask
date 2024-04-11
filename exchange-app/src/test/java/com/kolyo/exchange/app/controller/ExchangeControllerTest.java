package com.kolyo.exchange.app.controller;

import com.kolyo.exchange.app.dto.TransactionResponseDTO;
import com.kolyo.exchange.app.exception.InvalidCurrencyException;
import com.kolyo.exchange.app.exception.InvalidDataException;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.model.TransactionEntity;
import com.kolyo.exchange.app.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    //exchangeRate

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

    //currencyConversion
    @Test
    public void currencyConversion() throws Exception {

        TransactionEntity transaction = TransactionEntity.builder()
                .date(LocalDate.now())
                .fromCurrency("USD")
                .amount(BigDecimal.valueOf(100))
                .toCurrency("EUR")
                .result(BigDecimal.valueOf(89))
                .build();

        // Mock the response from the ExchangeService
        TransactionResponseDTO responseDTO = TransactionResponseDTO.builder()
                .id(1L)
                .date(LocalDate.now())
                .fromCurrency("USD")
                .amount(BigDecimal.valueOf(100.0))
                .toCurrency("EUR")
                .result(BigDecimal.valueOf(890))
                .build();

        when(exchangeService.currencyConversion("USD", BigDecimal.valueOf(100.0), "EUR"))
                .thenReturn(responseDTO);


        // Perform the GET request
        mockMvc.perform(post("/api/convert")
                        .param("fromCurrency", "USD")
                        .param("amount", "100.0")
                        .param("toCurrency", "EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.fromCurrency", is("USD")))
                .andExpect(jsonPath("$.amount", is(100.0)))
                .andExpect(jsonPath("$.toCurrency",is("EUR")))
                .andExpect(jsonPath("$.result", is(890)));
    }

    @Test
    void convert_should_return_exception_when_fromCurrency_is_null() throws Exception {

        String fromCurrency = null;
        mockMvc.perform(post("/api/convert")
                        .param("fromCurrency", fromCurrency)
                        .param("amount", "100.0")
                        .param("toCurrency", "EUR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(InvalidCurrencyException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Currency is null", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

    @Test
    void convert_should_return_exception_when_toCurrency_is_null() throws Exception {

        String toCurrency = null;
        mockMvc.perform(post("/api/convert")
                        .param("fromCurrency", "EUR")
                        .param("amount", "100.0")
                        .param("toCurrency", toCurrency)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(InvalidCurrencyException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Currency is null", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }

    @Test
    void convert_should_return_exception_when_amount_is_invalid() throws Exception {


        mockMvc.perform(post("/api/convert")
                        .param("fromCurrency", "EUR")
                        .param("amount", "0")
                        .param("toCurrency", "USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(result -> assertInstanceOf(InvalidDataException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("The amount is invalid!", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }


}
