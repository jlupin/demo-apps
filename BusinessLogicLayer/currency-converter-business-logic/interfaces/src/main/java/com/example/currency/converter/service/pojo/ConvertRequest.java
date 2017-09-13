package com.example.currency.converter.service.pojo;

import com.example.currency.converter.common.pojo.Currency;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class ConvertRequest implements Serializable {
    private BigDecimal valueInUsd;
    private Currency desiredCurrency;

    public BigDecimal getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(BigDecimal valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    public Currency getDesiredCurrency() {
        return desiredCurrency;
    }

    public void setDesiredCurrency(Currency desiredCurrency) {
        this.desiredCurrency = desiredCurrency;
    }
}
