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
public class VendorResponse {
    private String status;
    private String newVendor;

}
