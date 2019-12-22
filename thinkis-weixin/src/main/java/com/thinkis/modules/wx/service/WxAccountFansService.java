/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.wx.service;

import com.google.common.collect.Lists;
import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.wx.dao.WxAccountFansDao;
import com.thinkis.modules.wx.entity.WxAccountFans;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公众号关注人Service
 * @author liuzhiping
 * @version 2018-04-13
 */
@Service
@Transactional(readOnly = true)
public class WxAccountFansService extends CrudService<WxAccountFansDao, WxAccountFans> {
	
 	@Autowired
 	private Gzh1WxService wxService;

	public WxAccountFans get(String id) {
		return super.get(id);
	}
	
	public List<WxAccountFans> findList(WxAccountFans wxAccountFans) {
		return super.findList(wxAccountFans);
	}
	
	public Page<WxAccountFans> findPage(Page<WxAccountFans> page, WxAccountFans wxAccountFans) {
		return super.findPage(page, wxAccountFans);
	}
	
	@Transactional(readOnly = false)
	public List<WxMpUser> syncUserInfoList(){
		List<WxMpUser> wxMpUsers = Lists.newArrayList();
		try {
			WxMpUserList userList = wxService.getUserService().userList(null);
			wxMpUsers = wxService.getUserService().userInfoList(userList.getOpenids());
			for(WxMpUser wxMpUser : wxMpUsers){
				WxAccountFans wxAccountFans = new WxAccountFans();
				wxAccountFans.setOpenId(wxMpUser.getOpenId());
				List<WxAccountFans> wxAccountFanss = super.findList(wxAccountFans);
				if(null!=wxAccountFanss&&wxAccountFanss.size()>0){
					//数据库中已经存在该微信用户，更新数据库用户信息
					WxAccountFans obj = wxAccountFanss.get(0);
					obj.setSubscribeStatus(wxMpUser.getSubscribe().toString());
					obj.setSubscribeTime(new Date(wxMpUser.getSubscribeTime()*1000));
					obj.setNickname(filterEmoji(wxMpUser.getNickname()));
					obj.setSex(wxMpUser.getSex().toString());
					obj.setSexDesc(wxMpUser.getSexDesc());
					obj.setLanguage(wxMpUser.getLanguage());
					obj.setCountry(wxMpUser.getCountry());
					obj.setProvince(wxMpUser.getProvince());
					obj.setCity(wxMpUser.getCity());
					obj.setHeadimgurl(wxMpUser.getHeadImgUrl());
					obj.setCreateTime(new Date());
					obj.setStatus(Global.YES);
					obj.setUnionId(wxMpUser.getUnionId());
					obj.setGroupId(wxMpUser.getGroupId().toString());
					super.save(obj);
				}else {
					//数据库中不存在该微信用户，保存该用户到数据库
					wxAccountFans.setSubscribeStatus(wxMpUser.getSubscribe().toString());
					wxAccountFans.setSubscribeTime(new Date(wxMpUser.getSubscribeTime()*1000));
					wxAccountFans.setNickname(filterEmoji(wxMpUser.getNickname()));
					wxAccountFans.setSex(wxMpUser.getSex().toString());
					wxAccountFans.setSexDesc(wxMpUser.getSexDesc());
					wxAccountFans.setLanguage(wxMpUser.getLanguage());
					wxAccountFans.setCountry(wxMpUser.getCountry());
					wxAccountFans.setProvince(wxMpUser.getProvince());
					wxAccountFans.setCity(wxMpUser.getCity());
					wxAccountFans.setHeadimgurl(wxMpUser.getHeadImgUrl());
					wxAccountFans.setCreateTime(new Date());
					wxAccountFans.setStatus(Global.YES);
					wxAccountFans.setUnionId(wxMpUser.getUnionId());
					wxAccountFans.setGroupId(wxMpUser.getGroupId().toString());
					super.save(wxAccountFans);
				}
			}
			//更新微信用户关注状态
			this.filterFans(userList);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return wxMpUsers;
	}
	
	/**
	 * 过滤取消关注的微信关注人
	 * 更新数据库用户状态
	 * @param userList
	 */
	private void filterFans(WxMpUserList userList){
		if(null!=userList){
			List<String> openids = userList.getOpenids();
			List<WxAccountFans> wxAccountFansList = super.findList(new WxAccountFans());
			for(WxAccountFans obj : wxAccountFansList){
				String openId = obj.getOpenId();
				boolean isExist = false;
				for(String id : openids){
					if(openId.equals(id)){
						isExist = true;
						continue;
					}
				}
				if(!isExist){
					obj.setStatus(Global.NO);
					obj.setSubscribeStatus("false");
					super.save(obj);
				}
			}
		}
	}
	
	/**
	 * 过滤昵称中的特殊字符
	 * @param source
	 * @return
	 */
	private String filterEmoji(String source) {
        if (source == null) {
            return source;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("*");
            return source;
        }
        return source;
    }
	
	@Transactional(readOnly = false)
	public void save(WxAccountFans wxAccountFans) {
		super.save(wxAccountFans);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAccountFans wxAccountFans) {
		super.delete(wxAccountFans);
	}
	
	@Transactional(readOnly = false)
	public int batchDelete(String selectedIds){
		int success = 0;
		try{
			String[] ids = selectedIds.split(",");
			for(String id : ids){
				WxAccountFans wxAccountFans = new WxAccountFans();
				wxAccountFans.setId(id);
				super.delete(wxAccountFans);
				success++;
			}
		}catch(Exception e){
			logger.error("数据删除失败");
			return success;
		}
		return success;
	}
	
}