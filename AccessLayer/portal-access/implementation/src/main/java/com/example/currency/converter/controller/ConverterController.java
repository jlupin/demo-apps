// AccessLayer/portal-access/src/main/java/com/example/currency/converter/controller/ConverterController.java
package com.example.currency.converter.controller;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.model.ConvertedAmount;
import com.example.currency.converter.model.RequestedAmount;
import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import com.jlupin.impl.util.JLupinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Piotr Heilman
 */
@RestController
public class ConverterController {
    @Autowired
    private CurrencyConverterService currencyConverterService;

    @PostMapping("/convert")
    public ConvertedAmount convert(@RequestBody RequestedAmount requestedAmount) {
        try {
            return new ConvertedAmount(
                    requestedAmount.getUsd(),
                    currencyConverterService.convert(requestedAmount.getUsd(), Currency.EUR),
                    currencyConverterService.convert(requestedAmount.getUsd(), Currency.GBP)
            );
        } catch (Throwable th) {
            throw new IllegalStateException("an error occurred caused by:" + JLupinUtil.getHighestMessageFromThrowable(th), th);
        }
    }
}
