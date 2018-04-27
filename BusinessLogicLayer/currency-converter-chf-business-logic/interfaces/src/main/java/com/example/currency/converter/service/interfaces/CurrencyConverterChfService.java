// BusinessLogicLayer/currency-converter-business-logic/interfaces/src/main/java/com/example/currency/converter/service/interfaces/CurrencyConverterService.java
package com.example.currency.converter.service.interfaces;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.common.pojo.in.ConvertIn;
import com.example.currency.converter.common.pojo.out.ConvertOut;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public interface CurrencyConverterChfService {
    BigDecimal convert(BigDecimal value, Currency from, Currency to);

    ConvertOut convertMoney(ConvertIn in);
}