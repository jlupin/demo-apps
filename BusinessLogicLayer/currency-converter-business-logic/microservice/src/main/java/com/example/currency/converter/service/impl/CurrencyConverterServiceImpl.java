// BusinessLogicLayer/currency-converter-business-logic/microservice/src/main/java/com/example/currency/converter/service/impl/CurrencyConverterServiceImpl.java
package com.example.currency.converter.service.impl;

import com.example.currency.converter.bean.exception.UndefinedRatioException;
import com.example.currency.converter.bean.interfaces.ExchangeBean;
import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
@Service(value = "currencyConverterService")
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    @Autowired
    private ExchangeBean exchangeBean;

    @Override
    public BigDecimal convert(BigDecimal valueInUSD, Currency to){
        try {
            final double exchangeRatio = exchangeBean.getRatio(Currency.USD, to);
            final BigDecimal result = valueInUSD.multiply(BigDecimal.valueOf(exchangeRatio));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (UndefinedRatioException e) {
            return BigDecimal.valueOf(0);
        }
    }
}