package com.hrishikeshmishra.bj.models;

import lombok.*;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VendorChangeRequest {
    private String sr;
    private String newVendor;
}
