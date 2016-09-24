package com.hrishikeshmishra.sb.batchcamel.services.orchestrator;

import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component("orchestratorVNF")
@Slf4j
public class VNFProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        VendorInfo vendorInfo = (VendorInfo) exchange.getIn().getBody();
        log.info("[orchestratorVNF] Vendor  : {}" , vendorInfo );
    }
}