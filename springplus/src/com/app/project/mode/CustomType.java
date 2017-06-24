package com.app.project.mode;

import javax.persistence.Entity;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@Entity
@ApiDesc("客户类别")
public class CustomType {
	@ApiDesc("code")
	String code;
	@ApiDesc("客户类别显示名称")
	String CustomTypeName;
	Double orderBy;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCustomTypeName() {
		return CustomTypeName;
	}
	public void setCustomTypeName(String customTypeName) {
		CustomTypeName = customTypeName;
	}
	public Double getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Double orderBy) {
		this.orderBy = orderBy;
	}

	
}
