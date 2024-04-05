package com.kolyo.exchange.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "'id'")
    private Long id;

    @Column(name = "'created_at'")
    private Date date;

    @Column(name = "'from'")
    private String fromCurrency;

    @Column(name = "'amount'")
    private BigDecimal amount;

    @Column(name = "'to'")
    private String toCurrency;

    @Column(name = "'result'")
    private BigDecimal result;
}
