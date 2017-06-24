package com.app.project.mode;

import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
@TableCreate
@Table(name="test_resultlogOther")
@ApiDesc("测试结果记录")
public class TestResultLogOther {
	@ApiDesc("综合测试结果id")
	String resultLogId;
	@ApiDesc("项目类型大类")
	String projectType1;
	@ApiDesc("项目类型小类")
	String projectType2;
	@ApiDesc("项目评分")
	Integer score;
	@ApiDesc("项目评语")
	String comment;
	@ApiDesc("排序代码")
	Integer orderby;
	public String getResultLogId() {
		return resultLogId;
	}
	public void setResultLogId(String resultLogId) {
		this.resultLogId = resultLogId;
	}
	public String getProjectType1() {
		return projectType1;
	}
	public void setProjectType1(String projectType1) {
		this.projectType1 = projectType1;
	}
	public String getProjectType2() {
		return projectType2;
	}
	public void setProjectType2(String projectType2) {
		this.projectType2 = projectType2;
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
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	
}
