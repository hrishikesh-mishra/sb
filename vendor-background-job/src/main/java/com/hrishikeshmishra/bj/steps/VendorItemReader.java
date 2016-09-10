package com.hrishikeshmishra.bj.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrishikeshmishra.bj.models.Constants;
import com.hrishikeshmishra.bj.models.Vendor;
import com.hrishikeshmishra.bj.models.VendorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.hrishikeshmishra.bj.models.Constants.QUEUE_NAME;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@Component
public class VendorItemReader implements ItemReader<VendorRequest> {

    private static final Logger log = LoggerFactory.getLogger(VendorItemReader.class);

    private ObjectMapper objectMapper;
    private StringRedisTemplate redisTemplate;


    @Autowired
    public VendorItemReader(StringRedisTemplate  redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public VendorRequest read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("Starting Job ... : ");
        String sr = getSR();
        if (Objects.isNull(sr)) return null;
        log.info("Read SR:  {}", sr);
        return new VendorRequest(sr, getVendor(sr));
    }

    private String getSR(){
        ListOperations<String, String> listOps = this.redisTemplate.opsForList();
        return (listOps.size(QUEUE_NAME) == 0) ? null : listOps.rightPop(QUEUE_NAME);
    }

    private Vendor getVendor(String sr) throws IOException {
        ValueOperations<String, String> valueOps = this.redisTemplate.opsForValue();
        return  objectMapper.readValue(valueOps.get((sr.concat(Constants.DETAIL))), Vendor.class);
    }
}
