package com.app.project.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;

public class UserTripJobs implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDetail jobDetail = arg0.getJobDetail();
		System.out.println(JSON.toJSON(jobDetail.getJobDataMap()));
	}

}
