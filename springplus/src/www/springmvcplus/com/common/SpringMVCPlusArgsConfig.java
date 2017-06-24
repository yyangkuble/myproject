package www.springmvcplus.com.common;

import www.springmvcplus.com.services.AppFastFilter;
public class SpringMVCPlusArgsConfig {
	private boolean deleteTable;
	private String entityRootPackage;
	private String xmlSqlRootPackage;
	private String sessionUserKey;
	private String user_username;
	private String user_name;
	private String noRedisTables;
	private boolean isCheckLogin;
	private String urlFilter;
	private boolean isRedis;
	private String redisHost;
	private int redisPort;
	private boolean isProduce;
	private String inputFilePath;
	private String fileRootUrl;
	private String sessionRedisHost;
	private int sessionRedisPort;
    private String aesRole;
	
    
	public boolean isCheckLogin() {
		return isCheckLogin;
	}


	public void setCheckLogin(boolean isCheckLogin) {
		this.isCheckLogin = isCheckLogin;
	}


	public String getAesRole() {
		return aesRole;
	}


	public void setAesRole(String aesRole) {
		this.aesRole = aesRole;
	}


	public boolean isDeleteTable() {
		return deleteTable;
	}


	public void setDeleteTable(boolean deleteTable) {
		this.deleteTable = deleteTable;
	}


	public String getEntityRootPackage() {
		return entityRootPackage;
	}


	public void setEntityRootPackage(String entityRootPackage) {
		this.entityRootPackage = entityRootPackage;
	}


	public String getXmlSqlRootPackage() {
		return xmlSqlRootPackage;
	}


	public void setXmlSqlRootPackage(String xmlSqlRootPackage) {
		this.xmlSqlRootPackage = xmlSqlRootPackage;
	}


	public String getSessionUserKey() {
		return sessionUserKey;
	}


	public void setSessionUserKey(String sessionUserKey) {
		this.sessionUserKey = sessionUserKey;
	}


	public String getUser_username() {
		return user_username;
	}


	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getNoRedisTables() {
		return noRedisTables.toLowerCase();
	}


	public void setNoRedisTables(String noRedisTables) {
		this.noRedisTables = noRedisTables;
	}


	public String getUrlFilter() {
		return urlFilter;
	}


	public void setUrlFilter(String urlFilter) {
		this.urlFilter = urlFilter;
	}


	public boolean isRedis() {
		return isRedis;
	}


	public void setRedis(boolean isRedis) {
		this.isRedis = isRedis;
	}


	public String getRedisHost() {
		return redisHost;
	}


	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}


	public int getRedisPort() {
		return redisPort;
	}


	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}


	public boolean isProduce() {
		return isProduce;
	}


	public void setProduce(boolean isProduce) {
		this.isProduce = isProduce;
	}


	public String getInputFilePath() {
		if (inputFilePath == "") {
			inputFilePath=AppFastFilter.baseurl+"resource/files/";
		}
		return inputFilePath;
	}


	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}


	public String getFileRootUrl() {
		return fileRootUrl;
	}


	public void setFileRootUrl(String fileRootUrl) {
		this.fileRootUrl = fileRootUrl;
	}


	public String getSessionRedisHost() {
		return sessionRedisHost;
	}


	public void setSessionRedisHost(String sessionRedisHost) {
		this.sessionRedisHost = sessionRedisHost;
	}


	public int getSessionRedisPort() {
		return sessionRedisPort;
	}


	public void setSessionRedisPort(int sessionRedisPort) {
		this.sessionRedisPort = sessionRedisPort;
	}
}
