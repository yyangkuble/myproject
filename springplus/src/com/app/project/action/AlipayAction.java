package com.app.project.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import www.springmvcplus.com.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.app.project.mode.AlipayParameter;
import com.app.project.mode.PayAskLog;
import com.app.project.mode.User;
import com.app.project.mode.UserFriendsAsk;
import com.app.project.mode.UserPayLog;
import com.app.project.mode.WeiXinParameter;
import com.app.project.pay.PayBizInfo;
import com.app.project.pay.PayRequest;
import com.app.project.util.Result;

@Controller
@RequestMapping("/pay")
public class AlipayAction {
	private String alipayAppId="2017071407748745";
    private String alipayPrivateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJWt7bv3dG+F69WilkZyeRt9BXbFNpKnVhRv2eIKc29Nt4+fNC/jACrbYiA94SZQEa7Spdu6+sK7M7DbH2r+9BdVvqffPQpfRGcS1MmkKih12PHGSkrZ5PpbynEbZQrlbIijp+pyhBw4rc0KESXDIC7PjLgCxI0HRE45gT+7Km31YWpyGGDreXePPBv8q1AAJorDa0gcLOPg3qAz+X1koTFDBG900FHB+Pmh3ZaVINUospXWxqJq8etXIK7azpnCfHsvS9t5DtqReAzir8+0/aFH+n45kO8seVrImLbbne0PJSIv9Yd805dqt4gW1L+zaBfPYsV80O7fdCFBydiWV1AgMBAAECggEAIqPYMHNJEYzx1681YSzivc0trd6T6qHamH3e2FJD2YhHEWt3/h083nAQzuNKzjjK3o+Rb7I1y6X5vZrmluAn5spCNBEvvB8eS+WfFwKQa4zX+4+dkip3En8p5LiC0jYljM3PksF1VaCLFMVI2eiGdFZvKGNax8JKkFUVtXR0ycCJVSjThhmFfJ4gKnGxEswAgUykyHcqCx9HBUnxLuxD7xT4aEjV7tarWa9NWCzkmCMN+DLLYl0YUbDMRw7MIO8WwpC8dpSZMqVDD2mTJFt5SwTJKVSEca58HIJZ0euS7MIweRaqfzDgXcvAuPdj3lEnhCHsJEDeORG4nZJd9hSowQKBgQDfxP3lxpGT6BowGwqcZFXwb5kSUIaXCSsSDCEnmAKXxlO2sXZLgT9v00ItD7q1/YOiKZJnB5OFCPTw86Jed3O6CcAZhcv8/F0M+Qa6EfcRZvb7UmKi0LWCbxuzJX95orUl3VmtgIPq3ySAsO275VVAW5yp7Ge9h1nypEZiJk93uwKBgQCdI4hi66WaS/AJpu1CEkIug6a/8YzZ9AQU03WyAdlaJbS2GFWqKbUf/ljSr4CQlYN6S9zPCDyCVA/Wtzcu+gnT0aiGWZuI7Rugo1bj8H13Ga5tkEYDxv3cpbji+I7DsvvfXSH8AaiNnkjF1865PizvIIaVA4P40uq6xsWelC5MjwKBgQCEC4nL2pYzUoaJlpt3WBoCbMhGL3CMleNtney+oYv+JhFmQGhO+/EEOwTU9HA4TmYr/h4fYDAkE/n+abaJyjFnObO7G+IY4o7CRf07Nbi28yyRd7cu3rwNQSV2XI1RqLr+ohT2Nl0h7xVqP326IAVjmevjtdYt2D4y2c+SwYqpWwKBgFVDOnWyNyEJoy0ZjhMTpSVn+cqcXjjE1pIWSv5TUoQ/gVZszc6O4uCBOeDXqYLKHZT2JNGRPoPY6N8wepjawwpT8IU1idc6EIuRFUyI6Qr+vE5mwha6mnRm13MQOyakr1X7Sr1aiQKOqB3xgxGwUuFNXLjuN2WDzCmcQQ5SiOyxAoGAOCzWrM5FoKbD//yW0hRfuUb8/mJGkkO8469jj3lqr4Ty5splFs/IQkjMtWqiQySTeVCIm5jp+/UDH0L2DeD0ZE9fznTwrajIS/pgE/0vo0kTa9JZMvikdNWNAENrRZ9+NjBbYY1aoWG/IsEKfJ7WK03mNX2/VnKkXtQQtvK06c8=";
    private String alipayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiVre2793RvhevVopZGcnkbfQV2xTaSp1YUb9niCnNvTbePnzQv4wAq22IgPeEmUBGu0qXbuvrCuzOw2x9q/vQXVb6n3z0KX0RnEtTJpCooddjxxkpK2eT6W8pxG2UK5WyIo6fqcoQcOK3NChElwyAuz4y4AsSNB0ROOYE/uypt9WFqchhg63l3jzwb/KtQACaKw2tIHCzj4N6gM/l9ZKExQwRvdNBRwfj5od2WlSDVKLKV1saiavHrVyCu2s6Zwnx7L0vbeQ7akXgM4q/PtP2hR/p+OZDvLHlayJi2253tDyUiL/WHfNOXareIFtS/s2gXz2LFfNDu33QhQcnYlldQIDAQAB";
	
