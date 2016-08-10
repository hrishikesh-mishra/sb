package com.hrishikeshmishra.springcamel.controllers;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hrishikesh.mishra on 10/08/16.
 */
@RestController
public class CamelRouteController {

    private ProducerTemplate camelProducer;

    @Autowired
    public CamelRouteController(ApplicationContext applicationContext) {
        camelProducer = applicationContext.getBean(ProducerTemplate.class);
    }


    @RequestMapping(path = "/route", method = RequestMethod.GET)
    public ResponseEntity<Object> call(){
        String requestData = "RequestPayload";
        Object response = camelProducer.requestBody("direct://push-to-legacy", requestData);
        return new ResponseEntity<>(response, HttpStatus.OK );
    }

}
