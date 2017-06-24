package www.springmvcplus.com.services.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.modes.RequestMode;
import www.springmvcplus.com.modes.SqlMode;
import www.springmvcplus.com.services.AppFastFilter;
import www.springmvcplus.com.services.AppFastInit;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.util.EntityMake;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.EntityRootPakage;

@Component
public class SystemService {
	@Resource
	BaseDao baseDao;
	
	public int excs(String sqlsJson) {
		List<Map> list=JSON.parseArray(sqlsJson,Map.class);
		int result=0;
		for(Map map:list){
			String[] sql_mode=map.get("sql").toString().split("/");
			if (sql_mode[0].equals("exc")) {//如果是sqlId语句
				SqlResult sqlResult=SqlMake.makeSql((Map<String, String>)map.get("args"), sql_mode[1]);
				result+= baseDao.update(sqlResult.getSql(),sqlResult.getParamters());
			}else{//如果是更新对象
				try {
					String entityName=sql_mode[1];
					String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
					System.out.println("初始化:"+classpath);
					Object baseEntity = EntityMake.makeModeByRequest((Map<String, String>)map.get("args"), Class.forName(classpath));
					
					
					if (sql_mode[0].equals("delete")) {
						result+=baseDao.delete(baseEntity);
					}else if (sql_mode[0].equals("update")) {
						result+=baseDao.update(baseEntity);
					}else if (sql_mode[0].equals("insert")) {
						result+=baseDao.save(baseEntity);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