	@Resource
	MyService myService;
	//支付宝返回通知
	@RequestMapping("/alipay")
	public void alipayReturnUrl(HttpServletRequest request,HttpServletResponse response) throws AlipayApiException, IOException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		  //乱码解决，这段代码在出现乱码时使用。
		  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		  params.put(name, valueStr);
		 }
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
		AlipayParameter alipayParameter = JSON.parseObject(JSON.toJSONString(params), AlipayParameter.class);
		//判断是否已经处理过这个订单，这个订单是否成功了
		Integer handlerCount = Integer.valueOf(myService.getSingleResult("select count(*) from alipayParameter where trade_no ='"+alipayParameter.getTrade_no()+"' and trade_status='"+alipayParameter.getTrade_status()+"'"));
		if (handlerCount==0) {//没处理过进行处理
			//修改交易状态
			UserPayLog userPayLog = new UserPayLog();
			userPayLog.setId(alipayParameter.getOut_trade_no());
			userPayLog.setPayState(1);
			myService.update(userPayLog);
			handlerPayBiz(alipayParameter.getOut_trade_no());
		}
		
		myService.save(alipayParameter);
		
		
		response.getWriter().print("success");
	}
	//初始化支付业务
	public void handlerPayBiz(String payId) {
		UserPayLog payLog=myService.getModel("select * from UserPayLog where id='"+payId+"'", UserPayLog.class);
		//支付的业务类型，1:vip  296元，2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
		if (payLog.getBiz_type().equals("1")) {//业务： 被邀请人送50元，邀请人送100，会员周期1年
			User user = new User();
			user.setId(payLog.getUserId());
			user.setUserLevel(2);//用户类别， 1：试用用户   2：vip用户  3：vip过期用户  4：试用结束 未支付vip
			user.setVipTimeEnd(DateUtil.dateMath(DateUtil.getDate(), DateUtil.Year, 1, "yyyy-MM-dd HH:mm:ss"));//1年过期
			String userlevel=myService.getSingleResult("select userlevel from user where id='"+user.getId()+"'");
			if (StringUtil.hashText(userlevel) && userlevel.equals("1")) {//如果这个用户是第一次充值vip
				//查询这个用户是否是被邀请来的
				UserFriendsAsk userFriendsAsk=myService.getModel("select * from UserFriendsAsk where friendUserId='"+user.getId()+"'", UserFriendsAsk.class);
				if (userFriendsAsk != null) {//被邀请来的送50元，邀请人送100
					//给自己加50
					myService.update("update user set money=money+50,historyMoney=historyMoney+50 where id='"+user.getId()+"'");
					//给邀请人加100
					myService.update("update user set money=money+100,historyMoney=historyMoney+100 where id='"+user.getId()+"'");
					//修改邀请人的 邀请记录
					userFriendsAsk.setFriendMoney(50);
					userFriendsAsk.setIsVip(1);
					userFriendsAsk.setMyMoney(100);
					myService.update(userFriendsAsk);
				}
			}
			myService.update(user);
		}else if (payLog.getBiz_type().equals("4")) {
			//添加支付记录
			PayAskLog payAskLog=new PayAskLog();
			payAskLog.setUserId(payLog.getUserId());
			payAskLog.setPayAskId(payLog.getBiz_id());
			myService.update(payAskLog);
		}
	}
	
	//微信返回通知
	@RequestMapping("/weixin")
	public void weixinReturnUrl(WeiXinParameter weiXinParameter,HttpServletResponse response) throws IOException {
		myService.save(weiXinParameter);
		response.getWriter().print("<xml><return_code><![CDATA["+weiXinParameter.getReturn_code()+"]]></return_code><return_msg><![CDATA["+weiXinParameter.getReturn_msg()+"]]></return_msg></xml>");
	}
	
	public void name() {
		
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
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipayAppId, alipayPrivateKey, "json", "UTF-8", alipayPublicKey, "RSA2");
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(payBizInfo.getBody());
			model.setSubject(payBizInfo.getSubjec());
			model.setOutTradeNo(payBizInfo.getOut_trade_no());
			model.setTotalAmount(payBizInfo.getTotal_amount());
			model.setProductCode("QUICK_MSECURITY_PAY");
			alipayRequest.setBizModel(model);
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			alipayRequest.setNotifyUrl(basePath+"pay/alipay");
			Map<String, Object> mapresult=new HashMap<String, Object>();
			Result result = new Result();
			try {
			        //这里和普通的接口调用不同，使用的是sdkExecute
			        AlipayTradeAppPayResponse alipayResponse = alipayClient.sdkExecute(alipayRequest);
			        System.out.println(alipayResponse.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
			        mapresult.put("payparam", alipayResponse.getBody());
			        mapresult.put("orderId", payBizInfo.getOut_trade_no());
			        result.setData(mapresult);
			        //添加数据库
			        userPayLog.setId(payBizInfo.getOut_trade_no());
					userPayLog.setTitle(payBizInfo.getSubjec());
					userPayLog.setContext(payBizInfo.getBody());
					userPayLog.setPayMoney(payBizInfo.getTotal_amount());
					userPayLog.setBiz_type(payType);
					userPayLog.setUserId(userId);
					userPayLog.setPayState(0);
					userPayLog.setPayTime(DateUtil.getDate());
					myService.save(userPayLog);
			    } catch (AlipayApiException e) {
			        e.printStackTrace();
			        result.setErrorCode(1);
			        result.setErrorMessage("支付宝出现了错误");
			}
			
			ResponseUtil.print(response, result);
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
