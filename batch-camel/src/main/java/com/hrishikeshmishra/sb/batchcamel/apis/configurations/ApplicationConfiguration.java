package com.hrishikeshmishra.sb.batchcamel.apis.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Configuration
@ComponentScan({  "com.hrishikeshmishra.sb.batchcamel.services" })
public class ApplicationConfiguration {

    @Autowired
    private CamelContext camelContext;

    @Bean
    public ProducerTemplate producerTemplate() {
        return camelContext.createProducerTemplate();
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
