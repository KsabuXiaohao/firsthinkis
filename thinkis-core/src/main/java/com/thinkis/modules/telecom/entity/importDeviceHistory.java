package com.thinkis.modules.telecom.entity;

import com.thinkis.common.persistence.DataEntity;
import com.thinkis.common.utils.excel.annotation.ExcelField;

public class importDeviceHistory extends DataEntity<importDeviceHistory> {
    private String meid;

    private String companyid;

    private String company;

    private int isused;

    private String updatetime;

    private String addtime;

    private String remark;

    private String name;

    private String sdate;

    private String edate;

    private String sim;

    private String imei;

    private String imsi;

    private String longitude;

    private String latitude;

    private String position;

    private String deviceid;

    private String alarm;

    private String createdStamp;

    private String jinggao;

    private String data;

    private String zhi;

    private String shijian;

    private String sendaddress;

    private String sendtype;

    private String isonline;

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getSendaddress() {
        return sendaddress;
    }

    public void setSendaddress(String sendaddress) {
        this.sendaddress = sendaddress;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public String getZhi() {
        return zhi;
    }

    public void setZhi(String zhi) {
        this.zhi = zhi;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getJinggao() {
        return jinggao;
    }

    public void setJinggao(String jinggao) {
        this.jinggao = jinggao;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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


    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
    @ExcelField(title="公司名称", align=1, sort=30)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getIsused() {
        return isused;
    }

    public void setIsused(int isused) {
        this.isused = isused;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    @ExcelField(title="添加时间", align=4, sort=30)
    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }
    @ExcelField(title="IMEI", align=2, sort=30)
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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
    @ExcelField(title="安装位置", align=3, sort=30)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
