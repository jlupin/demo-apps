package com.example.currency.converter.controller;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.controller.in.ConvertIn;
import com.example.currency.converter.controller.out.ConvertOut;
import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import com.jlupin.impl.util.JLupinUtil;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Heilman
 */
@RestController
public class ConverterController {
    @Autowired
    @Qualifier("currencyConverterEURService")
    private CurrencyConverterService currencyConverterEURService;
    @Autowired
    @Qualifier("currencyConverterGBPService")
    private CurrencyConverterService currencyConverterGBPService;
    @Autowired
    @Qualifier("currencyConverterCHFService")
    private CurrencyConverterService currencyConverterCHFService;
    @Autowired
    private JLupinLogger jLupinLogger;

    @CrossOrigin
    @PostMapping("/convert")
    public List<ConvertOut> convert(@RequestBody ConvertIn convertIn) {
        final List<ConvertOut> result = new ArrayList<>();

        for (final Currency currency : Currency.values()) {
            if (currency.equals(convertIn.getCurrency())) {
                continue;
            }

            final ConvertOut subResult = new ConvertOut();
            subResult.setCurrency(currency);
            BigDecimal convertedAmount = null;
            try {
                switch (currency) {
                    case EUR:
                        convertedAmount = currencyConverterEURService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                    case GBP:
                        convertedAmount = currencyConverterGBPService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                    case CHF:
                        convertedAmount = currencyConverterCHFService.convert(convertIn.getValue(), convertIn.getCurrency(), currency);
                        break;
                }
            } catch (Throwable th) {
                jLupinLogger.error("an error occurred caused by:" + JLupinUtil.getHighestMessageFromThrowable(th), th);
            }
            subResult.setValue(convertedAmount);

            result.add(subResult);
        }

        return result;
    }
}
