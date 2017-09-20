package com.example.currency.converter.service.pojo.in;

import com.example.currency.converter.common.pojo.Currency;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class ConvertIn {
    private BigDecimal value;
    private Currency from;
    private Currency to;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }
}
