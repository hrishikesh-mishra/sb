package com.hrishikeshmishra.rest.models;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */
public class VNFRequest {
    private String sr;

    public VNFRequest(String sr) {
        this.sr = sr;
    }

    public VNFRequest() {
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    @Override
    public String toString() {
        return "VNFRequest{" +
                "sr='" + sr + '\'' +
                '}';
    }
}
