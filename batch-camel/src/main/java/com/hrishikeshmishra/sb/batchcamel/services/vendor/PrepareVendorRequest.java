package com.hrishikeshmishra.sb.batchcamel.services.vendor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants;
import com.hrishikeshmishra.sb.batchcamel.apis.models.SRDetail;
import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorProcessorEntity;
import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component("prepareVendorRequest")
@Slf4j
public class PrepareVendorRequest implements Processor {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public PrepareVendorRequest( StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Long startTime = System.currentTimeMillis();
        SRDetail srDetail = (SRDetail) exchange.getIn().getBody();
        log.info("[PrepareVendorRequest] process sr : {}", srDetail);
        VendorRequest request = getVendor(srDetail);
        exchange.getIn().setBody(new VendorProcessorEntity(srDetail, request));
        Long endTime = System.currentTimeMillis();
        log.info("[PrepareVendorRequest] Process in {} ms", (endTime - startTime));
    }

    private VendorRequest getVendor(SRDetail srDetail) throws IOException {
        ValueOperations<String, String> valueOps = this.redisTemplate.opsForValue();
        return  objectMapper.readValue(valueOps.get((srDetail.getSrId().concat(Constants.DETAIL))), VendorRequest.class);
    }
}
