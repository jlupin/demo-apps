package com.example.currency.converter.configuration;

import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import com.jlupin.impl.client.util.JLupinClientUtil;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4jImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.delegator.exception.JLupinDelegatorException;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.common.enums.PortType;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
        JLupinDelegator jLupinDelegator = JLupinClientUtil.generateInnerMicroserviceLoadBalancerDelegator(
                                          HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,REPEATS_AMOUNT,CHANGE_SERVER_INTERVAL_IN_MILLIS, PortType.JLRMC);
        try {
            jLupinDelegator.before();
        } catch (JLupinDelegatorException e) {
           throw new IllegalStateException("can not executing delegator before method caused by:", e);
        }
        return jLupinDelegator;
    }

    @Bean
    public JLupinLogger getJLupinLogger() {
           return JLupinLoggerOverLog4jImpl.getInstance();
    }

    @Bean
    public JLupinProxyObjectProducer getCurrencyConverterBusinessLogicMicroserviceProxyObjectProducer() {
           return JLupinClientUtil.generateProxyObjectProducer("currency-converter", getJLupinDelegator(), getJLupinLogger());
    }

    @Bean(name = "currencyConverterService")
    public CurrencyConverterService getCurrencyConverterService() {
        return getCurrencyConverterBusinessLogicMicroserviceProxyObjectProducer().produceObject(CurrencyConverterService.class);
    }
}

