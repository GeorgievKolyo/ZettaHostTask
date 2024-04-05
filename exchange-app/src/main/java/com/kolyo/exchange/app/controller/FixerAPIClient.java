package com.kolyo.exchange.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class FixerAPIClient {

    private final String API_ACCESS_KEY = "7214e7f0b8555213ee40d66396b4d855";

    private RestTemplate restTemplate;

    public LatestRateDTO latestRate(String base, String symbols) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://data.fixer.io/api/latest")
                .queryParam("access_key", API_ACCESS_KEY)
                .queryParam("base", base)
                .queryParam("symbols", symbols);

        return restTemplate.getForObject(builder.toUriString(), LatestRateDTO.class);
    }

}
