package com.hrishikeshmishra.rest.models;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */

public class VendorResponse {

    private String status;
    private String newVendor;

    public VendorResponse(String status) {
        this.status = status;
    }

    public VendorResponse(String status, String newVendor) {
        this.status = status;
        this.newVendor = newVendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewVendor() {
        return newVendor;
    }

    public void setNewVendor(String newVendor) {
        this.newVendor = newVendor;
    }

    @Override
    public String toString() {
        return "VendorResponse{" +
                "status='" + status + '\'' +
                ", newVendor='" + newVendor + '\'' +
                '}';
    }
}
