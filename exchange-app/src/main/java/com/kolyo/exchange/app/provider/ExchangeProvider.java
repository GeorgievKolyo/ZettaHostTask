package com.kolyo.exchange.app.provider;

import com.kolyo.exchange.app.dto.ConvertDTO;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public interface ExchangeProvider {

    RestTemplate restTemplate = new RestTemplate();

    LatestRateDTO latestRate(String base, String symbols);

    ConvertDTO convert(String from, BigDecimal amount, String to);
}
