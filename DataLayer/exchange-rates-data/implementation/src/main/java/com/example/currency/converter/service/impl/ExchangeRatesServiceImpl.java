package com.example.currency.converter.service.impl;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.common.pojo.CurrencyConversionPair;
import com.example.currency.converter.service.interfaces.ExchangeRatesService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Piotr Heilman on 2017-09-19.
 */
@Service(value = "exchangeRatesService")
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private final String EXCHANGE_RATES_PROPERTIES_FILE_NAME = "exchange_rates.properties";
    private Map<CurrencyConversionPair, BigDecimal> rates;

    public ExchangeRatesServiceImpl() {
        rates = new HashMap<>();
        initRates();
    }

    private void initRates() {
        final File root = new File(".");
        final String propertiesFilePath = root.getAbsolutePath() + File.separator + EXCHANGE_RATES_PROPERTIES_FILE_NAME;
        final FileInputStream propertiesFileInputStream;
        try {
            propertiesFileInputStream = new FileInputStream(propertiesFilePath);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Cannot find " + propertiesFilePath + " file.", e);
        }

        final Properties properties = new Properties();
        try {
            properties.load(propertiesFileInputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Error while loading properties from file " + propertiesFilePath + ".", e);
        }

        for (final Currency from : Currency.values()) {
            for (final Currency to : Currency.values()) {
                if (from.equals(to)) {
                    continue;
                }

                final String rate = properties.getProperty(from.toString() + "_" + to.toString());
                if (rate == null) {
                    throw new IllegalStateException("Rate conversion from " + from.toString() + " to " + to.toString() + " is undefined.");
                }

                final Double parsedRate;
                try {
                    parsedRate = Double.parseDouble(rate);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Rate conversion is not a valid double value.", e);
                }

                rates.put(new CurrencyConversionPair(from, to), BigDecimal.valueOf(parsedRate));
            }
        }
    }

    @Override
    public BigDecimal getRate(Currency from, Currency to) {
        return rates.get(new CurrencyConversionPair(from, to));
    }
}