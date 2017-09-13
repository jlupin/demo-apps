package com.example.currency.converter;

import com.example.currency.converter.configuration.PortalSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Piotr Heilman
 */

@SpringBootApplication
public class SpringBootApplicationStarter {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PortalSpringConfiguration.class, args);
    }
}

