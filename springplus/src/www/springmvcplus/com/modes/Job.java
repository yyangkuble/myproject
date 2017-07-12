package www.springmvcplus.com.modes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("任务表")
@Entity
public class Job {
	@Id
	@ApiDesc("手动添加")
	String jobId;
	String classPath;
	String jsonData;
	String warnDate;
	Integer state;//1： 已完成     0：等待运行
	String userId;
	String serviceId;//业务id
	String serviceType;//userTrip:用户行程
	@Transient
	String servicetime;//业务行程的时间
	
	
	
	public String getServicetime() {
		return servicetime;
	}
	public void setServicetime(String servicetime) {
		this.servicetime = servicetime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getWarnDate() {
		return warnDate;
	}
	public void setWarnDate(String warnDate) {
		this.warnDate = warnDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
	
}
