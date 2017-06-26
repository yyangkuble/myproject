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
import com.app.project.jobs.TestJobs;


public class TaskManager {
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
			job2.setClassPath(c.getTypeName());
			job2.setJsonData(JSON.toJSONString(map));
			job2.setServiceId(StringUtil.valueOf(map.get("serviceId")));
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
	/**
	 * 删除任务
	 * @param jobId
	 */
	 public static void removeJob(String jobId) {  
        try {  
            Scheduler scheduler = jobFactory.getScheduler();  
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobId));;// 停止触发器  
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobId));// 移除触发器  
            scheduler.deleteJob(JobKey.jobKey(jobId));// 删除任务  
            www.springmvcplus.com.modes.Job job2 = new www.springmvcplus.com.modes.Job();
			job2.setJobId(jobId);
			MyService myService = SpringBeanUtil.getBean(MyService.class);
			myService.delete(job2);
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
				job2.setState(1);
				MyService myService = SpringBeanUtil.getBean(MyService.class);
				myService.update(job2);
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	}
	 
	public static void main(String[] args) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sb", "meinv");
		addJob("ceshijob", TestJobs.class, DateUtil.dateMath(new Date(), DateUtil.Second, 10),map);
		//removeJob("ceshijob");
		updateJobTime("ceshijob",DateUtil.dateMath(new Date(), DateUtil.Second, 120));
	}
	
}
