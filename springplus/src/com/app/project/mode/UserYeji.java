package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("用户业绩表")
@Entity
public class UserYeji {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("用户id")
	String userId;
	@ApiDesc("创建时间自动生成")
	String createTime;
	@ApiDesc("业绩的年月， 格式为： 2017-06")
	String yejiDate;
	@ApiDesc("薪水")
	Double salary;
	@ApiDesc("保费")
	Double premium;
	@ApiDesc("件数")
	Integer policyNumber;
	@Transient
	@ApiDesc("访量，后台计算，增加和修改的时候不要传")
	Integer visitCount;
	@Transient
	Integer yejimonth;
	
	
	public Integer getYejimonth() {
		return yejimonth;
	}
	public void setYejimonth(Integer yejimonth) {
		this.yejimonth = yejimonth;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getYejiDate() {
		return yejiDate;
	}
	public void setYejiDate(String yejiDate) {
		this.yejiDate = yejiDate;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Integer getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(Integer policyNumber) {
		this.policyNumber = policyNumber;
	}
	
	
	
}
