package com.thinkis.common.oauth.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustAnyHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		return true;
	}

}
