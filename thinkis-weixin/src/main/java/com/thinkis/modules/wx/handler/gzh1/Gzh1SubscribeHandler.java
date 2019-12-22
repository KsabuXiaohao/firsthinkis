package com.thinkis.modules.wx.handler.gzh1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkis.modules.wx.config.WxConfig;
import com.thinkis.modules.wx.config.WxGzh1Config;
import com.thinkis.modules.wx.handler.SubscribeHandler;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component
public class Gzh1SubscribeHandler extends SubscribeHandler {
  @Autowired
  private WxGzh1Config wxConfig;

  @Override
  protected WxConfig getWxConfig() {
    return this.wxConfig;
  }

  @Override
  protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) {
    return null;
  }

}
