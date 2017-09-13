package com.example.currency.converter.business.logic.layer;

import com.example.currency.converter.common.pojo.Currency;
import com.example.currency.converter.service.interfaces.CurrencyConverterService;
import com.jlupin.common.communication.common.various.JLupinMainServerInZoneConfiguration;
import com.jlupin.impl.client.util.JLupinClientUtil;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4jImpl;
import com.jlupin.impl.serialize.JLupinFSTSerializerImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.common.enums.PortType;
import com.jlupin.interfaces.logger.JLupinLogger;
import com.jlupin.interfaces.serialize.JLupinSerializer;
import org.junit.Test;
import java.math.BigDecimal;

/**
 * @author Piotr Heilman
 */
public class CurrencyConverterBusinessLogicTest {
    private JLupinLogger jLupinLogger() {
        return JLupinLoggerOverLog4jImpl.getInstance();
    }

    private JLupinSerializer jLupinSerializer() {
        return JLupinFSTSerializerImpl.getInstance();
    }

    private JLupinDelegator jLupinDelegator() {
        final JLupinMainServerInZoneConfiguration[] mainServerInZoneConfigurations = new JLupinMainServerInZoneConfiguration[]{
                new JLupinMainServerInZoneConfiguration("NODE_1", "127.0.0.1", 9090, 9095, 9096, 9097)
        };

        final JLupinDelegator jLupinDelegator = JLupinClientUtil.generateOuterClientLoadBalancerDelegator(
                5000,
                3,
                1000,
                PortType.JLRMC,
                mainServerInZoneConfigurations,
                jLupinLogger(),
                jLupinSerializer()
        );
        return jLupinDelegator;
    }

    private JLupinProxyObjectProducer jLupinProxyObjectProducer() {
        return JLupinClientUtil.generateProxyObjectProducer("currency-converter", jLupinDelegator(), jLupinLogger());
    }

    // Example test
    @Test
    public void exampleTest() {
        CurrencyConverterService currencyConverterService = jLupinProxyObjectProducer().produceObject(CurrencyConverterService.class);
        BigDecimal result = currencyConverterService.convert(new BigDecimal("123.32"), Currency.EUR);
        System.out.println(result);
    }
}