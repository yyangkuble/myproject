package cn.itcast.exception;

public class CustomException extends Exception {
	
	Integer code;
	String message;//±ØÐë½Ðmessage
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
