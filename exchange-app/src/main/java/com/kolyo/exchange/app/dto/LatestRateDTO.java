package com.kolyo.exchange.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@ToString
@Builder
public class LatestRateDTO {

    private boolean success;
    private Long timestamp;
    private String base;
    private Date date;
    private Map<String, BigDecimal> rates;

}
