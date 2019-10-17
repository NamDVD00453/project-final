
package com.vinamine.mc.rest.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Credential implements Serializable {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("clientType")
    @Expose
    private String clientType;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("expired")
    @Expose
    private String expired;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

}
