package com.hrishikeshmishra.rest.models;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
public class VendorChangeRequest {

    private String sr;
    private String newVendor;

    public VendorChangeRequest() {
    }

    public VendorChangeRequest(String sr, String newVendor) {
        this.sr = sr;
        this.newVendor = newVendor;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getNewVendor() {
        return newVendor;
    }

    public void setNewVendor(String newVendor) {
        this.newVendor = newVendor;
    }

    @Override
    public String toString() {
        return "VendorChangeRequest{" +
                "sr='" + sr + '\'' +
                ", newVendor='" + newVendor + '\'' +
                '}';
    }
}
