package com.hrishikeshmishra.bj.steps;

import com.hrishikeshmishra.bj.models.VendorInfo;
import com.hrishikeshmishra.bj.models.VendorRequest;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.hrishikeshmishra.bj.models.Constants.VENDOR_URL;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
public class VendorItemProcessor implements ItemProcessor<VendorRequest, VendorInfo> {



    private final RestTemplate restTemplate;

    public VendorItemProcessor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public VendorInfo process(VendorRequest vendorRequest) throws Exception {
        HttpEntity<Object> vendorEntity = new HttpEntity<>(vendorRequest.getVendor());
        ResponseEntity<String> response = restTemplate.exchange(VENDOR_URL, HttpMethod.POST, vendorEntity , String.class);

        switch (response.getBody()){
            case "VNF":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.VNF, "TestVend");
            case "NOT_CHANGED":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.NOT_CHANGED, "TestVend");
            case "CHANGED":
                return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.CHANGED, "TestVend");
        }

        return new VendorInfo(vendorRequest.getSr(), VendorInfo.VendorStatus.FAILED, "");
    }
}
