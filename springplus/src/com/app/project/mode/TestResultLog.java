package com.app.project.mode;

import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
@TableCreate
@Table(name="test_resultlog")
@ApiDesc("测试结果记录")
public class TestResultLog {
	@Id
	String id;
	@ApiDesc("测试人id")
	String testerUserId;
	@ApiDesc("被测试人姓名")
	String testedName;
	@ApiDesc("被测试人的客户id")
	String testedCustomId;
	@ApiDesc("被测试人电话")
	String testedTel;
	@ApiDesc("被测试人生日")
	String testedBirthday;
	@ApiDesc("被测试人星座")
	String testedZodiac;
	@ApiDesc("测试时间")
	String testTime;
	@ApiDesc("综合评分")
	Integer score;
	@ApiDesc("综合评语")
	String comment;
	@Transient
	List<TestResultLogOther> list;
	
	
	public String getTestedCustomId() {
		return testedCustomId;
	}
	public void setTestedCustomId(String testedCustomId) {
		this.testedCustomId = testedCustomId;
	}
	public List<TestResultLogOther> getList() {
		return list;
	}
	public void setList(List<TestResultLogOther> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTesterUserId() {
		return testerUserId;
	}
	public void setTesterUserId(String testerUserId) {
		this.testerUserId = testerUserId;
	}
	public String getTestedName() {
		return testedName;
	}
	public void setTestedName(String testedName) {
		this.testedName = testedName;
	}
	public String getTestedTel() {
		return testedTel;
	}
	public void setTestedTel(String testedTel) {
		this.testedTel = testedTel;
	}
	public String getTestedBirthday() {
		return testedBirthday;
	}
	public void setTestedBirthday(String testedBirthday) {
		this.testedBirthday = testedBirthday;
	}
	public String getTestedZodiac() {
		return testedZodiac;
	}
	public void setTestedZodiac(String testedZodiac) {
		this.testedZodiac = testedZodiac;
	}
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
