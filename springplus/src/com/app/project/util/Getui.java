package com.app.project.util;

import java.io.IOException;
import java.util.List;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.apns.Payload;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class Getui {
	//首先定义一些常量, 修改成开发者平台获得的值
    public static String appId = "PvRh52wHVEA6uVxJHlYeUA";
    public static String appKey = "7NDli7VBpH7jEJnSxbkPl5";
    public static String masterSecret = "8yPG9u99Or7ZPVXcCwjry9";
    public static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    public static IGtPush push = new IGtPush(url,appKey, masterSecret);
    public static String keyPath= "WEB-INF/classes/configs/aps.p12";
	public static String password="Be2016it";
    
	public static String sendMessage(String userid,String message) {
		
		
		try {
			push.pushMessageToSingle(getTemplate(message), getTarget(userid));
		} catch (RequestException e) {
			push.pushMessageToSingle(null, getTarget(userid), e.getRequestId());
			e.printStackTrace();
		}
		return "";
	}
	public static SingleMessage getTemplate(String message) {
		SingleMessage singleMessage=new SingleMessage();
		singleMessage.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        singleMessage.setOfflineExpireTime(24 * 3600 * 1000);
        TransmissionTemplate template=new TransmissionTemplate();
		template.setAppId(appId);
        template.setAppkey(appKey);
     // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(message);
        
        APNPayload payload=new APNPayload();
        DictionaryAlertMsg alertMsg=new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(message);
        //alertMsg.setTitle(title);
        payload.setAlertMsg(alertMsg);
        template.setAPNInfo(payload);
        
        singleMessage.setData(template);
        singleMessage.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
	    return singleMessage;
	}
    
    public static Target getTarget(String userid){
    	Target target=new Target();
    	target.setAppId(appId);
    	target.setAlias(userid);
    	return target;
    }
   
}