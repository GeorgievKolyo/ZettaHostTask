package com.kolyo.exchange.app.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Data
@ToString
public class ConvertDTO {

    private boolean success;

    private Map<String, String> query;

    private Map<String, String> info;

    private LocalDate date;

    private Double result;
}
