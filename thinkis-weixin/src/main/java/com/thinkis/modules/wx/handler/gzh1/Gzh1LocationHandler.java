package com.thinkis.modules.wx.handler.gzh1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkis.modules.wx.config.WxConfig;
import com.thinkis.modules.wx.config.WxGzh1Config;
import com.thinkis.modules.wx.handler.LocationHandler;

@Component
public class Gzh1LocationHandler extends LocationHandler {
  @Autowired
  private WxGzh1Config wxConfig;

  @Override
  protected WxConfig getWxConfig() {
    return this.wxConfig;
  }

}
