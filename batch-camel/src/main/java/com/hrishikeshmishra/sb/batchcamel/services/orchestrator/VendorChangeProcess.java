package com.hrishikeshmishra.sb.batchcamel.services.orchestrator;

import com.hrishikeshmishra.sb.batchcamel.apis.models.VendorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@Component("orchestratorVendorChange")
@Slf4j
public class VendorChangeProcess implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        VendorInfo vendorInfo = (VendorInfo) exchange.getIn().getBody();
        log.info("[orchestratorVendorChange] Vendor  : {}" , vendorInfo );
    }

}
