package com.kolyo.exchange.app.util;

import com.kolyo.exchange.app.exception.InvalidCurrencyException;
import com.kolyo.exchange.app.exception.InvalidDataException;
import com.kolyo.exchange.app.model.CurrencyEnum;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Objects.isNull;

public class Validator {

    public static void validCurrency(String currency) {
        if (isNull(currency)) {
            throw new InvalidCurrencyException("Currency is null");
        }
        if (Arrays.stream(CurrencyEnum.values()).noneMatch(e -> e.equals(CurrencyEnum.convert(currency)))) {
            throw new InvalidCurrencyException("Invalid currency: " + currency);
        }
    }

    public static void validAmount(BigDecimal amount) {
        if (isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDataException("The amount is invalid!");
        }
    }
}
