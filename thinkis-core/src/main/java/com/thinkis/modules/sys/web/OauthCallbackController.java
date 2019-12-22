package com.thinkis.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkis.common.config.Global;
import com.thinkis.common.oauth.APIConfig;
import com.thinkis.common.oauth.OauthGithub;
import com.thinkis.common.oauth.OauthQQ;
import com.thinkis.common.oauth.util.OpenOauthBean;
import com.thinkis.common.oauth.util.TokenUtil;
import com.thinkis.common.service.ServiceException;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.OpenOauth;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.oauth.OauthToken;
import com.thinkis.modules.sys.service.OpenOauthService;
import com.thinkis.modules.sys.service.SystemService;

import cn.hutool.core.convert.Convert;

/**
 * 第三方登录回调
 *
 * @author liuzhiping 2018年5月15日.
 */
@Controller
@RequestMapping("/oauth/callback")
public class OauthCallbackController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	
    private static final String SESSION_STATE = "_SESSION_STATE_";

    @Autowired
    private OpenOauthService openOauthService;
    @Autowired
	private SystemService systemService;

    /**
     * 跳转到qq进行授权
     * @param request
     * @param response
     * @author liuzhiping 2018年5月15日13:56:30
     */
    @RequestMapping("/call_qq")
    public void callQQ(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
        	//获取并设置配置信息
        	APIConfig.getInstance().setOpenid_qq(Global.getConfig("ouath.qq.appid"));
            APIConfig.getInstance().setOpenkey_qq(Global.getConfig("ouath.qq.appkey"));
            APIConfig.getInstance().setRedirect_qq(Global.getConfig("ouath.qq.redirectUrl"));
            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthQQ.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new ServiceException("跳转到qq授权接口时发生异常");
        }
    }

    /**
     * qq回调
     *
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/qq")
    public String callback4QQ(String code, String state, HttpServletRequest request, Model model) throws Exception {
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);
        // 取消了授权
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new ServiceException("缺少必要的参数");
        }
        request.getSession().removeAttribute(SESSION_STATE);

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthQQ.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new ServiceException("解析信息时发生错误");
        }

        OpenOauth openOauth = new OpenOauth();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getExpireIn());
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(Convert.toStr(openOauthBean.getOauthType()));
        openOauth.setRefreshToken(openOauthBean.getRefreshToken());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());

        // 判断是否存在绑定此accessToken的用户
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        if (thirdToken == null) {
        	model.addAttribute("open", openOauth);
            //绑定页面
            return "modules/sys/userBind";
        }
        return login(openOauth.getOauthUserId(), code, request);
    }

    /**
     * 跳转到github进行授权
     * @param request
     * @param response
     * @author liuzhiping 2018年5月15日13:56:30
     */
    @RequestMapping("/call_github")
    public void callGithub(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try {
        	//获取并设置配置信息
        	APIConfig.getInstance().setOpenid_github(Global.getConfig("ouath.github.appid"));
            APIConfig.getInstance().setOpenkey_github(Global.getConfig("ouath.github.appkey"));
            APIConfig.getInstance().setRedirect_github(Global.getConfig("ouath.github.redirectUrl"));

            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthGithub.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new ServiceException("跳转到github授权接口时发生异常");
        }
    }

    /**
     * github回调
     *
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/github")
    public String callback4Github(String code, String state, HttpServletRequest request, ModelMap model) throws Exception {
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);
        // 取消了授权
        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new ServiceException("缺少必要的参数");
        }
        request.getSession().removeAttribute(SESSION_STATE);

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthGithub.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new ServiceException("解析信息时发生错误");
        }

        OpenOauth openOauth = new OpenOauth();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getExpireIn());
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(Convert.toStr(openOauthBean.getOauthType()));
        openOauth.setRefreshToken(openOauthBean.getRefreshToken());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());

        // 判断是否存在绑定此accessToken的用户
        OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        if (thirdToken == null) {
            model.put("open", openOauth);
            //绑定页面
            return "modules/sys/userBind";
        }
        return login(openOauth.getOauthUserId(), code, request);
    }

    /**
     * 执行第三方绑定
     * @param openOauth
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/bind_oauth")
    public String bindOauth(OpenOauth openOauth, HttpServletRequest request,Model model){
        String username = openOauth.getUsername();
        String password = openOauth.getPassword();
        if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)){
        	User user = systemService.getUserByLoginName(username);
        	if(null!=user){
        		if(SystemService.validatePassword(password, user.getPassword())){
        			openOauth.setUser(user);
        			openOauthService.save(openOauth);
        			return login(openOauth.getOauthUserId(), openOauth.getOauthCode(), request);
        		}else{
        			model.addAttribute("open", openOauth);
        			model.addAttribute("message", "密码错误");
        			return "modules/sys/userBind";
        		}
        	}else{
        		model.addAttribute("open", openOauth);
        		model.addAttribute("message", "用户不存在");
    			return "modules/sys/userBind";
    		}
        }else{
        	model.addAttribute("open", openOauth);
        	model.addAttribute("message", "用户名或者密码不能为空");
			return "modules/sys/userBind";
        }
    }

    /**
     * 执行登录请求
     *
     * @param username
     * @param request
     * @return
     */
    private String login(String openId, String code, HttpServletRequest request) {
        String ret = "redirect:" + frontPath;
        if (StringUtils.isNotBlank(openId)) {
            AuthenticationToken token = new OauthToken(openId, code);
            try {
                SecurityUtils.getSubject().login(token);
            } catch (AuthenticationException e) {
                logger.error(e);
                if (e instanceof UnknownAccountException) {
                    throw new ServiceException("用户不存在");
                } else if (e instanceof LockedAccountException) {
                    throw new ServiceException("用户被禁用");
                } else {
                    throw new ServiceException("用户认证失败");
                }
            }
            return ret;
        }
        throw new ServiceException("登录失败！");
    }

}