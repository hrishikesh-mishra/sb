package com.hrishikeshmishra.sb.batchcamel.apis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hrishikesh.mishra on 24/09/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorInfo {

    private String serviceRequestId;
    private VendorStatus vendorStatus;
    private String currentVendor;

    public VendorInfo(String serviceRequestId, VendorStatus vendorStatus) {
        this.serviceRequestId = serviceRequestId;
        this.vendorStatus = vendorStatus;
    }

    public static enum VendorStatus {
        NOT_CHANGED,
        CHANGED,
        VNF,
        FAILED;
    }
}
