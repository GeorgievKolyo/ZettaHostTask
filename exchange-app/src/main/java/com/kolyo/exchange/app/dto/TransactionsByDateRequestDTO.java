package com.kolyo.exchange.app.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TransactionsByDateRequestDTO {

    private LocalDate date;
}
