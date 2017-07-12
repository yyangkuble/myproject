package com.app.project.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.ResponseUtil;

import com.alipay.api.AlipayRequest;
import com.app.project.mode.AlipayParameter;
import com.app.project.mode.UserPayLog;
import com.app.project.pay.PayBizInfo;
import com.app.project.pay.PayRequest;

@Controller
@RequestMapping("/pay")
public class AlipayAction {
	
	@Resource
	MyService myService;
	
	@RequestMapping("/alipay")
	public void alipayReturnUrl(AlipayParameter alipayParameter,HttpServletResponse response) {
		myService.save(alipayParameter);
		ResponseUtil.print(response, alipayParameter);
	}
	/**
	 * 获取签名的商品信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPayInfo")
	public void getPayInfo(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> map = AESUtil.converParameter(request);
		String payType=map.get("biz_type");
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
			userPayLog.setBiz_id(map.get("biz_id"));
		}
		userPayLog.setId(payBizInfo.getOut_trade_no());
		userPayLog.setTitle(payBizInfo.getSubjec());
		userPayLog.setContext(payBizInfo.getBody());
		userPayLog.setPayMoney(payBizInfo.getTotal_amount());
		String userId=map.get("userId");
		userPayLog.setBiz_type(payType);
		userPayLog.setUserId(userId);
		userPayLog.setPayState(0);
		userPayLog.setPayTime(payRequest.getTimestamp());
		myService.save(userPayLog);
		payRequest.setBiz_content(payBizInfo);
		ResponseUtil.print(response, payRequest);
	}
	
	@RequestMapping("/payState")
	public void payState(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		String id = parameter.get("id");
		UserPayLog userPayLog = myService.getModel("select * from userPayLog where id='"+id+"'", UserPayLog.class);
		ResponseUtil.print(response, userPayLog);
	}
	
}
