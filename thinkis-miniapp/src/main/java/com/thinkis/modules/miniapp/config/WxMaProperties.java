package com.thinkis.modules.miniapp.config;

import com.thinkis.common.config.Global;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Configuration
public class WxMaProperties {

    /**
     * 设置微信小程序的appid
     */
    private String appid = Global.getConfig("miniapp_appid");

    /**
     * 设置微信小程序的Secret
     */
    private String secret = Global.getConfig("miniapp_secret");

    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token = Global.getConfig("miniapp_token");

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey = Global.getConfig("miniapp_aesKey");

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat = Global.getConfig("miniapp_msgDataFormat");


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMsgDataFormat() {
        return msgDataFormat;
    }

    public void setMsgDataFormat(String msgDataFormat) {
        this.msgDataFormat = msgDataFormat;
    }
}
