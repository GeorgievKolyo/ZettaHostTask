package com.kolyo.exchange.app.service;

import com.kolyo.exchange.app.dto.ExchangeRateResponseDTO;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import com.kolyo.exchange.app.exception.ExchangeNotFoundException;
import com.kolyo.exchange.app.model.RateEntity;
import com.kolyo.exchange.app.provider.ExchangeProvider;
import com.kolyo.exchange.app.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    @Mock
    private RateRepository rateRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ExchangeProvider exchangeProvider;

    @InjectMocks
    private ExchangeServiceImpl rateService;

    @Test
    void when_exchangeRate_return_rate_from_api() {
        // Mocked data
        String fromCurrency = "EUR";
        String toCurrency = "GBP";
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(toCurrency, new BigDecimal("0.856859"));

        // Mocked RateEntity
        RateEntity mockedRateEntity = RateEntity.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .date(LocalDate.now())
                .rate(BigDecimal.valueOf(0.856859))
                .build();

        RateEntity expected = RateEntity.builder()
                .id(mockedRateEntity.getId())
                .fromCurrency(mockedRateEntity.getFromCurrency())
                .toCurrency(mockedRateEntity.getToCurrency())
                .date(mockedRateEntity.getDate())
                .rate(mockedRateEntity.getRate())
                .build();

        // Mock API response
        LatestRateDTO mockedLatestRateDTO = LatestRateDTO.builder()
                .success(true)
                .timestamp(23121323L)
                .base(fromCurrency)
                .rates(rates)
                .date(new Date())
                .build();

        // Mock repository behavior
        when(rateRepository
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency))
                .thenReturn(Optional.empty()); // Return null to simulate repository not finding the entity

        when(exchangeProvider.latestRate(fromCurrency, toCurrency)).thenReturn(mockedLatestRateDTO);

        // Mock rateRepository.save() behavior
        when(rateRepository.save(any(RateEntity.class))).thenReturn(mockedRateEntity);

        // Call the method
        RateEntity result = rateService.exchangeRate(fromCurrency, toCurrency);

        // Verify the result
        assertThat(result.getFromCurrency()).isEqualTo(expected.getFromCurrency());
        assertThat(result.getDate()).isEqualTo(expected.getDate());
        assertThat(result.getToCurrency()).isEqualTo(expected.getToCurrency());

        // Verify repository method was called
        verify(rateRepository, times(1))
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency);
        verify(rateRepository, times(1)).save(any(RateEntity.class));

        // Verify restTemplate.exchange was called
        verify(exchangeProvider, times(1)).latestRate(fromCurrency, toCurrency);
    }


    @Test
    void when_exchangeRate_return_rate_from_db() {
        // Mocked data
        String fromCurrency = "EUR";
        String toCurrency = "GBP";

        // Mocked RateEntity
        RateEntity mockedRateEntity = RateEntity.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .date(LocalDate.now())
                .rate(BigDecimal.valueOf(0.856859))
                .build();

        RateEntity expected = RateEntity.builder()
                .id(mockedRateEntity.getId())
                .fromCurrency(mockedRateEntity.getFromCurrency())
                .toCurrency(mockedRateEntity.getToCurrency())
                .date(mockedRateEntity.getDate())
                .rate(mockedRateEntity.getRate())
                .build();

        // Mock repository behavior
        when(rateRepository
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency))
                .thenReturn(Optional.of(mockedRateEntity));

        // Call the method
        RateEntity result = rateService.exchangeRate(fromCurrency, toCurrency);

        // Verify the result
        assertThat(result.getFromCurrency()).isEqualTo(expected.getFromCurrency());
        assertThat(result.getDate()).isEqualTo(expected.getDate());
        assertThat(result.getToCurrency()).isEqualTo(expected.getToCurrency());

        // Verify repository method was called
        verify(rateRepository, times(1))
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency);

        // The exchangeProvider.latestRate method won't be run because the rateRepository.findRateEntitiesByDateAndFromCurrencyAndToCurrency return the mockedRateEntity
        verify(restTemplate, times(0)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ExchangeRateResponseDTO.class)
        );
    }

    @Test
    void when_exchangeRate_return_api_exception() {
        // Mocked data
        String fromCurrency = "EUR";
        String toCurrency = "GBP";
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(toCurrency, new BigDecimal("0.856859"));

        // Mocked RateEntity
        RateEntity mockedRateEntity = RateEntity.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .date(LocalDate.now())
                .rate(BigDecimal.valueOf(0.856859))
                .build();

        RateEntity expected = RateEntity.builder()
                .id(mockedRateEntity.getId())
                .fromCurrency(mockedRateEntity.getFromCurrency())
                .toCurrency(mockedRateEntity.getToCurrency())
                .date(mockedRateEntity.getDate())
                .rate(mockedRateEntity.getRate())
                .build();

        // Mock API response
        LatestRateDTO mockedLatestRateDTO = LatestRateDTO.builder()
                .success(false)
                .timestamp(23121323L)
                .base(fromCurrency)
                .rates(rates)
                .date(new Date())
                .build();

        // Mock repository behavior
        when(rateRepository
                .findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate.now(), fromCurrency, toCurrency))
                .thenReturn(Optional.empty()); // Return null to simulate repository not finding the entity

        when(exchangeProvider.latestRate(fromCurrency, toCurrency)).thenReturn(mockedLatestRateDTO);

        // Verify the result
        assertThat(mockedLatestRateDTO.isSuccess()).isEqualTo(false);

        assertThatThrownBy(() -> rateService.exchangeRate(fromCurrency, toCurrency))
                .isInstanceOf(ExchangeNotFoundException.class)
                .hasMessage("rest problem");


    }
}
