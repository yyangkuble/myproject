package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("保单管理")
@TableCreate
@Entity
public class CustomPolicy {
	@Id
	@Generated
	String id;
	@ApiDesc("客户id")
	String customId;
	@ApiDesc("保险公司")
	String insuranceCompany;
	@ApiDesc("保险名称")
	String insuranceName;
	@ApiDesc("被保人")
	String insured;
	@ApiDesc("保费")
	Double premium;
	@ApiDesc("保额")
	Double coverage;
	@ApiDesc("期限")
	String protectYears;
	@ApiDesc("保险期限开始")
	String protectStartTime;
	@ApiDesc("保险期限结束")
	String protectEndTime;
	
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public String getInsured() {
		return insured;
	}
	public void setInsured(String insured) {
		this.insured = insured;
	}
	public double getPremium() {
		return premium;
	}
	public void setPremium(double premium) {
		this.premium = premium;
	}
	public double getCoverage() {
		return coverage;
	}
	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}
	public String getProtectYears() {
		return protectYears;
	}
	public void setProtectYears(String protectYears) {
		this.protectYears = protectYears;
	}
	public String getProtectStartTime() {
		return protectStartTime;
	}
	public void setProtectStartTime(String protectStartTime) {
		this.protectStartTime = protectStartTime;
	}
	public String getProtectEndTime() {
		return protectEndTime;
	}
	public void setProtectEndTime(String protectEndTime) {
		this.protectEndTime = protectEndTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public void setCoverage(Double coverage) {
		this.coverage = coverage;
	}
	
}
