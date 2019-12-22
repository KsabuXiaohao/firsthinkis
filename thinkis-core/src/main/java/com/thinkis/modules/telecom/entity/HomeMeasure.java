package com.thinkis.modules.telecom.entity;

import com.thinkis.common.persistence.DataEntity;
import com.thinkis.common.utils.excel.annotation.ExcelField;

public class HomeMeasure extends DataEntity<HomeMeasure> {

    private String cme;

    private String meid;

    private String imsi;

    private String ip;

    private String cid;

    private String snr;

    private String pci;

    private String rsrq;

    private String signalpower;

    private String totalpower;

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

    private String date;

    private String textnum;

    private String signavg;

    private String sdate;

    private String edate;

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextnum() {
        return textnum;
    }

    public void setTextnum(String textnum) {
        this.textnum = textnum;
    }

    public String getSignavg() {
        return signavg;
    }

    public void setSignavg(String signavg) {
        this.signavg = signavg;
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

    @ExcelField(title="CellID", align=7, sort=20)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    @ExcelField(title="SNR", align=5, sort=20)
    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }
    @ExcelField(title="PIC", align=6, sort=20)
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
    @ExcelField(title="RSRP", align=6, sort=20)
    public String getSignalpower() {
        return signalpower;
    }

    public void setSignalpower(String signalpower) {
        this.signalpower = signalpower;
    }

    public String getTotalpower() {
        return totalpower;
    }

    public void setTotalpower(String totalpower) {
        this.totalpower = totalpower;
    }

    public String getEcl() {
        return ecl;
    }

    public void setEcl(String ecl) {
        this.ecl = ecl;
    }
    @ExcelField(title="频点", align=8, sort=20)
    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }
    @ExcelField(title="tx_power", align=9, sort=20)
    public String getTx_power() {
        return tx_power;
    }

    public void setTx_power(String tx_power) {
        this.tx_power = tx_power;
    }
    @ExcelField(title="tx_time", align=10, sort=20)
    public int getTx_time() {
        return tx_time;
    }

    public void setTx_time(int tx_time) {
        this.tx_time = tx_time;
    }
    @ExcelField(title="rx_time", align=11, sort=20)
    public int getRx_time() {
        return rx_time;
    }

    public void setRx_time(int rx_time) {
        this.rx_time = rx_time;
    }
    @ExcelField(title="测试地址", align=2, sort=20)
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
    @ExcelField(title="经度", align=3, sort=20)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    @ExcelField(title="纬度", align=4, sort=20)
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
    @ExcelField(title="测试时间", align=1, sort=20)
    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }
}
