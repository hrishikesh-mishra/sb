package com.hrishikeshmishra.bj.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */

@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendorRequest {
    private String sr;
    private Vendor vendor;
}
