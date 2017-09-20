package com.example.currency.converter.service.pojo.out;

import com.example.currency.converter.common.pojo.Currency;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class ConvertOut {
    private BigDecimal value;
    private Currency currency;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
