package com.kolyo.exchange.app.provider;

import com.kolyo.exchange.app.controller.ConvertDTO;
import com.kolyo.exchange.app.controller.LatestRateDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

//@Primary
@Service
public class FixerAPIClient implements ExchangeProvider {

    private final String API_ACCESS_KEY = "7214e7f0b8555213ee40d66396b4d855";

    @Override
    public LatestRateDTO latestRate(String base, String symbols) {

        System.out.println("Fixer api");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/latest")
                .queryParam("access_key", API_ACCESS_KEY)
                .queryParam("base", base)
                .queryParam("symbols", symbols);

        return restTemplate.getForObject(builder.toUriString(), LatestRateDTO.class);
    }

    @Override
    public ConvertDTO convert(String from, BigDecimal amount, String to) {

        System.out.println("Fixer api");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/convert")
                .queryParam("access_key", API_ACCESS_KEY)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", amount);

        return restTemplate.getForObject(builder.toUriString(), ConvertDTO.class);
    }
}
