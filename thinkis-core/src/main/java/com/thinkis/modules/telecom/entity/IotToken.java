package com.thinkis.modules.telecom.entity;

import com.thinkis.common.persistence.DataEntity;

public class IotToken extends DataEntity<IotToken> {

    private String scope;
    private String tokentype;
    private String expiresin;
    private String accesstoken;
    private String refreshtoken;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokentype() {
        return tokentype;
    }

    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
    }

    public String getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(String expiresin) {
        this.expiresin = expiresin;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }
}
