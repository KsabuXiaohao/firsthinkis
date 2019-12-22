package com.thinkis.common.oauth.util;

import com.thinkis.common.oauth.APIConfig;

public class OathConfig {
	public static String getValue(String key) {
		return APIConfig.getInstance().getValue(key);
	}
}