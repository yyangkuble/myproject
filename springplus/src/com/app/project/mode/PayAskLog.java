package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("客户表")
@Entity
public class PayAskLog {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	String userId;
	String payAskId;
	
}
