package com.app.project.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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
import www.springmvcplus.com.util.JdomParseXmlUtils;
import www.springmvcplus.com.util.MD5Util;
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
import com.app.project.mode.UnifiedorderResult;
import com.app.project.mode.User;
import com.app.project.mode.UserFriendsAsk;
import com.app.project.mode.UserPayLog;
import com.app.project.mode.WeiXinParameter;
import com.app.project.pay.PayBizInfo;
import com.app.project.pay.PayRequest;
import com.app.project.util.Result;
import com.sun.net.ssl.HttpsURLConnection;

@Controller
@RequestMapping("/pay")
public class AlipayAction {
	private String alipayAppId="2017071407748745";
    private String alipayPrivateKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJWt7bv3dG+F69WilkZyeRt9BXbFNpKnVhRv2eIKc29Nt4+fNC/jACrbYiA94SZQEa7Spdu6+sK7M7DbH2r+9BdVvqffPQpfRGcS1MmkKih12PHGSkrZ5PpbynEbZQrlbIijp+pyhBw4rc0KESXDIC7PjLgCxI0HRE45gT+7Km31YWpyGGDreXePPBv8q1AAJorDa0gcLOPg3qAz+X1koTFDBG900FHB+Pmh3ZaVINUospXWxqJq8etXIK7azpnCfHsvS9t5DtqReAzir8+0/aFH+n45kO8seVrImLbbne0PJSIv9Yd805dqt4gW1L+zaBfPYsV80O7fdCFBydiWV1AgMBAAECggEAIqPYMHNJEYzx1681YSzivc0trd6T6qHamH3e2FJD2YhHEWt3/h083nAQzuNKzjjK3o+Rb7I1y6X5vZrmluAn5spCNBEvvB8eS+WfFwKQa4zX+4+dkip3En8p5LiC0jYljM3PksF1VaCLFMVI2eiGdFZvKGNax8JKkFUVtXR0ycCJVSjThhmFfJ4gKnGxEswAgUykyHcqCx9HBUnxLuxD7xT4aEjV7tarWa9NWCzkmCMN+DLLYl0YUbDMRw7MIO8WwpC8dpSZMqVDD2mTJFt5SwTJKVSEca58HIJZ0euS7MIweRaqfzDgXcvAuPdj3lEnhCHsJEDeORG4nZJd9hSowQKBgQDfxP3lxpGT6BowGwqcZFXwb5kSUIaXCSsSDCEnmAKXxlO2sXZLgT9v00ItD7q1/YOiKZJnB5OFCPTw86Jed3O6CcAZhcv8/F0M+Qa6EfcRZvb7UmKi0LWCbxuzJX95orUl3VmtgIPq3ySAsO275VVAW5yp7Ge9h1nypEZiJk93uwKBgQCdI4hi66WaS/AJpu1CEkIug6a/8YzZ9AQU03WyAdlaJbS2GFWqKbUf/ljSr4CQlYN6S9zPCDyCVA/Wtzcu+gnT0aiGWZuI7Rugo1bj8H13Ga5tkEYDxv3cpbji+I7DsvvfXSH8AaiNnkjF1865PizvIIaVA4P40uq6xsWelC5MjwKBgQCEC4nL2pYzUoaJlpt3WBoCbMhGL3CMleNtney+oYv+JhFmQGhO+/EEOwTU9HA4TmYr/h4fYDAkE/n+abaJyjFnObO7G+IY4o7CRf07Nbi28yyRd7cu3rwNQSV2XI1RqLr+ohT2Nl0h7xVqP326IAVjmevjtdYt2D4y2c+SwYqpWwKBgFVDOnWyNyEJoy0ZjhMTpSVn+cqcXjjE1pIWSv5TUoQ/gVZszc6O4uCBOeDXqYLKHZT2JNGRPoPY6N8wepjawwpT8IU1idc6EIuRFUyI6Qr+vE5mwha6mnRm13MQOyakr1X7Sr1aiQKOqB3xgxGwUuFNXLjuN2WDzCmcQQ5SiOyxAoGAOCzWrM5FoKbD//yW0hRfuUb8/mJGkkO8469jj3lqr4Ty5splFs/IQkjMtWqiQySTeVCIm5jp+/UDH0L2DeD0ZE9fznTwrajIS/pgE/0vo0kTa9JZMvikdNWNAENrRZ9+NjBbYY1aoWG/IsEKfJ7WK03mNX2/VnKkXtQQtvK06c8=";
    private String alipayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiVre2793RvhevVopZGcnkbfQV2xTaSp1YUb9niCnNvTbePnzQv4wAq22IgPeEmUBGu0qXbuvrCuzOw2x9q/vQXVb6n3z0KX0RnEtTJpCooddjxxkpK2eT6W8pxG2UK5WyIo6fqcoQcOK3NChElwyAuz4y4AsSNB0ROOYE/uypt9WFqchhg63l3jzwb/KtQACaKw2tIHCzj4N6gM/l9ZKExQwRvdNBRwfj5od2WlSDVKLKV1saiavHrVyCu2s6Zwnx7L0vbeQ7akXgM4q/PtP2hR/p+OZDvLHlayJi2253tDyUiL/WHfNOXareIFtS/s2gXz2LFfNDu33QhQcnYlldQIDAQAB";
    private String weixinAppId="2017071407748745";
    private String weixinMch_id="2017071407748745";//微信商户号
    private String weixinSpbill_create_ip="39.108.95.173";//用户实际ip地址
    
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
		//boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
		AlipayParameter alipayParameter = JSON.parseObject(JSON.toJSONString(params), AlipayParameter.class);
		//判断是否已经处理过这个订单，这个订单是否成功了
		Integer handlerCount = Integer.valueOf(myService.getSingleResult("select count(*) from alipayParameter where trade_no ='"+alipayParameter.getTrade_no()+"' and trade_status='TRADE_SUCCESS'"));
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
	public void weixinReturnUrl(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{  
            BufferedReader reader = request.getReader();  
  
            String line = "";  
            StringBuffer inputString = new StringBuffer();  
  
            try{  
                PrintWriter writer = response.getWriter();  
  
                while ((line = reader.readLine()) != null) {  
                    inputString.append(line);  
                }  
  
                if(reader!=null){  
                    reader.close();  
                }  
  
                System.out.println("----[微信回调]接收到的报文---"+inputString.toString());  
  
                if(StringUtil.hashText(inputString.toString())){  
                    WeiXinParameter alipayParameter = JdomParseXmlUtils.getWXPayResult(inputString.toString());  
                    myService.save(alipayParameter);
                    if("SUCCESS".equalsIgnoreCase(alipayParameter.getReturn_code())){  
                        writer.write(backWeixin("SUCCESS","OK")); 
                        Integer handlerCount = Integer.valueOf(myService.getSingleResult("select count(*) from WeiXinParameter where transaction_id ='"+alipayParameter.getTransaction_id()+"' and result_code='SUCCESS'"));
                		if (handlerCount==0) {//没处理过进行处理
                			//修改交易状态
                			UserPayLog userPayLog = new UserPayLog();
                			userPayLog.setId(alipayParameter.getOut_trade_no());
                			userPayLog.setPayState(1);
                			myService.update(userPayLog);
                			handlerPayBiz(alipayParameter.getOut_trade_no());
                		}
                		
                    }else{  
                        writer.write(backWeixin("FAIL",alipayParameter.getReturn_msg()));  
                          
                        System.out.println("---------微信支付返回Fail----------"+alipayParameter.getReturn_msg());  
                    }  
  
                    if(writer!=null){  
                        writer.close();  
                    }  
                }else{  
                    writer.write(backWeixin("FAIL","未获取到微信返回的结果"));  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
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
			UserPayLog userPayLog = new UserPayLog();
			String orderId=IdManage.getTimeUUid();
			
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
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
	        parameters.put("appid", weixinAppId);  
	        parameters.put("mch_id", weixinMch_id);  
	        parameters.put("nonce_str", orderId);  
	        parameters.put("body", title);  
	        parameters.put("detail", body);  
	        parameters.put("out_trade_no", orderId);  
	        parameters.put("total_fee", total_fee);  
	        parameters.put("notify_url", basePath+"pay/weixin");  
	        parameters.put("trade_type", "APP");  
	        parameters.put("spbill_create_ip", weixinSpbill_create_ip);
	        String sign=createSign("UTF-8", parameters);
	        parameters.put("sign", sign);  
			String xmlInfo = xmlInfo(parameters);
			String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";  
	        
	        String method = "POST";  
	          
	        String weixinPost = httpsRequest(wxUrl, method, xmlInfo).toString(); 
	        UnifiedorderResult unifiedorderResult = JdomParseXmlUtils.getUnifiedorderResult(weixinPost);
	        System.out.println(unifiedorderResult);
	        Map<String, Object> mapresult=new HashMap<String, Object>();
	        Result result = new Result();
	        mapresult.put("payparam", unifiedorderResult);
	        mapresult.put("orderId", orderId);
	        result.setData(mapresult);
	        
			userPayLog.setId(orderId);
			userPayLog.setTitle(title);
			userPayLog.setContext(body);
			userPayLog.setPayMoney(total_fee);
			userPayLog.setBiz_type(payType);
			userPayLog.setUserId(userId);
			userPayLog.setPayState(0);
			userPayLog.setPayTime(DateUtil.getDate());
			myService.save(userPayLog);
			ResponseUtil.print(response, result);
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
	/**返回给微信的消息
     * 
     * @param return_code
     * @param return_msg
     * @param output
     * @return
     */
    /*<xml> 
    <return_code><![CDATA[SUCCESS]]></return_code>
    <return_msg><![CDATA[OK]]></return_msg>
    </xml>*/
    public static String backWeixin(String return_code, String return_msg) {
        StringBuffer bf = new StringBuffer();
        bf.append("<xml>");

        bf.append("<return_code><![CDATA[");
        bf.append(return_code);
        bf.append("]]></return_code>");

        bf.append("<return_msg><![CDATA[");
        bf.append(return_msg);
        bf.append("]]></return_msg>");

        bf.append("</xml>");
        return bf.toString();
    }
	/** 
     * 微信支付签名算法sign 
     * @param characterEncoding 
     * @param parameters 
     * @return 
     */  
    @SuppressWarnings("rawtypes")  
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        System.out.println("字符串拼接后是："+sb.toString());  
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
        return sign;  
    }  
    
    /** 
     * post请求并得到返回结果 
     * @param requestUrl 
     * @param requestMethod 
     * @param output 
     * @return 
     */  
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {  
        try{  
            URL url = new URL(requestUrl);  
            javax.net.ssl.HttpsURLConnection connection = (javax.net.ssl.HttpsURLConnection) url.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setRequestMethod(requestMethod);  
            if (null != output) {  
                OutputStream outputStream = connection.getOutputStream();  
                outputStream.write(output.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = connection.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            connection.disconnect();  
            return buffer.toString();  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
  
        return "";  
    } 
    /** 
     * 构造xml参数 
     * @param xml 
     * @return 
     */  
    public String xmlInfo(SortedMap<Object,Object> parameters){  
        //构造xml参数的时候，至少有10个必传参数  
        /* 
         * <xml> 
               <appid>wx2421b1c4370ec43b</appid> 
               <attach>支付测试</attach> 
               <body>JSAPI支付测试</body> 
               <mch_id>10000100</mch_id> 
               <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str> 
               <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url> 
               <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid> 
               <out_trade_no>1415659990</out_trade_no> 
               <spbill_create_ip>14.23.150.211</spbill_create_ip> 
               <total_fee>1</total_fee> 
               <trade_type>JSAPI</trade_type> 
               <sign>0CB01533B8C1EF103065174F50BCA001</sign> 
            </xml> 
            parameters.put("appid", weixinAppId);  
	        parameters.put("mch_id", weixinMch_id);  
	        parameters.put("nonce_str", orderId);  
	        parameters.put("body", title);  
	        parameters.put("detail", body);  
	        parameters.put("out_trade_no", orderId);  
	        parameters.put("total_fee", total_fee);  
	        parameters.put("notify_url", basePath+"pay/weixin");  
	        parameters.put("trade_type", "APP");  
	        parameters.put("spbill_create_ip", weixinSpbill_create_ip);
	        String sign=createSign("UTF-8", parameters);
	        parameters.put("sign", sign);  
         */  
  
            StringBuffer bf = new StringBuffer();  
            bf.append("<xml>");  
  
            bf.append("<appid><![CDATA[");  
            bf.append(parameters.get("appid"));  
            bf.append("]]></appid>");  
  
            bf.append("<mch_id><![CDATA[");  
            bf.append(parameters.get("mch_id"));  
            bf.append("]]></mch_id>");  
  
            bf.append("<nonce_str><![CDATA[");  
            bf.append(parameters.get("nonce_str"));  
            bf.append("]]></nonce_str>");  
  
            bf.append("<sign><![CDATA[");  
            bf.append(parameters.get("sign"));  
            bf.append("]]></sign>");  
  
            bf.append("<body><![CDATA[");  
            bf.append(parameters.get("body"));  
            bf.append("]]></body>");  
  
            bf.append("<detail><![CDATA[");  
            bf.append(parameters.get("detail"));  
            bf.append("]]></detail>");  
  
           /* bf.append("<attach><![CDATA[");  
            bf.append(parameters.get(""));  
            bf.append("]]></attach>");  
  */
            bf.append("<out_trade_no><![CDATA[");  
            bf.append(parameters.get("out_trade_no"));  
            bf.append("]]></out_trade_no>");  
  
            bf.append("<total_fee><![CDATA[");  
            bf.append(parameters.get("total_fee"));  
            bf.append("]]></total_fee>");  
  
            bf.append("<spbill_create_ip><![CDATA[");  
            bf.append(parameters.get("spbill_create_ip"));  
            bf.append("]]></spbill_create_ip>");  
  
            /*bf.append("<time_start><![CDATA[");  
            bf.append(parameters.get(""));  
            bf.append("]]></time_start>");  
  
            bf.append("<time_expire><![CDATA[");  
            bf.append(parameters.get(""));  
            bf.append("]]></time_expire>");  
  */
            bf.append("<notify_url><![CDATA[");  
            bf.append(parameters.get("notify_url"));  
            bf.append("]]></notify_url>");  
  
            bf.append("<trade_type><![CDATA[");  
            bf.append(parameters.get("trade_type"));  
            bf.append("]]></trade_type>");  
  
  
            bf.append("</xml>");  
            return bf.toString();  
  
    }  
}
