package com.example.currency.converter.configuration;

import com.jlupin.common.container.JLupinCommonProgrammingContainer;
import com.jlupin.impl.balancer.ext.impl.roundrobin.JLupinRoundRobinLoadBalancerImpl;
import com.jlupin.impl.balancer.structures.property.JLupinLoadBalancerProperties;
import com.jlupin.impl.balancer.structures.wrapper.JLupinLoadBalancerPropertiesWrapper;
import com.jlupin.impl.balancer.type.JLupinBalancerType;
import com.jlupin.impl.client.delegator.balance.JLupinRMCLoadBalancerDelegatorImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.delegator.exception.JLupinDelegatorException;
import com.jlupin.interfaces.container.system.JLupinSystemContainer;
import com.jlupin.interfaces.logger.JLupinLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
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

    private final JLupinLogger jLupinLogger;
    private final JLupinDelegator jLupinDelegator;

    public CurrencyConverterBusinessLogicSpringConfiguration() {
        final JLupinSystemContainer jLupinSystemContainer = JLupinSystemContainer.getInstance();
        final JLupinCommonProgrammingContainer jLupinCommonProgrammingContainer = JLupinCommonProgrammingContainer.getInstance();

        jLupinLogger = jLupinSystemContainer.getJLupinLogger();

        final JLupinLoadBalancerProperties jLupinLoadBalancerProperties = new JLupinLoadBalancerProperties();
        jLupinLoadBalancerProperties.setJLupinMainServerInZoneConfigurations(
                jLupinCommonProgrammingContainer.getJLupinMainServersInZone()
        );

        final JLupinLoadBalancerPropertiesWrapper jLupinLoadBalancerPropertiesWrapper = new JLupinLoadBalancerPropertiesWrapper();
        jLupinLoadBalancerPropertiesWrapper.setJLupinLoadBalancerProperties(jLupinLoadBalancerProperties);

        JLupinRoundRobinLoadBalancerImpl jLupinLoadBalancer = new JLupinRoundRobinLoadBalancerImpl(
                jLupinLogger,
                HOW_OFTEN_CHECKING_SERVER_IN_MILLIS,
                REPEATS_AMOUNT,
                CHANGE_SERVER_INTERVAL_IN_MILLIS
        );
        jLupinLoadBalancer.setJLupinBalancerType(JLupinBalancerType.INNER_MICROSERVICE);
        jLupinLoadBalancer.setJLupinLoadBalancerPropertiesWrapper(jLupinLoadBalancerPropertiesWrapper);

        jLupinDelegator = new JLupinRMCLoadBalancerDelegatorImpl(jLupinLoadBalancer, jLupinSystemContainer.getJLupinSerializer());
    }

    @PostConstruct
    public void startLoadBalancingDelegator() throws JLupinDelegatorException {
        jLupinDelegator.before();
    }

    // @Bean(name = "exampleService")
    // public ExampleService getExampleService() {
    //     JLupinProxyObjectProducer objectProducer =
    //             new JLupinSeleuciaProxyObjectSupportsExceptionProducerImpl("example-microservice", jLupinDelegator, jLupinLogger);
    //
    //     return objectProducer.produceObject(ExampleService.class);
    // }

    @Bean(name = "jLupinRegularExpressionToRemotelyEnabled")
    public List getRemotelyBeanList() {
        List<String> list = new ArrayList<>();
        list.add("currencyConverterService");
        // list.add("<REMOTE_SERVICE_NAME>");
        return list;
    }
}

