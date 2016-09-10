package com.hrishikeshmishra.rest.models;

/**
 * Created by hrishikesh.mishra on 10/09/16.
 */

public class VendorRequest {
    private String merchantReferenceId;

    private String merchantCode;

    private String sourceType;

    private double shipmentValue;

    private double amountToCollect;

    private long originPincode;

    public VendorRequest() {
    }

    public VendorRequest(String merchantReferenceId, String merchantCode, String sourceType, double shipmentValue, double amountToCollect, long originPincode) {
        this.merchantReferenceId = merchantReferenceId;
        this.merchantCode = merchantCode;
        this.sourceType = sourceType;
        this.shipmentValue = shipmentValue;
        this.amountToCollect = amountToCollect;
        this.originPincode = originPincode;
    }

    public String getMerchantReferenceId() {
        return merchantReferenceId;
    }

    public void setMerchantReferenceId(String merchantReferenceId) {
        this.merchantReferenceId = merchantReferenceId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public double getShipmentValue() {
        return shipmentValue;
    }

    public void setShipmentValue(double shipmentValue) {
        this.shipmentValue = shipmentValue;
    }

    public double getAmountToCollect() {
        return amountToCollect;
    }

    public void setAmountToCollect(double amountToCollect) {
        this.amountToCollect = amountToCollect;
    }

    public long getOriginPincode() {
        return originPincode;
    }

    public void setOriginPincode(long originPincode) {
        this.originPincode = originPincode;
    }

    @Override
    public String toString() {
        return "VendorRequest{" +
                "merchantReferenceId='" + merchantReferenceId + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", shipmentValue=" + shipmentValue +
                ", amountToCollect=" + amountToCollect +
                ", originPincode=" + originPincode +
                '}';
    }
}
