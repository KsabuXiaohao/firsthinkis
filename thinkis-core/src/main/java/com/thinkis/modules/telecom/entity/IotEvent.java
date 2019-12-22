package com.thinkis.modules.telecom.entity;

import com.thinkis.common.persistence.DataEntity;

import java.util.Date;

public class IotEvent extends DataEntity<IotEvent> {

    private String  notifyType;
    private String  deviceId;
    private String  gatewayId;
    private String  requestId;
    private String  serviceId;
    private String  serviceType;
    private Date eventTime;
    private String  serviceData;
    private Date  lastUpdatedStamp;
    private Date  lastUpdatedTxStamp;
    private Date  createdStamp;
    private String  removed;
    private Date  createdTxStamp;
    private String alarm;
    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * 该字段为SQL代名字段
     */
    private String jinggao;

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public String getServiceData() {
        return serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Date getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getJinggao() {
        return jinggao;
    }

    public void setJinggao(String jinggao) {
        this.jinggao = jinggao;
    }
}
