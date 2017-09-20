package com.example.currency.converter.common.pojo;

/**
 * @author Piotr Heilman
 */
public class CurrencyConversionPair {
    private Currency from;
    private Currency to;

    public CurrencyConversionPair(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyConversionPair pair = (CurrencyConversionPair) o;
        if (from != pair.from) return false;
        return to == pair.to;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}
