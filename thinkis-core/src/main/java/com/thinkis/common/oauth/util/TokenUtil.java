package com.thinkis.common.oauth.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import com.alibaba.fastjson.JSONObject;

public class TokenUtil {
	private static final String STR_S = "abcdefghijklmnopqrstuvwxyz0123456789";

	public static String getAccessToken(String string) {
		String accessToken = "";
		try {
			JSONObject json = JSONObject.parseObject(string);
			if (json != null)
				accessToken = json.getString("access_token");
		} catch (Exception e) {
			Matcher m = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$").matcher(string);
			if (m.find()) {
				accessToken = m.group(1);
			} else {
				Matcher m2 = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)$").matcher(string);
				if (m2.find()) {
					accessToken = m2.group(1);
				} else {
					Matcher m3 = Pattern.compile("^access_token=(\\w+)&scope=&token_type=(\\w+)$").matcher(string);
					if(m3.find()){
						accessToken = m3.group(1);
					}
				}
			}
		}
		return accessToken;
	}

	public static String getOpenId(String string) {
		String openid = null;
		Matcher m = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"").matcher(string);
		if (m.find())
			openid = m.group(1);
		return openid;
	}

	public static String getUid(String string) {
		JSONObject json = JSONObject.parseObject(string);
		return json.getString("uid");
	}

	public static String randomState() {
		return RandomStringUtils.random(24, STR_S);
	}
}
