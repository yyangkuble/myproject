package www.springmvcplus.com.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSON;

import www.springmvcplus.com.modes.Api;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.LogUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.StringUtil;

public class ApiInit {
	public static void apiInit() {
		LogUtil.info(ApiInit.class, "初始化api");
		RequestMappingHandlerMapping requestMappingHandlerMapping=SpringBeanUtil.getBean(RequestMappingHandlerMapping.class);
        MyService myService=SpringBeanUtil.getBean(MyService.class);
        myService.execute("delete from sys_api");
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();  
        //方法接口
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {  
            RequestMappingInfo info = m.getKey();  
            HandlerMethod method = m.getValue();  
            Class<?> class1 = method.getMethod().getDeclaringClass();
            ApiDesc apiDesc = class1.getAnnotation(ApiDesc.class);
            Method method2 = method.getMethod();
            ApiParameter apiParameter = method2.getAnnotation(ApiParameter.class);
            if (apiDesc!=null && apiParameter != null) {
            	Api api=new Api();
            	PatternsRequestCondition p = info.getPatternsCondition();  
            	for (String url : p.getPatterns()) {  
            		api.setUrl(url);
            	}
				api.setDesc(apiParameter.desc());
				api.setModename(apiDesc.value());
				api.setApitype("api");
				String[] params=apiParameter.parameters();
				Map<String, String> map2=new HashMap<String, String>();
				for (String string : params) {
					if (string.contains(":")) {
						map2.put(string.split(":")[0],string.split(":")[1] );
					}
				}
				api.setRequestparam(JSON.toJSONString(map2));
				myService.save(api);
			}
        }  
		//xml sql接口
        Map<String, Sql> sqlMap=XmlSqlParse.getSqlTemplate();
        for (String sqlid : sqlMap.keySet()) {
        	Sql sql=sqlMap.get(sqlid);
        	if (StringUtil.hashText(sql.getDes())) {
        		Api api=new Api();
        		api.setId(sqlid);
        		api.setDesc(sql.getDes());
        		api.setModename(sql.getModename());
        		api.setRequestparam(JSON.toJSONString(sql.getApi()));
        		api.setUrl(sql.getSqlTemplate());
        		api.setApitype("sql");
        		myService.save(api);
			}
		}
        //实体类接口
      //3获取mode
  		for (String entityName : EntityManager.entityNameMap.keySet()) {
  			if (!EntityManager.sysEntityList.contains(entityName)) {
  				Class<?> class1 = EntityManager.entityNameMap.get(entityName);
  				ApiDesc apiDescClass = class1.getAnnotation(ApiDesc.class);
  				String modeName=apiDescClass==null ? "未定义模块名称": apiDescClass.value();
  				String tableName=EntityManager.getTable(class1);
  				//增加的接口
  				{
	  				Api api=new Api();
	  				api.setId(entityName+"_insert");
	  				api.setApitype("mode");
	  				api.setModename(modeName);
	  				api.setDesc(tableName+"表 - 增加");
	  				api.setUrl("insert/"+entityName);
	  				Map<String, String> requestParam=new HashMap<String, String>();
	  				Field[] files = class1.getDeclaredFields();
					for (Field field : files) {
						Generated generated=field.getAnnotation(Generated.class);
						Transient transient1 = field.getAnnotation(Transient.class);
						if (generated == null && transient1 == null) {//如果此列不是自动生成的值,并且不是透明值
							ApiDesc desc=field.getAnnotation(ApiDesc.class);
							requestParam.put(field.getName(), desc==null?"未添加注释":desc.value());
						}
					}
					api.setRequestparam(JSON.toJSONString(requestParam));
					myService.save(api);
  				}
  				
  				//查询全部数据
  				{
	  				Api api=new Api();
	  				api.setId(entityName+"_selectAll");
	  				api.setApitype("mode");
	  				api.setModename(modeName);
	  				api.setDesc(tableName+"表 - 查询全部");
	  				api.setUrl("select/"+entityName+"_all");
					api.setRequestparam("");
					myService.save(api);
  				}
  				//如果有id，那么就有 通过id删除，修改，查询实体类的 接口
  				String idcolum = EntityManager.idMap.get(class1);
  				if (StringUtil.hashText(idcolum)) {
  					//通过id查询
  					{
  						Api api=new Api();
  		  				api.setId(entityName+"_select_byid");
  		  				api.setApitype("mode");
  		  				api.setModename(modeName);
  		  				api.setDesc(tableName+"表 - 通过主键查询");
  		  				api.setUrl("select/"+entityName+"/byid");
  		  				Map<String, String> requestParam=new HashMap<String, String>();
  		  				Field[] files = class1.getDeclaredFields();
  						for (Field field : files) {
  							Generated generated=field.getAnnotation(Generated.class);
  							if (generated != null) {//如果此列不是自动生成的值,并且不是透明值
  								ApiDesc desc=field.getAnnotation(ApiDesc.class);
  								requestParam.put(field.getName(), desc==null?"未添加注释":desc.value());
  							}
  						}
  						api.setRequestparam(JSON.toJSONString(requestParam));
  						myService.save(api);
  					}
  					//通过id修改
  	  				{
  		  				Api api=new Api();
  		  				api.setId(entityName+"_update");
  		  				api.setApitype("mode");
  		  				api.setModename(modeName);
  		  				api.setDesc(tableName+"表 - 通过主键修改");
  		  				api.setUrl("update/"+entityName);
  		  				Map<String, String> requestParam=new HashMap<String, String>();
  		  				Field[] files = class1.getDeclaredFields();
  						for (Field field : files) {
  							Generated generated=field.getAnnotation(Generated.class);
  							if (generated != null) {//如果此列不是自动生成的值,并且不是透明值
  								ApiDesc desc=field.getAnnotation(ApiDesc.class);
  								requestParam.put(field.getName(), desc==null?"未添加注释":desc.value());
  							}
  						}
  						api.setRequestparam(JSON.toJSONString(requestParam));
  						myService.save(api);
  	  				}
  	  				//通过id删除
  	  				{
  		  				Api api=new Api();
  		  				api.setId(entityName+"_delete");
  		  				api.setApitype("mode");
  		  				api.setModename(modeName);
  		  				api.setDesc(tableName+"表 - 通过主键删除");
  		  				api.setUrl("delete/"+entityName);
  		  				Map<String, String> requestParam=new HashMap<String, String>();
  		  				Field[] files = class1.getDeclaredFields();
  						for (Field field : files) {
  							Generated generated=field.getAnnotation(Generated.class);
  							if (generated != null) {//如果此列不是自动生成的值,并且不是透明值
  								ApiDesc desc=field.getAnnotation(ApiDesc.class);
  								requestParam.put(field.getName(), desc==null?"未添加注释":desc.value());
  							}
  						}
  						api.setRequestparam(JSON.toJSONString(requestParam));
  						myService.save(api);
  	  				}
				}
  			}
  		}
        
	}
}
