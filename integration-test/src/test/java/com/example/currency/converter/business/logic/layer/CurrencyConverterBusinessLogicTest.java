package com.example.currency.converter.business.logic.layer;

import com.jlupin.impl.client.delegator.JLupinSocketDelegatorImpl;
import com.jlupin.impl.logger.impl.log4j.JLupinLoggerOverLog4jImpl;
import com.jlupin.impl.serialize.JLupinFSTSerializerImpl;
import com.jlupin.interfaces.client.delegator.JLupinDelegator;
import com.jlupin.impl.client.seleucia.proxy.producer.ext.JLupinSeleuciaProxyObjectSupportsExceptionProducerImpl;
import com.jlupin.interfaces.client.proxy.producer.JLupinProxyObjectProducer;
import com.jlupin.interfaces.logger.JLupinLogger;
import com.jlupin.interfaces.serialize.JLupinSerializer;

import java.net.InetSocketAddress;

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
        JLupinSocketDelegatorImpl jLupinDelegator = new JLupinSocketDelegatorImpl(jLupinLogger(), jLupinSerializer());
        jLupinDelegator.setSocketAddressToConnectServer(new InetSocketAddress("localhost", 9090));
        jLupinDelegator.setReadTimeout(30000);

        return jLupinDelegator;
    }

    private JLupinProxyObjectProducer jLupinProxyObjectProducer() {
        return new JLupinSeleuciaProxyObjectSupportsExceptionProducerImpl(
                "currency-converter-business-logic-microservice", jLupinDelegator(), jLupinLogger()
        );
    }

    // Example test
    // @Test
    // public void exampleTest() {
    //     ExampleService service = jLupinProxyObjectProducer().produceObject(ExampleService.class);
    //     assertEquals("2 + 2 must be 4", new Integer(4), service.add(2, 2));
    // }
}