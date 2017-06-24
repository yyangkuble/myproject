package www.springmvcplus.com.common;

import www.springmvcplus.com.util.MyJSON;

import com.alibaba.fastjson.JSON;

public class SqlResult {
	
	public SqlResult() {
		// TODO Auto-generated constructor stub
	}
	public SqlResult(String sql,Object[] paramters) {
		// TODO Auto-generated constructor stub
		this.sql=sql;
		this.paramters=paramters;
	}
	String sql;
	Object[] paramters;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getParamters() {
		return paramters;
	}
	public void setParamters(Object[] paramters) {
		this.paramters = paramters;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MyJSON.toJSONString(this);
	}
}
