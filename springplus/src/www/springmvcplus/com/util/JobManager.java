package www.springmvcplus.com.util;
import static org.quartz.CronScheduleBuilder.cronSchedule; 
import static org.quartz.JobBuilder.newJob; 
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

import www.springmvcplus.com.services.service.MyService;

import com.alibaba.fastjson.JSON;
import com.app.project.jobs.UserTripJobs;


public class JobManager {
	private static SchedulerFactory jobFactory = new StdSchedulerFactory(); 
	
	/**
	 * 添加任务
	 * @param jobId
	 * @param c
	 * @param timeRun
	 * @param map
	 */
	public static <T extends Job> void addJob(String jobId,Class<T> c,Date timeRun,Map<String, Object> map) {
		try {
			Scheduler scheduler = jobFactory.getScheduler();
			JobDetail job = newJob(c).withIdentity(jobId).build();
			JobDataMap jobDataMap = job.getJobDataMap();
			jobDataMap.putAll(map);
		    // Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger()
		      .withIdentity(jobId)
		      .startAt(timeRun)      
		      .build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			MyService myService = SpringBeanUtil.getBean(MyService.class);
			www.springmvcplus.com.modes.Job job2 = new www.springmvcplus.com.modes.Job();
			job2.setJobId(jobId);
			job2.setClassPath(c.getName());
			job2.setJsonData(JSON.toJSONString(map));
			job2.setServiceId(StringUtil.valueOf(map.get("serviceId")));
			job2.setServiceType(StringUtil.valueOf(map.get("serviceType")));
			job2.setState(0);
			job2.setUserId(StringUtil.valueOf(map.get("userId")));
			job2.setWarnDate(DateUtil.dateFormat(timeRun, "yyyy-MM-dd HH:mm:ss"));
			myService.save(job2);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//启动程序时初始化  任务
	public static <T extends Job> void initJob(String jobId,Class<T> c,Date timeRun,Map<String, Object> map) {
		try {
			Scheduler scheduler = jobFactory.getScheduler();
			JobDetail job = newJob(c).withIdentity(jobId).build();
			JobDataMap jobDataMap = job.getJobDataMap();
			jobDataMap.putAll(map);
		    // Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger()
		      .withIdentity(jobId)
		      .startAt(timeRun)      
		      .build();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除任务
	 * @param jobId
	 */
	 public static void removeJob(String jobId) {  
        try {  
            Scheduler scheduler = jobFactory.getScheduler();  
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobId));
            if (jobDetail != null) {
            	scheduler.pauseTrigger(TriggerKey.triggerKey(jobId));;// 停止触发器  
            	scheduler.unscheduleJob(TriggerKey.triggerKey(jobId));// 移除触发器  
            	scheduler.deleteJob(JobKey.jobKey(jobId));// 删除任务  
            	www.springmvcplus.com.modes.Job job2 = new www.springmvcplus.com.modes.Job();
            	job2.setJobId(jobId);
            	MyService myService = SpringBeanUtil.getBean(MyService.class);
            	myService.delete(job2);
			}
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
	}
	 /**
	  * 更新时间
	  * @param jobId
	  * @param timeRun
	  */
	 public static void updateJobTime(String jobId,Date timeRun) {  
	        try {  
	        	Scheduler scheduler = jobFactory.getScheduler();  
	        	JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobId));
	        	Class<? extends Job> jobClass = jobDetail.getJobClass();
	        	JobDataMap jobDataMap = jobDetail.getJobDataMap();
	            removeJob(jobId);
	            addJob(jobId, jobClass, timeRun, jobDataMap);
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	}
	/**
	 * 任务完成
	 * @param jobId
	 */
	 public static void finishJob(String jobId) {  
	        try {  
	            www.springmvcplus.com.modes.Job job2 = new www.springmvcplus.com.modes.Job();
				job2.setJobId(jobId);
				MyService myService = SpringBeanUtil.getBean(MyService.class);
				myService.delete(job2);
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	}
	
	public static Date getDateByVisitDate(String date,String type) {
		Date date2=null;
		if (date == null) {
			return null;
		}else {
			if (date.trim().length()==16) {
				date2=DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm");
			}else {
				date2=DateUtil.dateFormat(date, "yyyy-MM-dd HH:mm:ss");
			}
		}
		if (type.equals("不提醒")) {
			return null;
		}else if (type.equals("15分钟前")) {
			return DateUtil.dateMath(date2, DateUtil.Minute, -15);
		}else if (type.equals("1分钟前")) {//测试使用
			return DateUtil.dateMath(date2, DateUtil.Minute, -1);
		}else if (type.equals("1小时前")) {
			return DateUtil.dateMath(date2, DateUtil.Hour, -1);
		}else if (type.equals("2小时前")) {
			return DateUtil.dateMath(date2, DateUtil.Hour, -2);
		}else if (type.equals("3小时前")) {
			return DateUtil.dateMath(date2, DateUtil.Hour, -3);
		}else if (type.equals("24小时前")) {
			return DateUtil.dateMath(date2, DateUtil.Hour, -4);
		}else {//半小时前
			return DateUtil.dateMath(date2, DateUtil.Minute, -30);
		}
	}
	 
	public static void main(String[] args) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sb", "meinv");
		addJob("ceshijob", UserTripJobs.class, DateUtil.dateMath(new Date(), DateUtil.Second, 10),map);
		//removeJob("ceshijob");
		updateJobTime("ceshijob",DateUtil.dateMath(new Date(), DateUtil.Second, 120));
		//System.out.println("2017-06-22 15:26:08".length());
	}
	
}
