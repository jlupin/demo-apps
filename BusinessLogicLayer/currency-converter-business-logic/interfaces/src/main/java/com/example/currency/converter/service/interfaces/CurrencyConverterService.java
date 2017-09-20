// BusinessLogicLayer/currency-converter-business-logic/interfaces/src/main/java/com/example/currency/converter/service/interfaces/CurrencyConverterService.java
package com.example.currency.converter.service.interfaces;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.service.pojo.in.ConvertIn;
import com.example.currency.converter.service.pojo.out.ConvertOut;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public interface CurrencyConverterService {
    BigDecimal convert(BigDecimal value, Currency from, Currency to);

    ConvertOut convertMoney(ConvertIn in);
}