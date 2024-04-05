package com.kolyo.exchange.app.controller;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@ToString
public class ConvertDTO {

    private boolean success;

    private Map<String, String> query;
    private Map<String, String> info;

    private Date date;

    private BigDecimal result;
}
