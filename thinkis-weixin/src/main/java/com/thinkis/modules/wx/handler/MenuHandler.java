package com.thinkis.modules.wx.handler;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.thinkis.modules.wx.builder.AbstractBuilder;
import com.thinkis.modules.wx.builder.ImageBuilder;
import com.thinkis.modules.wx.builder.TextBuilder;
import com.thinkis.modules.wx.dto.WxMenuEnum;
import com.thinkis.modules.wx.dto.WxMenuKey;
import com.thinkis.modules.wx.service.BaseWxService;

import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang
 */
public abstract class MenuHandler extends AbstractHandler {

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {
    BaseWxService weixinService = (BaseWxService) wxMpService;

    String key = wxMessage.getEventKey();
    WxMenuKey menuKey = null;
    try {
      menuKey = JSON.parseObject(key, WxMenuKey.class);
    } catch (Exception e) {

      String content = WxMenuEnum.getValue(key);
      return WxMpXmlOutMessage.TEXT().content(content)
          .fromUser(wxMessage.getToUser())
          .toUser(wxMessage.getFromUser()).build();
    }

    AbstractBuilder builder = null;
    switch (menuKey.getType()) {
      case XmlMsgType.TEXT:
        builder = new TextBuilder();
        break;
      case XmlMsgType.IMAGE:
        builder = new ImageBuilder();
        break;
      case XmlMsgType.VOICE:
        break;
      case XmlMsgType.VIDEO:
        break;
      case XmlMsgType.NEWS:
        break;
      default:
        break;
    }

    if (builder != null) {
      try {
        return builder.build(menuKey.getContent(), wxMessage, weixinService);
      } catch (Exception e) {
        this.logger.error(e.getMessage(), e);
      }
    }

    return null;

  }

}
