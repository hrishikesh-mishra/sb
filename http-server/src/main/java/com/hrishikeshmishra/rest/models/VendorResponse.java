package com.hrishikeshmishra.rest.models;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */

public class VendorResponse {

    private String status;

    public VendorResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VendorResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
