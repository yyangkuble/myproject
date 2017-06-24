package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("拜访项目")
@Entity
@TableCreate
public class VisitProject {
	@ApiDesc("代码")
	String code;
	@ApiDesc("项目显示名称")
	String projectName;
	@ApiDesc("排序代码")
	Double orderBy;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Double getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Double orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
