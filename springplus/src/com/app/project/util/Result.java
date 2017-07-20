package com.app.project.util;

import java.util.List;
import java.util.Map;

public class Result {
	int errorCode=0;
	String errorMessage;
	Integer page;
	Integer max;
	Object data="";
	Long count;
	public Result() {
		// TODO Auto-generated constructor stub
	}
	public Result(int errorCode,Object data) {
		// TODO Auto-generated constructor stub
		this.errorCode=errorCode;
		this.data=data;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Result(Object data){
		this.data=data;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
