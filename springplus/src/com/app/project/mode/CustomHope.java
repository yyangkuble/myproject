package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("保险观念")
@Entity
@TableCreate
public class CustomHope {
	@ApiDesc("代码")
	String code;
	@ApiDesc("显示名称")
	String HopeDisplayName;
	@ApiDesc("排序代码")
	Double orderBy;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHopeDisplayName() {
		return HopeDisplayName;
	}
	public void setHopeDisplayName(String hopeDisplayName) {
		HopeDisplayName = hopeDisplayName;
	}
	public Double getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Double orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
