package com.hrishikeshmishra.springcamel.transformer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by hrishikesh.mishra on 10/08/16.
 */
@Component("requestSender")
public class RequestSender implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String requestData = (String) exchange.getIn().getBody();
        requestData += ", Transformed@" + getClass().getSimpleName();
        exchange.getOut().setBody(requestData);
    }
}
