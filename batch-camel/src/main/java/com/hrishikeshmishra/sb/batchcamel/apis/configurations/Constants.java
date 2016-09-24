package com.hrishikeshmishra.sb.batchcamel.apis.configurations;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
public interface Constants {
    String QUEUE_NAME = "sr_list";
    String DETAIL = "_detail";
    String ORCHESTRATOR_VENDOR_CHANGE_URL = "http://127.0.0.1:8080/orchestrator/vendor/change";
    String ORCHESTRATOR_VENDOR_VNF_URL = "http://127.0.0.1:8080/orchestrator/vendor/vnf";
    String VENDOR_CONFIRM_URL  = "http://127.0.0.1:8080/vendor/confirm";
    public static final String CAMEL_VENDOR_ROUTE = "direct://background-process";
}
