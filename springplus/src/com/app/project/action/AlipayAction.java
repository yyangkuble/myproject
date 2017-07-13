package com.app.project.action;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.system.JsoupUtil;

import com.alipay.api.AlipayRequest;
import com.app.project.mode.AlipayParameter;
import com.app.project.mode.UserPayLog;
import com.app.project.mode.WeiXinParameter;
import com.app.project.pay.PayBizInfo;
import com.app.project.pay.PayRequest;

@Controller
@RequestMapping("/pay")
public class AlipayAction {
	
	@Resource
	MyService myService;
	//支付宝返回通知
	@RequestMapping("/alipay")
	public void alipayReturnUrl(AlipayParameter alipayParameter,HttpServletResponse response) {
		myService.save(alipayParameter);
		ResponseUtil.print(response, alipayParameter);
	}
	//微信返回通知
	@RequestMapping("/weixin")
	public void weixinReturnUrl(WeiXinParameter weiXinParameter,HttpServletResponse response) throws IOException {
		myService.save(weiXinParameter);
		response.getWriter().print("<xml><return_code><![CDATA["+weiXinParameter.getReturn_code()+"]]></return_code><return_msg><![CDATA["+weiXinParameter.getReturn_msg()+"]]></return_msg></xml>");
	}
	/**
	 * 获取签名的商品信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/getPayAlipayInfo")
	public void getPayInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, String> map = AESUtil.converParameter(request);
		String paytype = map.get("paytype");
		String payType=map.get("biz_type");
		String userId=map.get("userId");
		String biz_id = map.get("biz_id");
		if (paytype.equals("weixin")) {
			Connection connect = Jsoup.connect("https://api.mch.weixin.qq.com/pay/unifiedorder");
			UserPayLog userPayLog = new UserPayLog();
			String orderId=IdManage.getTimeUUid();
			connect.data("appid", "");
			connect.data("mch_id", "");
			connect.data("nonce_str", orderId);
			connect.data("sign", "");
			connect.data("sign_type", "HMAC-SHA256");
			String title="";
			String body="";
			String total_fee="";
			if (payType.equals("1")) {//vip
				title="保险智库VIP会员";
				body="Vip用户可以享受所有功能";
				total_fee="0.01";
			}else if (payType.equals("2")) {//2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
				title="保险智库不公开提问题";
				body="您发表的问题将不公开";
				total_fee="0.01";
			}else if (payType.equals("3")) {//2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
				title="向保险大咖提出您的疑问";
				body="向保险大咖提出您的疑问";
				total_fee="0.01";
			}else if (payType.equals("4")) {
				userPayLog.setBiz_id(biz_id);
				title="聆听大咖回答的问题";
				body="聆听大咖回答的问题";
				total_fee="0.01";
			}
			connect.data("body", title);
			connect.data("detail", body);
			connect.data("total_fee", total_fee);
			
			connect.data("out_trade_no", orderId);
			connect.data("spbill_create_ip", "39.108.95.173");
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			connect.data("notify_url", basePath+"pay/weixin");
			connect.data("trade_type", "APP");
			
			userPayLog.setId(orderId);
			userPayLog.setTitle(title);
			userPayLog.setContext(body);
			userPayLog.setPayMoney(total_fee);
			userPayLog.setBiz_type(payType);
			userPayLog.setUserId(userId);
			userPayLog.setPayState(0);
			userPayLog.setPayTime(DateUtil.getDate());
			myService.save(userPayLog);
			Document document = connect.post();
			System.out.println(document.text());
		}else {
			PayRequest payRequest=new PayRequest(request);
			PayBizInfo payBizInfo=new PayBizInfo();
			UserPayLog userPayLog = new UserPayLog();
			if (payType.equals("1")) {//vip
				payBizInfo.setBody("Vip用户可以享受所有功能");//商品描述
				payBizInfo.setSubjec("保险智库VIP会员");
				payBizInfo.setTotal_amount("0.01");
			}else if (payType.equals("2")) {//2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
				payBizInfo.setBody("您发表的问题将不公开");//商品描述
				payBizInfo.setSubjec("保险智库不公开提问题");
				payBizInfo.setTotal_amount("0.01");
			}else if (payType.equals("3")) {//2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
				payBizInfo.setBody("向保险大咖提出您的疑问");//商品描述
				payBizInfo.setSubjec("向保险大咖提出您的疑问");
				payBizInfo.setTotal_amount("0.01");
			}else if (payType.equals("4")) {
				payBizInfo.setBody("聆听大咖回答的问题");//商品描述
				payBizInfo.setSubjec("聆听大咖回答的问题");
				payBizInfo.setTotal_amount("0.01");
				userPayLog.setBiz_id(biz_id);
			}
			userPayLog.setId(payBizInfo.getOut_trade_no());
			userPayLog.setTitle(payBizInfo.getSubjec());
			userPayLog.setContext(payBizInfo.getBody());
			userPayLog.setPayMoney(payBizInfo.getTotal_amount());
			userPayLog.setBiz_type(payType);
			userPayLog.setUserId(userId);
			userPayLog.setPayState(0);
			userPayLog.setPayTime(payRequest.getTimestamp());
			myService.save(userPayLog);
			payRequest.setBiz_content(payBizInfo);
			ResponseUtil.print(response, payRequest);
		}
	}
	
	@RequestMapping("/payState")
	public void payState(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		String id = parameter.get("id");
		UserPayLog userPayLog = myService.getModel("select * from userPayLog where id='"+id+"'", UserPayLog.class);
		ResponseUtil.print(response, userPayLog);
	}
	
}
