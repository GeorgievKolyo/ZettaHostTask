package com.kolyo.exchange.app.util;

import com.kolyo.exchange.app.exception.InvalidCurrencyException;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Validator {

    public static void validCurrency(String currency) {
        if (!getAllCurrencies().stream().anyMatch(e -> e.getCurrencyCode().equals(currency))) {
            throw new InvalidCurrencyException(currency + " is not valid!");
        }
    }

    public static Set<Currency> getAllCurrencies()
    {
        Set<Currency> toret = new HashSet<>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {
            try {
                Currency currency = Currency.getInstance( loc );

                if ( currency != null ) {
                    toret.add( currency );
                }
            } catch(Exception exc)
            {

            }
        }

        return toret;
    }
}
