package com.thinkis.common.utils;

import java.util.regex.Pattern;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {
	public static final String module = SmsUtil.class.getName();

	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
//    static final String accessKeyId = "LTAI67p5oQXp1Rb0";
//    static final String accessKeySecret = "UePzv31LxbPsXJwk5wdmo4RJ1BykBC";
    static final String accessKeyId = "LTAILeFYbn8WtqIP";
    static final String accessKeySecret = "GD7K6qFMgb3BVJLUHfFpFrl8LCqctQ";
    static final String signName = "孚睿星辰";
	

	
    public static SendSmsResponse sendSms(String mobiles, String templateParam, String templateCode) throws ClientException{
		
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobiles);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);
//        request.setTemplateParam("{\"name\":\"Tom\", \"phone_num\":\"13636428359\", \"help\":\"刘先生\", \"info\":\"测试发送短信\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
       // request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

	public static void main(String[] args) throws ClientException {
		SendSmsResponse sendSmsResponse = sendSms("17612163089", "{\"name\":\"群众2\", \"phone_num\":\"02155444022\", \"help\":\"咨询\", \"user_question\":\"喂你好，我想问一下，嗯关于那个养老...\"}", "SMS_162547280");
		System.out.println(sendSmsResponse.getCode()+":"+sendSmsResponse.getMessage());
//		String checkNumber = "模板不合法(不存在或被拉黑)isv.SMS_TEMPLATE_ILLEGAL:";
//		if(checkNumber.length()>20) checkNumber = checkNumber.substring(0, 20);
//		System.out.println(checkNumber);
		
	}
	
}
