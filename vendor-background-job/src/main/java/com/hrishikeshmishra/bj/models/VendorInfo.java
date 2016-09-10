package com.hrishikeshmishra.bj.models;

import lombok.*;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
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
