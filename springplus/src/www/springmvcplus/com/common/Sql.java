package www.springmvcplus.com.common;

import java.util.Map;

public class Sql {
	String id;
	String des;
	String modename;
	Map<String, String> api;
	String sqlTemplate;
	
	
	public String getSqlTemplate() {
		return sqlTemplate;
	}
	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getModename() {
		return modename;
	}
	public void setModename(String modename) {
		this.modename = modename;
	}
	public Map<String, String> getApi() {
		return api;
	}
	public void setApi(Map<String, String> api) {
		this.api = api;
	}
	
	
}
