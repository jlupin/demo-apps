// BusinessLogicLayer/currency-converter-business-logic/microservice/src/main/java/com/example/currency/converter/bean/impl/ExchangeBeanImpl.java
package com.example.currency.converter.bean.impl;

import com.example.currency.converter.bean.exception.UndefinedRatioException;
import com.example.currency.converter.bean.interfaces.ExchangeBean;
import com.example.currency.converter.common.pojo.Currency;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Piotr Heilman
 */
@Service(value = "exchangeBean")
public class ExchangeBeanImpl implements ExchangeBean {
    private Random random;
    private Map<Pair, Double> rates;

    public ExchangeBeanImpl() {
        random = new Random();
        rates = new HashMap<>();
        initRates();
    }

    private void initRates() {
        rates.put(new Pair(Currency.USD, Currency.EUR), 0.89389);
        rates.put(new Pair(Currency.EUR, Currency.USD), 1.11871);

        rates.put(new Pair(Currency.USD, Currency.GBP), 0.78139);
        rates.put(new Pair(Currency.GBP, Currency.USD), 1.27978);

        rates.put(new Pair(Currency.EUR, Currency.GBP), 0.87416);
        rates.put(new Pair(Currency.GBP, Currency.EUR), 1.14396);
    }

    @Override
    public double getRatio(Currency from, Currency to) throws UndefinedRatioException {
        final Double rate = rates.get(new Pair(from, to));
        if (rate == null) {
            throw new UndefinedRatioException();
        }

        return rate;
    }

    private class Pair {
        private Currency from;
        private Currency to;

        Pair(Currency from, Currency to) {
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

            Pair pair = (Pair) o;

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
}