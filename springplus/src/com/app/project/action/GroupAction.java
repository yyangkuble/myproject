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
import com.app.project.util.Getui;
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
	
	@RequestMapping("/group1to3")
	public void group1to3(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		String userId2=parameter.get("userId");
		String userid1 = myService.getSingleResult("select id from user where groupid = (select groupId from user where id = '"+userId2+"') and groupAuth=0");
		myService.update("update user set groupAuth=2 where id='"+userid1+"'");
		myService.update("update user set groupAuth=0 where id='"+userId2+"'");
		ResponseUtil.print(response, new Result());
	}
	
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
		message.setNotifyType("1");
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
			Getui.sendMessage(message.getToUserId(), message.getTitle());
			if (save ==0) {
				result.setErrorCode(0);
				result.setErrorMessage("系统故障");
			}else {
				result.setData(message);
			}
		}
		ResponseUtil.print(response, result);
	}
	//同意添加用户
	@RequestMapping("/addGroup")
	public void addGroupYes(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> map = AESUtil.converParameter(request);
		String notifyId = map.get("notifyId");
		String type=map.get("type");
		if (type.equals("yes")) {
			NotifyMessage notifyMessage = myService.getModel("select * from NotifyMessage where id='"+notifyId+"'", NotifyMessage.class);
			Map<String,Object> mapJson = JSON.parseObject(notifyMessage.getJsonData(), Map.class);
			mapJson.put("handleResult", "yes");
			notifyMessage.setJsonData(JSON.toJSONString(mapJson));
			myService.update(notifyMessage);
			Object groupId = mapJson.get("groupId");
			myService.update("update user set groupAuth=0 ,groupId="+groupId+" where id='"+notifyMessage.getFromUserId()+"'");
			ResponseUtil.print(response, new Result(notifyMessage));
		}else {
			NotifyMessage notifyMessage = myService.getModel("select * from NotifyMessage where id='"+notifyId+"'", NotifyMessage.class);
			Map<String,Object> mapJson = JSON.parseObject(notifyMessage.getJsonData(), Map.class);
			mapJson.put("handleResult", "no");
			notifyMessage.setJsonData(JSON.toJSONString(mapJson));
			myService.update(notifyMessage);
			ResponseUtil.print(response, new Result(notifyMessage));
		}
	}
	
	@RequestMapping("/groupRemoveUser")
	public void groupRemoveUser(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		String userId=parameter.get("userId");
		int update = myService.update("update user set groupAuth=null,groupId=null where id= '"+userId+"'");
		
		Result result = new Result();
		if (update != 1) {
			result.setErrorCode(1);
			result.setErrorMessage("系统发生错误");
		}else {
			Map<String, Object> map = myService.getMap("select * from user where id = '"+userId+"'");
			Getui.sendMessage(userId, "您已经被移除组");
			map.remove("password");
			result.setData(map);
		}
		
		ResponseUtil.print(response, result);
	}
}
