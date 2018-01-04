package com.example.currency.converter.configuration;

import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import com.jlupin.impl.client.util.JLupinClientUtil;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4j2Impl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.common.enums.PortType;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author Piotr Heilman
 */
@Configuration
@ComponentScan("com.example.currency.converter")
public class PortalSpringConfiguration {
    private static final int HOW_OFTEN_CHECKING_SERVER_IN_MILLIS = 5000;
    private static final int REPEATS_AMOUNT = 3;
    private static final int CHANGE_SERVER_INTERVAL_IN_MILLIS = 5000;

    @Bean
    public JLupinDelegator getJLupinDelegator() {
        return JLupinClientUtil.generateInnerMicroserviceLoadBalancerDelegator(
                HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,
                REPEATS_AMOUNT,
                CHANGE_SERVER_INTERVAL_IN_MILLIS,
                PortType.JLRMC
        );
    }

    @Bean
    public JLupinLogger getJLupinLogger() {
        return JLupinLoggerOverLog4j2Impl.getInstance();
    }

    @PreDestroy
    public void destroy() {
        JLupinClientUtil.closeResources();
    }

    @Bean
    public JLupinProxyObjectProducer getCurrencyConverterEURBusinessLogicMicroserviceProxyObjectProducer() {
        return JLupinClientUtil.generateProxyObjectProducer("currency-converter-eur", getJLupinDelegator());
    }

    @Bean
    public JLupinProxyObjectProducer getCurrencyConverterGBPBusinessLogicMicroserviceProxyObjectProducer() {
        return JLupinClientUtil.generateProxyObjectProducer("currency-converter-gbp", getJLupinDelegator());
    }

    @Bean
    public JLupinProxyObjectProducer getCurrencyConverterCHFBusinessLogicMicroserviceProxyObjectProducer() {
        return JLupinClientUtil.generateProxyObjectProducer("currency-converter-chf", getJLupinDelegator());
    }

    @Bean(name = "currencyConverterEURService")
    public CurrencyConverterService getCurrencyConverterEURService() {
        return getCurrencyConverterEURBusinessLogicMicroserviceProxyObjectProducer().produceObject(CurrencyConverterService.class);
    }

    @Bean(name = "currencyConverterGBPService")
    public CurrencyConverterService getCurrencyConverterGBPService() {
        return getCurrencyConverterGBPBusinessLogicMicroserviceProxyObjectProducer().produceObject(CurrencyConverterService.class);
    }

    @Bean(name = "currencyConverterCHFService")
    public CurrencyConverterService getCurrencyConverterCHFService() {
        return getCurrencyConverterCHFBusinessLogicMicroserviceProxyObjectProducer().produceObject(CurrencyConverterService.class);
    }
}

