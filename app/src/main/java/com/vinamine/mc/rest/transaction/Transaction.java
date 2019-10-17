package com.vinamine.mc.rest.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("accountId")
    @Expose
    private Integer accountId;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("storeAddressId")
    @Expose
    private Integer storeAddressId;
    @SerializedName("voucherId")
    @Expose
    private Integer voucherId;
    @SerializedName("adults")
    @Expose
    private Integer adults;
    @SerializedName("children")
    @Expose
    private Integer children;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStoreAddressId() {
        return storeAddressId;
    }

    public void setStoreAddressId(Integer storeAddressId) {
        this.storeAddressId = storeAddressId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}