package com.thinkis.modules.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thinkis.modules.wx.service.BaseWxService;
import com.thinkis.modules.wx.service.Gzh1WxService;

/**
 * 第一个公众号的微信交互接口
 *
 * @author Binary Wang
 */
@RestController
@RequestMapping("${frontPath}/api/gzh1/portal")
public class Gzh1WxPortalController extends AbstractWxPortalController {
  @Autowired
  private Gzh1WxService wxService;

  @Override
  protected BaseWxService getWxService() {
    return this.wxService;
  }

}
