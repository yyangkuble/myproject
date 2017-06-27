package com.app.project.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.JobManager;
import www.springmvcplus.com.util.SpringBeanUtil;

import com.app.project.jobs.UserTripJobs;
import com.app.project.mode.User;
import com.app.project.mode.UserTrip;

public class UserTripJobUitl {
	public static void addJob(UserTrip userTrip) {
		Map<String, Object> jobMap=new HashMap<>();
		jobMap.put("serviceId", userTrip.getId());
		jobMap.put("userId", userTrip.getUserId());
		jobMap.put("serviceType", "userTrip");
		
		
		
		JobManager.addJob(IdManage.getTimeUUid(), UserTripJobs.class, DateUtil.dateFormat(userTrip.getVisitTime(), "yyyy-MM-dd HH:mm:ss"), jobMap);
	}
	
	public static Date getTripWarn(UserTrip userTrip) {
		MyService myService = SpringBeanUtil.getBean(MyService.class);
		String tripWarn = myService.getSingleResult("select tripWarn from user where id='"+userTrip.getUserId()+"'");
		//不提醒，15分钟前，半小时前，1小时前，2小时前，3小时前，24小时前
		if (tripWarn.equals("不提醒")) {
			return null;
		}else if (tripWarn.equals("15分钟前")) {
			//return DateUtil.dateMath(DateUtil.dateFormat(userTrip.getVisitTime(), "yyyy-MM-"), type, count);
		}else if (tripWarn.equals("半小时前")) {
			
		}else if (tripWarn.equals("1小时前")) {
			
		}else if (tripWarn.equals("2小时前")) {
			
		}else if (tripWarn.equals("3小时前")) {
			
		}else if (tripWarn.equals("24小时前")) {
			
		}else {
			
		}
		return null;
	}
	
}
