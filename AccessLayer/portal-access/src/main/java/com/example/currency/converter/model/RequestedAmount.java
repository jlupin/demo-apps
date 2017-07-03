// AccessLayer/portal-access/src/main/java/com/example/currency/converter/model/RequestedAmount.java
package com.example.currency.converter.model;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class RequestedAmount {
    private BigDecimal usd;

    public RequestedAmount() {
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getUsd() {
        return usd;
    }
}
