// BusinessLogicLayer/currency-converter-business-logic/interfaces/src/main/java/com/example/currency/converter/service/interfaces/CurrencyConverterService.java
package com.example.currency.converter.service.interfaces;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.service.pojo.ConvertRequest;
import com.example.currency.converter.service.pojo.ConvertResponse;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public interface CurrencyConverterService {
    BigDecimal convert(BigDecimal valueInUSD, Currency to);

    ConvertResponse convertMoney(ConvertRequest in);
}