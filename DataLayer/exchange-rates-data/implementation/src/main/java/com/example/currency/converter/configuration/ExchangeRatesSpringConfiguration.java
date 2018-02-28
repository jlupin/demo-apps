package com.example.currency.converter.configuration;

import com.jlupin.impl.client.util.JLupinClientUtil;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.common.enums.PortType;
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
public class ExchangeRatesSpringConfiguration {
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

    @PreDestroy
    public void destroy() {
        JLupinClientUtil.closeResources();
    }

    // @Bean
    // public JLupinProxyObjectProducer getExampleMicroserviceProxyObjectProducer() {
    //        return JLupinClientUtil.generateProxyObjectProducer("example-microservice", getJLupinDelegator());
    // }

    // @Bean(name = "exampleService")
    // public ExampleService getExampleService() {
    //     return getExampleMicroserviceProxyObjectProducer().produceObject(ExampleService.class);
    // }

    @Bean(name = "jLupinRegularExpressionToRemotelyEnabled")
    public List getRemotelyBeanList() {
        List<String> list = new ArrayList<>();
        list.add("exchangeRatesService");
        // list.add("<REMOTE_SERVICE_NAME>");
        return list;
    }
}

