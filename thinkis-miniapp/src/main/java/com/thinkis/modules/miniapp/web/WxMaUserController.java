package com.thinkis.modules.miniapp.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.miniapp.dto.MiniAppParam;
import com.thinkis.modules.sys.entity.Office;
import com.thinkis.modules.sys.entity.Role;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.SystemService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("${apiPath}/miniapp/user/")
public class WxMaUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;
    @Autowired
    private SystemService systemService;

    /**
     * 登陆接口
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(@RequestBody MiniAppParam param) {
        String code = param.getCode();
        if (StringUtils.isBlank(code)) {
            return ResultGenerator.genFailResult("empty jscode");
        }
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return ResultGenerator.genSuccessResult(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return ResultGenerator.genFailResult(e.toString());
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @RequestMapping(value = "info",method = RequestMethod.POST)
    public Result info(@RequestBody MiniAppParam param) {
        String sessionKey = param.getSessionKey();
        String signature = param.getSignature();
        String rawData = param.getRawData();
        String encryptedData = param.getEncryptedData();
        String iv = param.getIv();

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ResultGenerator.genFailResult( "user check failed");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return ResultGenerator.genSuccessResult(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @RequestMapping(value = "phone",method = RequestMethod.GET)
    public Result phone(@RequestBody MiniAppParam param) {
        String sessionKey = param.getSessionKey();
        String signature = param.getSignature();
        String rawData = param.getRawData();
        String encryptedData = param.getEncryptedData();
        String iv = param.getIv();
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ResultGenerator.genFailResult( "user check failed");
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return ResultGenerator.genSuccessResult(phoneNoInfo);
    }

    /**
     * 注册
     * @param param
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Result register(@RequestBody MiniAppParam param) {
        String sessionKey = param.getSessionKey();
        String signature = param.getSignature();
        String rawData = param.getRawData();
        String encryptedData = param.getEncryptedData();
        String iv = param.getIv();

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ResultGenerator.genFailResult( "user check failed");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        User user = new User();
        user.setLoginName(phoneNoInfo.getPhoneNumber());
        user.setName(userInfo.getNickName());
        user.setPassword(SystemService.entryptPassword("123456"));
        user.setOpenId(userInfo.getOpenId());
        user.setPhoto(userInfo.getAvatarUrl());
        user.setPhone(phoneNoInfo.getPhoneNumber());

        // 保存用户信息
        systemService.createUser(user);
        return ResultGenerator.genSuccessResult("恭喜您! "+user.getName()+"，注册成功");
    }
}
