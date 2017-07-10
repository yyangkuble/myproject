package com.app.project.util;

import java.io.IOException;
import java.util.List;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
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
    public static String appId = "6cBdFepiNoA9OzwPQRxPk6";
    public static String appKey = "bFncaMC7T977AB6Sju0E";
    public static String masterSecret = "sL1L6uRAoN7Fbqx8E2Xrj8";
    public static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    public static IGtPush push = new IGtPush(url,appKey, masterSecret);
    public static String keyPath= "WEB-INF/classes/configs/BaoXianDK.p12";
	public static String password="123456";
    
	public static Result sendMessage(String userid,String message) {
		try {
			push.pushMessageToSingle(getTemplate(message), getTarget(userid));
			return new Result();
		} catch (RequestException e) {
			e.printStackTrace();
			return new Result(1,"绑定失败，网络问题，请重新绑定");
		}
	}
	
	public static Result setAlias(String username,String cliendid) {
    	IAliasResult bAliasResult =push.queryClientId(appId, username);
    	List<String> cliendids=bAliasResult.getClientIdList();
    	if (cliendids==null) {
    		push.bindAlias(appId, username, cliendid);
		}else {
			if (!cliendids.contains(cliendid)) {//如果换了手机
				push.unBindAliasAll(appId, username);//解绑以前的手机
				push.bindAlias(appId, username, cliendid);//绑定现在的手机
			}
		}
		//bAliasResult=
		System.out.println("绑定结果：" + username+" "+cliendid + "  错误码:" + bAliasResult.getErrorMsg());
		return new Result(bAliasResult.getErrorMsg());
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
    
    public static void main(String[] args) {
    	//setAlias("201706061110592710", "184293283e31c638e18082cd03c5e65e");
    	sendMessage("201706061110592710", "宋荣洋");
	}
   
}