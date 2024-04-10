package com.kolyo.exchange.app.provider;

import com.kolyo.exchange.app.dto.ConvertDTO;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Primary
@Service
@Slf4j
public class FixerAPIClient implements ExchangeProvider {

    private final String API_ACCESS_KEY = "a417be18a915b0e5791546525611c2fa";

    @Override
    public LatestRateDTO latestRate(String base, String symbols) {
        log.info("ExchangeService-latestRate is called");

        System.out.println("Fixer api");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/latest")
                .queryParam("access_key", API_ACCESS_KEY)
                .queryParam("base", base)
                .queryParam("symbols", symbols);

        return restTemplate.getForObject(builder.toUriString(), LatestRateDTO.class);
    }

    @Override
    public ConvertDTO convert(String from, Double amount, String to) {
        log.info("ExchangeService-convert is called");

        System.out.println("Fixer api");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/convert")
                .queryParam("access_key", API_ACCESS_KEY)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", amount);

        return restTemplate.getForObject(builder.toUriString(), ConvertDTO.class);
    }
}
