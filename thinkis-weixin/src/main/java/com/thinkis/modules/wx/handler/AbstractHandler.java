package com.thinkis.modules.wx.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.thinkis.modules.wx.config.WxConfig;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;

/**
 * @author Binary Wang
 */
public abstract class AbstractHandler implements WxMpMessageHandler {

  protected final Gson gson = new Gson();
  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected abstract WxConfig getWxConfig();

}
