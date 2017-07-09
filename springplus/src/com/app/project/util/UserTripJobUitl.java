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
import com.app.project.mode.GroupTrip;
import com.app.project.mode.User;
import com.app.project.mode.UserTrip;

public class UserTripJobUitl {
	public static void addOrUpdateJob(UserTrip userTrip,String serviceType) {
		MyService myService = SpringBeanUtil.getBean(MyService.class);
		String isjob = myService.getSingleResult("select count(*) from job where jobid = '"+userTrip.getId()+serviceType+"'");
		if (isjob.equals("1")) {//修改
			String tripWarn = myService.getSingleResult("select tripWarn from user where id='"+userTrip.getUserId()+"'");
			Date warnDate = JobManager.getDateByVisitDate(userTrip.getVisitTime(), tripWarn);
			if (warnDate != null) {
				JobManager.updateJobTime(userTrip.getId()+serviceType, warnDate);
			}
		}else {//添加
			Map<String, Object> jobMap=new HashMap<>();
			jobMap.put("serviceId", userTrip.getId());
			jobMap.put("userId", userTrip.getUserId());
			jobMap.put("serviceType", serviceType);
			String tripWarn = myService.getSingleResult("select tripWarn from user where id='"+userTrip.getUserId()+"'");
			Date warnDate = JobManager.getDateByVisitDate(userTrip.getVisitTime(), tripWarn);
			if (warnDate != null) {
				JobManager.addJob(userTrip.getId()+serviceType, UserTripJobs.class,warnDate, jobMap);
			}
		}
	}
	
	public static void deleteJob(UserTrip userTrip,String serviceType) {
		JobManager.removeJob(userTrip.getId()+serviceType);
	}
	public static void addOrUpdateJob(GroupTrip userTrip,String serviceType) {
		MyService myService = SpringBeanUtil.getBean(MyService.class);
		String tripUsers = userTrip.getTripUsers().toString();
		for (String userId : tripUsers.split(",")) {
			String isjob = myService.getSingleResult("select count(*) from job where jobid = '"+userTrip.getId()+userId+serviceType+"'");
			if (isjob.equals("1")) {//修改
				String tripWarn = myService.getSingleResult("select tripWarn from user where id='"+userId+"'");
				Date warnDate = JobManager.getDateByVisitDate(userTrip.getStartTime(), tripWarn);
				if (warnDate != null) {
					JobManager.updateJobTime(userTrip.getId()+userId+serviceType, warnDate);
				}
			}else {//添加
				Map<String, Object> jobMap=new HashMap<>();
				jobMap.put("serviceId", userTrip.getId());
				jobMap.put("userId", userId);
				jobMap.put("serviceType", serviceType);
				String tripWarn = myService.getSingleResult("select tripWarn from user where id='"+userId+"'");
				Date warnDate = JobManager.getDateByVisitDate(userTrip.getStartTime(), tripWarn);
				if (warnDate != null) {
					JobManager.addJob(userTrip.getId()+userId+serviceType, UserTripJobs.class,warnDate, jobMap);
				}
			}
		}
	}
	
	public static void deleteJob(GroupTrip userTrip,String serviceType,String userId) {
		JobManager.removeJob(userTrip.getId()+userId+serviceType);
	}
}
