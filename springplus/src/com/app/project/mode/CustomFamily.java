package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("家庭关系")
@Entity
@TableCreate
public class CustomFamily {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("客户id")
	String customId;
	@ApiDesc("关系")
	String relationship;//关系
	@ApiDesc("名字")
	String name;//名字
	@ApiDesc("性别")
	String sex;//性别
	@ApiDesc("生日")
	String bithday;//生日
	@ApiDesc("身份证")
	String idCards;//身份证
	@ApiDesc("工作")
	String work;//工作
	@ApiDesc("年收入")
	String yearIncome;//年收入
	@ApiDesc("兴趣")
	String interest;//兴趣
	
	
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
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBithday() {
		return bithday;
	}
	public void setBithday(String bithday) {
		this.bithday = bithday;
	}
	public String getIdCards() {
		return idCards;
	}
	public void setIdCards(String idCards) {
		this.idCards = idCards;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getYearIncome() {
		return yearIncome;
	}
	public void setYearIncome(String yearIncome) {
		this.yearIncome = yearIncome;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	
}
