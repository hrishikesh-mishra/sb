package com.hrishikeshmishra.sb.batchcamel.services.vendor;

import com.hrishikeshmishra.sb.batchcamel.apis.models.SRDetail;
import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorInfo;
import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorProcessorEntity;
import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.hrishikeshmishra.sb.batchcamel.apis.configurations.Constants.VENDOR_CONFIRM_URL;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component("vendorService")

@Slf4j
public class VendorService implements Processor {

    private final RestTemplate restTemplate;

    public VendorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Long startTime = System.currentTimeMillis();
        VendorProcessorEntity entity = (VendorProcessorEntity) exchange.getIn().getBody();
        VendorResponse response = callVendorService(entity);
        VendorInfo vendorInfo = getVendorInfo(entity.getSrDetail(), response);
        exchange.getIn().setHeader("vendorStatus", vendorInfo.getVendorStatus());
        exchange.getIn().setBody(vendorInfo);
        Long endTime = System.currentTimeMillis();
        log.info("[VendorService] Process in {} ms", (endTime - startTime));
    }

    private VendorResponse callVendorService(VendorProcessorEntity entity) {
        log.info("[VendorService] callVendorService SR:  {}",  entity.getSrDetail().getSrId());
        HttpEntity<Object> vendorEntity = new HttpEntity<>(entity.getVendorRequest());
        ResponseEntity<VendorResponse> response = restTemplate.exchange(VENDOR_CONFIRM_URL, HttpMethod.POST, vendorEntity, VendorResponse.class);
        log.info("[VendorService] Got Vendor Response for SR: {}, : {}", entity.getSrDetail().getSrId(), response.getBody());
        return response.getBody();
    }


    private VendorInfo getVendorInfo(SRDetail srDetail, VendorResponse vendorResponse){
        switch (vendorResponse.getStatus()) {
            case "VNF":
                return new VendorInfo(srDetail.getSrId(), VendorInfo.VendorStatus.VNF);
            case "NOT_CHANGED":
                return new VendorInfo(srDetail.getSrId(), VendorInfo.VendorStatus.NOT_CHANGED);
            case "CHANGED":
                return new VendorInfo(srDetail.getSrId(), VendorInfo.VendorStatus.CHANGED, vendorResponse.getNewVendor());
            default:
                return new VendorInfo(srDetail.getSrId(), VendorInfo.VendorStatus.FAILED, "");
        }
    }

}
