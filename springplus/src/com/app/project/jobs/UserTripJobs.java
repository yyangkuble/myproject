package com.app.project.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.JobManager;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.NotifyMessage;
import com.app.project.mode.User;
import com.app.project.util.Getui;

public class UserTripJobs implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		MyService myService = SpringBeanUtil.getBean(MyService.class);
		JobDetail jobDetail = arg0.getJobDetail();
		System.out.println(JSON.toJSON(jobDetail.getJobDataMap()));
		JobDataMap map = jobDetail.getJobDataMap();
		String userId=map.getString("userId");
		User user=myService.getModel("select * from user where id='"+userId+"'", User.class);
		String serviceId=map.getString("serviceId");
		if (map.getString("serviceType").equals("userTrip")) {
			JobManager.finishJob(map.getString("serviceId")+map.getString("serviceType"));
			String message=myService.getSingleResult("select mark from usertrip where id='"+serviceId+"'");
			if (!StringUtil.valueOf(user.getTripWarn()).equals("不提醒")) {
				Getui.sendMessage(userId, message);
			}
			NotifyMessage notifyMessage=new NotifyMessage();
			notifyMessage.setContext(message);
			notifyMessage.setImgUrl(user.getImgUrl());
			notifyMessage.setIsread(0);
			notifyMessage.setNotifyType("用户行程");
			notifyMessage.setTitle("用户行程");
			notifyMessage.setToUserId(userId);
			myService.save(notifyMessage);
		}else {
			JobManager.finishJob(map.getString("serviceId")+map.getString("userId")+map.getString("serviceType"));
			String message=myService.getSingleResult("select title from grouptrip where id='"+serviceId+"'");
			if (!StringUtil.valueOf(user.getTripWarn()).equals("不提醒")) {
				Getui.sendMessage(userId, message);
			}
			NotifyMessage notifyMessage=new NotifyMessage();
			notifyMessage.setContext(message);
			notifyMessage.setImgUrl(user.getImgUrl());
			notifyMessage.setIsread(0);
			notifyMessage.setNotifyType("用户行程");
			notifyMessage.setTitle("用户行程");
			notifyMessage.setToUserId(userId);
			myService.save(notifyMessage);
		}
	}

}
