package com.hrishikeshmishra.bj.models;

import lombok.*;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    private String merchantReferenceId;

    private String merchantCode;

    private String sourceType;

    private double shipmentValue;

    private double amountToCollect;

    private long originPincode;
}
