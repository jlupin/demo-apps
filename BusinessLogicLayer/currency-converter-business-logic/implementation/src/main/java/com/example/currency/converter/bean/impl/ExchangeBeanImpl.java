package com.example.currency.converter.bean.impl;

import com.example.currency.converter.bean.exception.UndefinedRatioException;
import com.example.currency.converter.bean.interfaces.ExchangeBean;
import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.common.pojo.CurrencyConversionPair;
import com.example.currency.converter.service.interfaces.ExchangeRatesService;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Piotr Heilman
 */
@Service(value = "exchangeBean")
public class ExchangeBeanImpl implements ExchangeBean {
    private final String SAFE_RATES_PROPERTIES_FILE_NAME = "safe_rates.properties";
    private Map<CurrencyConversionPair, Double> safeRates;

    @Autowired
    private ExchangeRatesService exchangeRatesService;
    private JLupinLogger logger;

    public ExchangeBeanImpl(@Autowired JLupinLogger logger) {
        this.safeRates = new HashMap<>();
        this.logger = logger;
        initRates();
    }

    private void initRates() {
        final File root = new File(".");
        final String propertiesFilePath = root.getAbsolutePath() + File.separator + SAFE_RATES_PROPERTIES_FILE_NAME;
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
                    logger.warn("Rate conversion from " + from.toString() + " to " + to.toString() + " is undefined.");
                    continue;
                }

                final Double parsedRate;
                try {
                    parsedRate = Double.parseDouble(rate);
                } catch (NumberFormatException e) {
                    logger.warn("Rate conversion is not a valid double value.", e);
                    continue;
                }

                safeRates.put(new CurrencyConversionPair(from, to), parsedRate);
            }
        }
    }

    @Override
    public double getRatio(Currency from, Currency to) throws UndefinedRatioException {
        BigDecimal exchangeRate = null;
        boolean executedCorrectly = true;
        try {
            exchangeRate = exchangeRatesService.getRate(from, to);
            if (exchangeRate == null) {
                throw new UndefinedRatioException("Exchange rate is undefined on remote microservice (falling back into safe rates).");
            }
        } catch (Throwable th) {
            executedCorrectly = false;
            logger.error("Error while getting exchange rate from remote microservice (falling back into safe rates).", th);
        }

        final Double rate = executedCorrectly ? exchangeRate.doubleValue() : safeRates.get(new CurrencyConversionPair(from, to));
        if (rate == null) {
            throw new UndefinedRatioException();
        }

        return rate;
    }
}