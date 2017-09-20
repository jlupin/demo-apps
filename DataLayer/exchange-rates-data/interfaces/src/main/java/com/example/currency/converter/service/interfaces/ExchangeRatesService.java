package com.example.currency.converter.service.interfaces;

import com.example.currency.converter.common.pojo.Currency;

import java.math.BigDecimal;

/**
 * Created by Piotr Heilman on 2017-09-19.
 */
public interface ExchangeRatesService {
    BigDecimal getRate(Currency from, Currency to);
}