package com.kolyo.exchange.app.provider;

import com.kolyo.exchange.app.dto.ConvertDTO;
import com.kolyo.exchange.app.dto.LatestRateDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Primary
@Service
public class CurrencyBeaconAPIClient implements ExchangeProvider {

    private final String API_ACCESS_KEY = "cTJoBUR40uwSdtJouvZDTFyehTmxQgww";

    @Override
    public LatestRateDTO latestRate(String base, String symbols) {
        System.out.println("CurrencyBeacon api");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.currencybeacon.com/v1/latest")
                .queryParam("api_key", API_ACCESS_KEY)
                .queryParam("base", base)
                .queryParam("symbols", symbols);

        return restTemplate.getForObject(builder.toUriString(), LatestRateDTO.class);    }

    @Override
    public ConvertDTO convert(String from, BigDecimal amount, String to) {
        System.out.println("CurrencyBeacon api");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.currencybeacon.com/v1/convert")
                .queryParam("api_key", API_ACCESS_KEY)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", amount);

        return restTemplate.getForObject(builder.toUriString(), ConvertDTO.class);    }
}
