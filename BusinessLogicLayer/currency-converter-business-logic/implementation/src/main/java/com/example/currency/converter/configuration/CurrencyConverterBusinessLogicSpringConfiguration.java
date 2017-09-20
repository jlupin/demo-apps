package com.example.currency.converter.configuration;

import com.example.currency.converter.service.interfaces.ExchangeRatesService;
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

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Heilman
 */
@Configuration
@ComponentScan("com.example.currency.converter")
public class CurrencyConverterBusinessLogicSpringConfiguration {
    private static final int HOW_OFTEN_CHECKING_SERVER_IN_MILLIS = 5000;
    private static final int REPEATS_AMOUNT = 3;
    private static final int CHANGE_SERVER_INTERVAL_IN_MILLIS = 5000;

    @Bean
    public JLupinDelegator getJLupinDelegator() {
        final JLupinDelegator jLupinDelegator = JLupinClientUtil.generateInnerMicroserviceLoadBalancerDelegator(
                HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,
                REPEATS_AMOUNT,
                CHANGE_SERVER_INTERVAL_IN_MILLIS,
                PortType.JLRMC
        );

        try {
            jLupinDelegator.before();
        } catch (JLupinDelegatorException e) {
            throw new IllegalStateException("can not execute delegator's before method caused by:", e);
        }
        return jLupinDelegator;
    }

    @PreDestroy
    public void destroy() {
        try {
            getJLupinDelegator().after();
        } catch (JLupinDelegatorException e) {
            throw new IllegalStateException("can not execute delegator's after method caused by:", e);
        }
        JLupinClientUtil.closeResources();
    }

    @Bean
    public JLupinLogger getJLupinLogger() {
        return JLupinLoggerOverLog4jImpl.getInstance();
    }

    // @Bean
    // public JLupinProxyObjectProducer getExampleMicroserviceProxyObjectProducer() {
    //        return JLupinClientUtil.generateProxyObjectProducer("example-microservice", getJLupinDelegator(), getJLupinLogger());
    // }

    // @Bean(name = "exampleService")
    // public ExampleService getExampleService() {
    //     return getExampleMicroserviceProxyObjectProducer().produceObject(ExampleService.class);
    // }

    @Bean
    public JLupinProxyObjectProducer getExampleMicroserviceProxyObjectProducer() {
        return JLupinClientUtil.generateProxyObjectProducer("exchange-rates", getJLupinDelegator(), getJLupinLogger());
    }

    @Bean(name = "exchangeRatesService")
    public ExchangeRatesService getExampleService() {
        return getExampleMicroserviceProxyObjectProducer().produceObject(ExchangeRatesService.class);
    }

    @Bean(name = "jLupinRegularExpressionToRemotelyEnabled")
    public List getRemotelyBeanList() {
        List<String> list = new ArrayList<>();
        list.add("currencyConverterService");
        return list;
    }
}

