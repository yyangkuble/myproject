package com.app.project.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.project.jobs.UserTripJobs;

import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.modes.Job;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.JobManager;

@Service
public class PublicService {
	@Resource
	BaseDao baseDao;
	
	public void initJobs() {
		List<Job> list = baseDao.getListModels(Job.class);
		for (Job job : list) {
			Map<String,Object> map = JSON.parseObject(job.getJsonData(), Map.class);
			JobManager.initJob(job.getJobId(), UserTripJobs.class, DateUtil.dateFormat(job.getWarnDate(), "yyyy-MM-dd HH:mm:ss"),map );
		}
	}
	
}
