package com.app.project.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.project.mode.PayAsk;
import com.app.project.mode.PayAskLog;
import com.app.project.mode.PayAskYes;
import com.app.project.util.Result;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.ResponseUtil;

@Controller
@RequestMapping("/payAsk")
public class PayAskAction {
	@Resource
	MyService myService;
	
	@RequestMapping("/savePayAsk")
	public void savePayAsk(HttpServletRequest request,HttpServletResponse response) {
		PayAsk payAsk = AESUtil.converEntity(request, PayAsk.class);
		payAsk.setAskTime(DateUtil.getDate());
		myService.save(payAsk);
		ResponseUtil.print(response, new Result(payAsk));
	}
	
	@RequestMapping("/updatePayAsk")
	public void updatePayAsk(HttpServletRequest request,HttpServletResponse response) {
		PayAsk payAsk = AESUtil.converEntity(request, PayAsk.class);
		myService.update(payAsk);
		PayAsk result = myService.getModelById(payAsk);
		ResponseUtil.print(response, new Result(result));
	}
	
	@RequestMapping("/deletePayAsk")
	public void deletePayAsk(HttpServletRequest request,HttpServletResponse response) {
		PayAsk payAsk = AESUtil.converEntity(request, PayAsk.class);
		myService.delete(payAsk);
		ResponseUtil.print(response, new Result(1));
	}
	/**
	 * 添加专家回答
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addAnswer")
	public void addAnswer(HttpServletRequest request,HttpServletResponse response) {
		PayAsk payAsk = AESUtil.converEntity(request, PayAsk.class);
		payAsk.setAnswerTime(DateUtil.getDate());
		payAsk.setListenNumber(0);
		payAsk.setYesNumber(0);
		myService.update(payAsk);
		PayAsk result = myService.getModelById(payAsk);
		ResponseUtil.print(response, new Result(result));
	}
	
	/**
	 * 点赞
	 * @param request
	 * @param response
	 */
	@RequestMapping("/yes")
	public void yes(HttpServletRequest request,HttpServletResponse response) {
		PayAskYes payAskYes = AESUtil.converEntity(request, PayAskYes.class);
		String string = myService.getSingleResult("select count(*) from payaskyes where userid = '"+payAskYes.getUserId()+"' and payaskid='"+payAskYes.getPayAskId()+"'");
		if (string.equals("0")) {
			myService.save(payAskYes);
			myService.update("update payask set yesNumber=yesNumber+1 where id ='"+payAskYes.getPayAskId()+"'");
			PayAsk payAsk = new PayAsk();
			payAsk.setId(payAskYes.getPayAskId());
			PayAsk result = myService.getModelById(payAsk);
			ResponseUtil.print(response, new Result(result));
		}else {
			Result result = new Result();
			result.setErrorCode(1);
			result.setErrorMessage("重复点赞");
			ResponseUtil.print(response, result);
		}
	}
	
	
	
}
