package com.hrishikeshmishra.bj.steps;

import com.hrishikeshmishra.bj.models.VendorInfo;
import com.hrishikeshmishra.bj.models.VendorRequest;
import com.hrishikeshmishra.bj.models.VendorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.hrishikeshmishra.bj.models.Constants.VENDOR_CONFIRM_URL;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@Component
public class VendorItemProcessor implements ItemProcessor<VendorRequest, VendorInfo> {

    private static final Logger log = LoggerFactory.getLogger(VendorItemProcessor.class);

    private final RestTemplate restTemplate;

    @Autowired
    public VendorItemProcessor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VendorInfo process(VendorRequest vendorRequest) throws Exception {
        VendorResponse vendorResponse = callVendorService(vendorRequest);
        return getVendorInfo(vendorRequest, vendorResponse);
    }

    private VendorResponse callVendorService(VendorRequest vendorRequest) {
        log.info("Processing SR:  {}",  vendorRequest.getSr());
        HttpEntity<Object> vendorEntity = new HttpEntity<>(vendorRequest.getVendor());
        ResponseEntity<VendorResponse> response = restTemplate.exchange(VENDOR_CONFIRM_URL, HttpMethod.POST, vendorEntity, VendorResponse.class);
        log.info("Got Vendor Response for SR: {}, : {}", vendorRequest.getSr(), response.getBody());
        return response.getBody();
    }

    private VendorInfo getVendorInfo(VendorRequest vendorRequest, VendorResponse vendorResponse){
        switch (vendorResponse.getStatus()) {
            case "VNF":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.VNF);
            case "NOT_CHANGED":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.NOT_CHANGED);
            case "CHANGED":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.CHANGED, vendorResponse.getNewVendor());
            default:
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.FAILED, "");
        }
    }
}
