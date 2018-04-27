package com.example.currency.converter.service.impl;

import com.example.currency.converter.bean.exception.UndefinedRatioException;
import com.example.currency.converter.bean.interfaces.ExchangeBean;
import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.common.pojo.in.ConvertIn;
import com.example.currency.converter.common.pojo.out.ConvertOut;
import com.example.currency.converter.service.interfaces.CurrencyConverterChfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
@Api(
        description = "Currency Converter API.",
        consumes = "application/json, application/xml, text/xml",
        produces = "application/json, application/xml"
)
@Service(value = "currencyConverterChfService")
public class CurrencyConverterChfServiceImpl implements CurrencyConverterChfService {
    @Autowired
    private ExchangeBean exchangeBean;

    @Override
    public BigDecimal convert(BigDecimal value, Currency from, Currency to) {
        try {
            final double exchangeRatio = exchangeBean.getRatio(from, to);
            final BigDecimal result = value.multiply(BigDecimal.valueOf(exchangeRatio));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (UndefinedRatioException e) {
            return null;
        }
    }

    @ApiOperation("Convert money.")
    @Override
    public ConvertOut convertMoney(@ApiParam("Value to convert.") ConvertIn in){
        try {
            final double exchangeRatio = exchangeBean.getRatio(in.getFrom(), in.getTo());
            final BigDecimal result = in.getValue().multiply(BigDecimal.valueOf(exchangeRatio));

            final ConvertOut out = new ConvertOut();
            out.setValue(result.setScale(2, BigDecimal.ROUND_HALF_UP));
            out.setCurrency(in.getTo());
            return out;
        } catch (UndefinedRatioException e) {
            throw new IllegalStateException("Rate conversion from " + in.getFrom().toString() + " to " + in.getTo().toString() + " is undefined.");
        }
    }
}