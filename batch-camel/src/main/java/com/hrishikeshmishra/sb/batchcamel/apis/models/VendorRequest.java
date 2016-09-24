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
public class VendorRequest {

    private String merchantReferenceId;

    private String merchantCode;

    private String sourceType;

    private double shipmentValue;

    private double amountToCollect;

    private long originPincode;

}


