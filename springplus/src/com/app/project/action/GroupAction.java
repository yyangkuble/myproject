package com.app.project.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.NotifyMessage;
import com.app.project.mode.TestResultLog;
import com.app.project.mode.TestResultLogOther;
import com.app.project.mode.User;
import com.app.project.util.PublicUtil;
import com.app.project.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.ApiParameter;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.StringUtil;

@Controller
@RequestMapping("/group")
public class GroupAction {
	@Resource
	MyService myService;
	//userId，context，title，groupId
	/**
	 * 申请加入团队
	 * @param request
	 * @param response
	 */
	@RequestMapping("/groupAddUser")
	public void groupAddUser(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Integer groupId=Integer.valueOf(parameter.get("groupId"));
		String title=parameter.get("title");
		String userId=parameter.get("userId");
		Result result = new Result();
		NotifyMessage message = new NotifyMessage();
		message.setImgUrl(myService.getSingleResult("select imgUrl from user where id='"+userId+"'"));
		message.setIsread(0);
		message.setNotifyType(1);
		message.setTitle(title);
		message.setToUserId(myService.getSingleResult("select id from user where groupId="+groupId+" and groupAuth=0"));
		message.setFromUserId(userId);
		Map<String, Object> json=new HashMap<>();
		json.put("groupId", groupId);
		message.setJsonData(MyJSON.toJSONString(json));
		//判断是否重复申请加入组
		String sql="select count(*) from NotifyMessage where fromUserId='"+message.getFromUserId()+"' and toUserId ='"+message.getToUserId()+"' and NotifyType="+message.getNotifyType()+"";
		Integer isaddedCount = Integer.valueOf(myService.getSingleResult(sql));
		if (isaddedCount >0) {
			result.setErrorCode(0);
			result.setErrorMessage("已经添加过");
		}else {
			int save = myService.save(message);
			if (save ==0) {
				result.setErrorCode(0);
				result.setErrorMessage("系统故障");
			}else {
				result.setData(message);
			}
		}
		ResponseUtil.print(response, result);
	}
	
}
