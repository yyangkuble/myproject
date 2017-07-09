package com.app.project.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import www.springmvcplus.com.util.JobManager;

import com.alibaba.fastjson.JSON;

public class UserTripJobs implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail jobDetail = arg0.getJobDetail();
		System.out.println(JSON.toJSON(jobDetail.getJobDataMap()));
		JobDataMap map = jobDetail.getJobDataMap();
		if (map.getString("serviceType").equals("userTrip")) {
			JobManager.finishJob(map.getString("serviceId")+map.getString("serviceType"));
			
		}else {
			JobManager.finishJob(map.getString("serviceId")+map.getString("userId")+map.getString("serviceType"));
			
		}
	}

}
