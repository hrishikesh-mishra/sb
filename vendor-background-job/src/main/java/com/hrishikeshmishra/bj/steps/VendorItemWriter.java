package com.hrishikeshmishra.bj.steps;

import com.hrishikeshmishra.bj.models.VNFRequest;
import com.hrishikeshmishra.bj.models.VendorChangeRequest;
import com.hrishikeshmishra.bj.models.VendorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.hrishikeshmishra.bj.models.Constants.*;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@Component
public class VendorItemWriter implements ItemWriter<VendorInfo> {

    private static final Logger log = LoggerFactory.getLogger(VendorItemWriter.class);

    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;


    @Autowired
    public VendorItemWriter(RestTemplate restTemplate,  StringRedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(List<? extends VendorInfo> items) throws Exception {

        for (VendorInfo vendorInfo: items){
            log.info("Writing SR:  {}",  vendorInfo.getServiceRequestId());
            process(vendorInfo);
        }
    }

    private void process(VendorInfo vendorInfo) {
        writeToRedis(vendorInfo);
        if(vendorInfo.getVendorStatus() == VendorInfo.VendorStatus.VNF)
            sendVNF(vendorInfo);
        else if(vendorInfo.getVendorStatus() == VendorInfo.VendorStatus.CHANGED)
            sendChangedVendor(vendorInfo);
    }

    private void writeToRedis(VendorInfo vendorInfo) {
        ListOperations<String, String> listOps = this.redisTemplate.opsForList();
        listOps.leftPush(QUEUE_NAME, vendorInfo.getServiceRequestId());
        log.info("Writing SR To Redis : {}",  vendorInfo.getServiceRequestId());
    }

    private void sendChangedVendor(VendorInfo vendorInfo) {
        log.info("Informing Orchestrator for vendor change for SR : {}",  vendorInfo.getServiceRequestId());
        VendorChangeRequest request = new VendorChangeRequest(vendorInfo.getServiceRequestId(), vendorInfo.getCurrentVendor());
        HttpEntity<VendorChangeRequest> vendorEntity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(ORCHESTRATOR_VENDOR_CHANGE_URL, HttpMethod.POST, vendorEntity , String.class);
        log.info("Orchestrator response for vendor change for SR : {}, {}",  vendorInfo.getServiceRequestId(), response.getBody());
    }

    private void sendVNF(VendorInfo vendorInfo) {
        log.info("Informing Orchestrator for VNF for SR : {}",  vendorInfo.getServiceRequestId());
        VNFRequest vnfRequest = new VNFRequest(vendorInfo.getServiceRequestId());
        HttpEntity<VNFRequest> vendorEntity = new HttpEntity<>(vnfRequest);
        ResponseEntity<String> response = restTemplate.exchange(ORCHESTRATOR_VENDOR_VNF_URL, HttpMethod.POST, vendorEntity , String.class);
        log.info("Orchestrator response for VNF for SR : {}, {}",  vendorInfo.getServiceRequestId(), response.getBody());
    }
}
