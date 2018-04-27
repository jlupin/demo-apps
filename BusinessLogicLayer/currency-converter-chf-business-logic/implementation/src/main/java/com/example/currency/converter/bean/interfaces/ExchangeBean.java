// BusinessLogicLayer/currency-converter-business-logic/microservice/src/main/java/com/example/currency/converter/bean/interfaces/ExchangeBean.java
package com.example.currency.converter.bean.interfaces;

import com.example.currency.converter.bean.exception.UndefinedRatioException;
import com.example.currency.converter.common.pojo.Currency;

/**
 * @author Piotr Heilman
 */
public interface ExchangeBean {
    double getRatio(Currency from, Currency to) throws UndefinedRatioException;
}