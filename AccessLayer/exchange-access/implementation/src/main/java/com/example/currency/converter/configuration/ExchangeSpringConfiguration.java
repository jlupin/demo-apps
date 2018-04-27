package com.example.currency.converter.configuration;

import com.example.currency.converter.service.interfaces.CurrencyConverterChfService;
import com.example.currency.converter.service.interfaces.CurrencyConverterEurService;
import com.example.currency.converter.service.interfaces.CurrencyConverterGbpService;
import com.jlupin.impl.client.util.JLupinClientUtil;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.common.enums.PortType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Piotr Heilman
 */
@Configuration
@ComponentScan({
        "com.example.currency.converter",
        "com.jlupin.servlet.monitor.configuration"
})
public class ExchangeSpringConfiguration {
    @Bean
    public JLupinDelegator getJLupinDelegator() {
        return JLupinClientUtil.generateInnerMicroserviceLoadBalancerDelegator(PortType.JLRMC);
    }

    @Bean(name = "currencyConverterEurService")
    public CurrencyConverterEurService getCurrencyConverterEurService() {
        return JLupinClientUtil.generateRemote(getJLupinDelegator(), "currency-converter-eur", CurrencyConverterEurService.class);
    }

    @Bean(name = "currencyConverterGbpService")
    public CurrencyConverterGbpService getCurrencyConverterGbpService() {
        return JLupinClientUtil.generateRemote(getJLupinDelegator(), "currency-converter-gbp", CurrencyConverterGbpService.class);
    }

    @Bean(name = "currencyConverterChfService")
    public CurrencyConverterChfService getCurrencyConverterChfService() {
        return JLupinClientUtil.generateRemote(getJLupinDelegator(), "currency-converter-chf", CurrencyConverterChfService.class);
    }
}

