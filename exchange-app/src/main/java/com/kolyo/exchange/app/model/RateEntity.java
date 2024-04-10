package com.kolyo.exchange.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rates")
@Builder
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "'id'")
    private Long id;

    @Column(name = "'created_at'")
    private LocalDate date;

    @Column(name = "'from'")
    private String fromCurrency;

    @Column(name = "'to'")
    private String toCurrency;

    @Column(name = "'rate'")
    private BigDecimal rate;
}
