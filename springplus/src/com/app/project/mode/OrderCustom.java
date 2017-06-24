package com.app.project.mode;

import javax.persistence.Column;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@Table(name = "ordercustom")
@ApiDesc("基础表客户排序列表")
@TableCreate
public class OrderCustom {
	//排序代码
	@ApiDesc("代码")
	String code;
	//排序显示名称
	@Column(name="displayname")
	@ApiDesc("排序显示名称")
	String displayName;
	//搜索排序号码
	@Column(name="orderby")
	@ApiDesc("排序代码")
	Double orderBy;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Double getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Double orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
