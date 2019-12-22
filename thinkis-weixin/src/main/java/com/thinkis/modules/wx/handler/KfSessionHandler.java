package com.thinkis.modules.wx.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.thinkis.modules.wx.config.WxConfig;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
@Component
public class KfSessionHandler extends AbstractHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    //TODO 对会话做处理
    return null;
  }

  @Override
  protected WxConfig getWxConfig() {
    return null;
  }

}
