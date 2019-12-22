package com.thinkis;


import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpRequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JSONObject params = new JSONObject();
    	params.put("systemCode", "13");
    	params.put("securityKey", "M0UwMDFDNTQxQTk5QTQ2N0MyNDAxQTE2QUNCQzk4QUJGRjlBNjU1Nzc2MDBBNUZERUZCOTc3ODc0RTc1OEQxMjI0Q0QxNTIwNkNFN0IzREM4QTJFOEQwMTE0Nzk4Qzg1QTMxMzk3MEUyRkRGREIyOQ");
    	String json = params.toJSONString();
    	
    	String url = "http://map.szssfj.cn/gdfwhxzc-test/portal/oauth/oauthAction!getToken.action";
    	
    	String result2 = HttpRequest.post(url)
    			.body(json)
    			.contentType("application/json")
    			.execute().body();
        System.out.println(result2);
    }
}
