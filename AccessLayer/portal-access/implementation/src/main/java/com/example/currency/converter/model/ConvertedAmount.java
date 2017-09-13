// AccessLayer/portal-access/src/main/java/com/example/currency/converter/model/ConvertedAmount.java
package com.example.currency.converter.model;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class ConvertedAmount {
    private BigDecimal usd;
    private BigDecimal eur;
    private BigDecimal gbp;

    public ConvertedAmount(BigDecimal usd, BigDecimal eur, BigDecimal gbp) {
        this.usd = usd;
        this.eur = eur;
        this.gbp = gbp;
    }


    public BigDecimal getUsd() {
        return usd;
    }

    public BigDecimal getEur() {
        return eur;
    }

    public BigDecimal getGbp() {
        return gbp;
    }
}
