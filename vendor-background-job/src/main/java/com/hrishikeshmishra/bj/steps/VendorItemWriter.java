package com.hrishikeshmishra.bj.steps;

import com.hrishikeshmishra.bj.models.VendorInfo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

import static com.hrishikeshmishra.bj.models.Constants.QUEUE_NAME;
import static com.hrishikeshmishra.bj.models.Constants.VENDOR_URL;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
public class VendorItemWriter implements ItemWriter<VendorInfo> {

    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;


    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;


    public VendorItemWriter(RestTemplate restTemplate, StringRedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(List<? extends VendorInfo> items) throws Exception {

        for (VendorInfo vendorInfo: items){
            process(vendorInfo);
        }
    }

    private void process(VendorInfo vendorInfo) {
        listOps.leftPush(QUEUE_NAME, vendorInfo.getServiceRequestId());
        if(vendorInfo.getVendorStatus() == VendorInfo.VendorStatus.VNF)
            sendVNF(vendorInfo);
        else if(vendorInfo.getVendorStatus() == VendorInfo.VendorStatus.CHANGED)
            sendChangedVendor(vendorInfo);


    }

    private void sendChangedVendor(VendorInfo vendorInfo) {
        HttpEntity<Object> vendorEntity = new HttpEntity<>(vendorInfo.getServiceRequestId());
        ResponseEntity<String> response = restTemplate.exchange(VENDOR_URL, HttpMethod.POST, vendorEntity , String.class);

    }

    private void sendVNF(VendorInfo vendorInfo) {
        HttpEntity<Object> vendorEntity = new HttpEntity<>(vendorInfo.getServiceRequestId());
        ResponseEntity<String> response = restTemplate.exchange(VENDOR_URL, HttpMethod.POST, vendorEntity , String.class);

    }
}
