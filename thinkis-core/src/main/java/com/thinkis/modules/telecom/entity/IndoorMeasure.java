package com.thinkis.modules.telecom.entity;

import com.thinkis.common.persistence.DataEntity;

public class IndoorMeasure extends DataEntity<IndoorMeasure> {

    private int pointid;

    private String cme;

    private String meid;

    private String imsi;

    private String ip;

    private String cid;

    private String snr;

    private String pci;

    private String rsrq;

    private String signal_power;

    private String total_power;

    private String ecl;

    private String earfcn;

    private String tx_power;

    private int tx_time;

    private int rx_time;

    private String location;

    private String remark;

    private String longitude;

    private String latitude;

    private String geohash;

    private String savetime;

    public int getPointid() {
        return pointid;
    }

    public void setPointid(int pointid) {
        this.pointid = pointid;
    }

    public String getCme() {
        return cme;
    }

    public void setCme(String cme) {
        this.cme = cme;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getRsrq() {
        return rsrq;
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq;
    }

    public String getSignal_power() {
        return signal_power;
    }

    public void setSignal_power(String signal_power) {
        this.signal_power = signal_power;
    }

    public String getTotal_power() {
        return total_power;
    }

    public void setTotal_power(String total_power) {
        this.total_power = total_power;
    }

    public String getEcl() {
        return ecl;
    }

    public void setEcl(String ecl) {
        this.ecl = ecl;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getTx_power() {
        return tx_power;
    }

    public void setTx_power(String tx_power) {
        this.tx_power = tx_power;
    }

    public int getTx_time() {
        return tx_time;
    }

    public void setTx_time(int tx_time) {
        this.tx_time = tx_time;
    }

    public int getRx_time() {
        return rx_time;
    }

    public void setRx_time(int rx_time) {
        this.rx_time = rx_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }
}
