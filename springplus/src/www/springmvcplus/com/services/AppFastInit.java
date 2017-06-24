package www.springmvcplus.com.services;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.druid.pool.DruidDataSource;

import www.springmvcplus.com.common.ApiInit;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.common.XmlSqlParse;
import www.springmvcplus.com.util.LogUtil;
import www.springmvcplus.com.util.MyTask;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.system.RedisManager;
public class AppFastInit implements ApplicationListener<ContextRefreshedEvent>  {
	public static String noRedisTables;
	public static String dbtype="";
	public static Pattern UrlFilter =null;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		ApplicationContext context = event.getApplicationContext();
		
		DruidDataSource dataSource = (DruidDataSource) context.getBean("dataSource");
		String jdbcurl = dataSource.getUrl().toLowerCase();
		if (jdbcurl.contains("mysql")) {
			dbtype="mysql";
		}else if (jdbcurl.contains("db2")) {
			dbtype="db2";
		}else if (jdbcurl.contains("oracle")) {
			dbtype="oracle";
		}else if (jdbcurl.contains("sqlserver")) {
			dbtype="sqlserver";
		}else if (jdbcurl.contains("postgresql")) {
			dbtype="sqlserver";
		}
		//initTable
		SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
		noRedisTables="##"+springMVCPlusArgsConfig.getNoRedisTables().toLowerCase().replaceAll(",","##")+"##";
		UrlFilter= Pattern.compile(".*(?i)("+springMVCPlusArgsConfig.getUrlFilter()+")");
		//开始添加任务
		Map<String, MyTask> mytasks = context.getBeansOfType(MyTask.class);
		MyTask.task(mytasks);
		//结束添加任务
		
		//初始化实体类
		EntityManager.initEntitys();
		//删除redis缓存数据
		if (springMVCPlusArgsConfig.isRedis()) {
			RedisManager.flushRedis();
			LogUtil.info("已清空缓存");
		}
		//初始化SQL模板
		XmlSqlParse.initScriptEngine();
		//加载api
		//ApiInit.apiInit();
	}
	


}
