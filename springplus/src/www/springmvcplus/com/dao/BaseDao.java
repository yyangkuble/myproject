package www.springmvcplus.com.dao;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.app.project.util.Result;

import www.springmvcplus.com.common.*;
import www.springmvcplus.com.modes.RequestMode;
import www.springmvcplus.com.modes.SqlMode;
import www.springmvcplus.com.services.AppFastFilter;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.RedisManager;
import www.springmvcplus.com.util.system.SqlUtil;
import www.springmvcplus.com.util.system.WatcherUtil;

@Service
public class BaseDao {
	@Resource
	public JdbcTemplate jdbcTemplate;
	public Logger logger = LoggerFactory.getLogger(BaseDao.class);
	@Resource
	SpringMVCPlusArgsConfig springMVCPlusArgsConfig;
	public void execute(String sql) {
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		jdbcTemplate.execute(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			RedisManager.delRedis(sql);
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
	}

	
	/**
	 * ����
	 * @param sql
	 */
	public <T> int update(T entity) {
		Map<String, Object> entityConvert=EntityManager.updateSql(entity);
		Object[] args= (Object[])entityConvert.get("args");
		String sql=(String)entityConvert.get("sql");
		logger.info(sql+"  "+JSON.toJSONString(args));
		
		
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result=0;
		if (!sql.equals("")) {
			result=jdbcTemplate.update(sql,args);
		}else {
			result = 1;
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int update(String sql) {
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int update(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int save(String sql) {
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int save(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * 持久化对象
	 * @param entity
	 */
	public <T> int save(T entity) {
		Map<String, Object> entityConvert=EntityManager.insertsql(entity);
		Object[] args= (Object[])entityConvert.get("args");
		String sql=(String)entityConvert.get("sql");
		logger.info(sql+"  "+JSON.toJSONString(args));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,args);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int delete(String sql) {
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int delete(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public <T> int delete(T entity) {
		Map<String, Object> entityConvert=EntityManager.deltesql(entity);
		Object[] args= (Object[])entityConvert.get("args");
		String sql=(String)entityConvert.get("sql");
		logger.info(sql+"  "+JSON.toJSONString(args));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,args);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMaps(String sql) {
		logger.info(sql);
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql, List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return list;
		
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMaps(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return list;
		
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMaps(String sql,int page,int maxrow) {
		String fenyeSql=SqlUtil.makeFenye(sql, page, maxrow);
		String countSql=SqlUtil.makeFenyeCount(sql);
		String allrow=this.getSingleResult(countSql);
		Map<String, Object> map=new HashMap<>();
		if(Integer.valueOf(allrow)>0){
			List<Map<String, Object>> list = this.getListMaps(fenyeSql);
			map.put("count",allrow);
			map.put("list", list);
		}else {
			map.put("count",allrow);
			map.put("list", null);
		}
		return map;
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMaps(String sql,Object[] paramters,int page,int maxrow) {
		String fenyeSql=SqlUtil.makeFenye(sql, page, maxrow);
		String countSql=SqlUtil.makeFenyeCount(sql);
		String allrow=this.getSingleResult(countSql,paramters);
		Map<String, Object> map=new HashMap<String, Object>();
		if(Integer.valueOf(allrow)>0){
			List<Map<String, Object>> list = this.getListMaps(fenyeSql,paramters);
			map.put("count",allrow);
			map.put("list", list);
		}else {
			map.put("count",allrow);
			map.put("list", null);
		}
		return map;
	}
	public Map<String, Object> getMap(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		Map<String, Object> map=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return map;
	}
	public Map<String, Object> getMap(String sql) {
		logger.info(sql);
		Map<String, Object> map=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql, Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return map;
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(String sql,Class<T> c) {
		logger.info(sql);
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql, List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(Class<T> c) {
		String sql="select * from "+EntityManager.getTable(c);
		logger.info(sql);
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql, List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(String sql,Object[] paramters,Class<T> c) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResult(String sql) {
		logger.info(sql);
		String result = null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			result=RedisManager.get(sql, String.class);
			if (result!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (result==null) {
			//result = jdbcTemplate.queryForObject(sql, String.class);
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql);
			if (list1 != null && list1.size()!=0) {
				Map<String, Object> value = list1.get(0);
				Collection<Object> values = value.values();
				if (values != null && values.size() != 0) {
					result = StringUtil.valueOf(values.toArray()[0]);
				}
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, result);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}	
		return result;
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResult(String sql,Object[] paramters) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		String result = null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			result=RedisManager.get(sql+JSON.toJSONString(paramters), String.class);
			if (result!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (result==null) {
			//result = jdbcTemplate.queryForObject(sql,paramters, String.class);
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql,paramters);
			if (list1 != null && list1.size()!=0) {
				Map<String, Object> value = list1.get(0);
				Collection<Object> values = value.values();
				if (values != null && values.size() != 0) {
					result = StringUtil.valueOf(values.toArray()[0]);
				}
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), result);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}	
		return result;
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModel(String sql,Class<T> c) {
		logger.info(sql);
		T t=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		Map<String, Object> map=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql, Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		if (map != null) {
			t=EntityManager.getModel(map, c);
		}
		return t;
	}
	public <T> T getModelById(Object id,Class<T> c) {
		Object[] parameters=new Object[]{id};
		String tableName = EntityManager.tableMap.get(c);
		String idAttrName = EntityManager.idMap.get(c);
		String idcolumName=EntityManager.columnMap.get(c).get(idAttrName);
		String sql="select * from "+tableName +" where "+idcolumName+"=?";
		T model = getModel(sql, parameters, c);
		return model;
	}
	public <T> T getModelById(T t) {
		try {
			Class<T> c=(Class<T>) t.getClass();
			String tableName = EntityManager.tableMap.get(c);
			String idAttrName = EntityManager.idMap.get(c);
			String idcolumName=EntityManager.columnMap.get(c).get(idAttrName);
			Field idField = c.getDeclaredField(idAttrName);
			idField.setAccessible(true);
			Object[] parameters = new Object[]{idField.get(t)};
			String sql="select * from "+tableName +" where "+idcolumName+"=?";
			T model = getModel(sql, parameters, c);
			return model;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModel(String sql,Object[] paramters,Class<T> c) {
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		T t=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		Map<String, Object> map=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		if (map != null) {
			t=EntityManager.getModel(map, c);
		}
		return t;
	}
	/**
	 * ���
	 * @param sql
	 */
	public void executeBySqlId(String sqlId) {
		String sql = SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		jdbcTemplate.execute(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			RedisManager.delRedis(sql);
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
	}

	
	
	public int updateBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int updateBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int updateBySqlId(String sqlId,Map<String, String> map) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int saveBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int saveBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int saveBySqlId(String sqlId,Map<String, String> map) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int deleteBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int deleteBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	public int deleteBySqlId(String sqlId,Map<String, String> map) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> list=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			list=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (list == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)list.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		int result = jdbcTemplate.update(sql,paramters);
		if (springMVCPlusArgsConfig.isRedis()) {
			if (result>0) {
				RedisManager.delRedis(sql);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				list.add(sqlMode);
			}
		}
		return result;
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql, List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return list;
		
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return list;
		
	}
	public Result selectList(String sqlId,Map<String, String> map,Result result) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		String countsql="";
		if (map.get("page") != null) {
			int page=Integer.valueOf(map.get("page"));
			result.setPage(page);
			if (map.get("max") != null) {
				result.setMax(Integer.valueOf(map.get("max")));
			}else {
				result.setMax(Integer.valueOf(30));
			}
			countsql=SqlUtil.makeFenyeCount(sql);
			sql=SqlUtil.makeFenye(sql, page, result.getMax());
		}
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		if (!countsql.equals("")) {
			String allrow=this.getSingleResult(countsql,paramters);
			result.setCount(Long.valueOf(allrow));
		}
		List<Map<String, Object>> list = this.getListMaps(sql,paramters);
		result.setData(list);
		return result;
	}
	public Result selectOne(String sqlId,Map<String, String> map,Result result) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		
		Map<String, Object> map2 = this.getMap(sql,paramters);
		result.setData(map2);
		return result;
	}
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId,Map<String, String> map) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return list;
		
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMapsBySqlId(String sqlId,int page,int maxrow) {
		String sql=SqlMake.makeSql(sqlId);
		String fenyeSql=SqlUtil.makeFenye(sql, page, maxrow);
		String countSql=SqlUtil.makeFenyeCount(sql);
		String allrow=this.getSingleResult(countSql);
		Map<String, Object> map=new HashMap<>();
		if(Integer.valueOf(allrow)>0){
			List<Map<String, Object>> list = this.getListMaps(fenyeSql);
			map.put("count",allrow);
			map.put("list", list);
		}else {
			map.put("count",allrow);
			map.put("list", null);
		}
		return map;
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMapsBySqlId(String sqlId,HttpServletRequest request,int page,int maxrow) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		String fenyeSql=SqlUtil.makeFenye(sql, page, maxrow);
		String countSql=SqlUtil.makeFenyeCount(sql);
		String allrow=this.getSingleResult(countSql,paramters);
		Map<String, Object> map=new HashMap<String, Object>();
		if(Integer.valueOf(allrow)>0){
			List<Map<String, Object>> list = this.getListMaps(fenyeSql,paramters);
			map.put("count",allrow);
			map.put("list", list);
		}else {
			map.put("count",allrow);
			map.put("list", null);
		}
		return map;
	}
	public Map<String, Object> getListMapsBySqlId(String sqlId,Map<String, String> mapParamerts,int page,int maxrow) {
		SqlResult sqlResult=SqlMake.makeSql(mapParamerts, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		String fenyeSql=SqlUtil.makeFenye(sql, page, maxrow);
		String countSql=SqlUtil.makeFenyeCount(sql);
		String allrow=this.getSingleResult(countSql,paramters);
		Map<String, Object> map=new HashMap<String, Object>();
		if(Integer.valueOf(allrow)>0){
			List<Map<String, Object>> list = this.getListMaps(fenyeSql,paramters);
			map.put("count",allrow);
			map.put("list", list);
		}else {
			map.put("count",allrow);
			map.put("list", null);
		}
		return map;
	}
	public Map<String, Object> getMapBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		Map<String, Object> map=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return map;
	}
	public Map<String, Object> getMapBySqlId(String sqlId,Map<String, String> mapParamters) {
		SqlResult sqlResult=SqlMake.makeSql(mapParamters, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		Map<String, Object> map=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return map;
	}
	public Map<String, Object> getMapBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		Map<String, Object> map=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql, Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		return map;
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModelsBySqlId(String sqlId,Class<T> c) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql, List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModelsBySqlId(String sqlId,HttpServletRequest request,Class<T> c) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	public <T> List<T> getListModelsBySqlId(String sqlId,Map<String, String> map,Class<T> c) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		List<Map<String, Object>> list=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			list=RedisManager.get(sql+JSON.toJSONString(paramters), List.class);
			if (list!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (list==null) {
			list=jdbcTemplate.queryForList(sql,paramters);
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), list);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		List<T> entitylist =EntityManager.getList(list, c) ;
		return entitylist;
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResultBySqlId(String sqlId) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		String result = null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			result=RedisManager.get(sql, String.class);
			if (result!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (result==null) {
			//result = jdbcTemplate.queryForObject(sql, String.class);
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql);
			if (list1 != null && list1.size()!=0) {
				Map<String, Object> value = list1.get(0);
				Collection<Object> values = value.values();
				if (values != null && values.size() != 0) {
					result = StringUtil.valueOf(values.toArray()[0]);
				}
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, result);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}	
		return result;
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResultBySqlId(String sqlId,HttpServletRequest request) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		String result = null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			result=RedisManager.get(sql+JSON.toJSONString(paramters), String.class);
			if (result!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (result==null) {
			//result = jdbcTemplate.queryForObject(sql,paramters, String.class);
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql,paramters);
			if (list1 != null && list1.size()!=0) {
				Map<String, Object> value = list1.get(0);
				Collection<Object> values = value.values();
				if (values != null && values.size() != 0) {
					result = StringUtil.valueOf(values.toArray()[0]);
				}
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), result);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}	
		return result;
	}
	public String getSingleResultBySqlId(String sqlId,Map<String, String> map) {
		SqlResult sqlResult=SqlMake.makeSql(map, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		String result = null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			result=RedisManager.get(sql+JSON.toJSONString(paramters), String.class);
			if (result!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (result==null) {
			//result = jdbcTemplate.queryForObject(sql,paramters, String.class);
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql,paramters);
			if (list1 != null && list1.size()!=0) {
				Map<String, Object> value = list1.get(0);
				Collection<Object> values = value.values();
				if (values != null && values.size() != 0) {
					result = StringUtil.valueOf(values.toArray()[0]);
				}
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), result);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}	
		return result;
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModelBySqlId(String sqlId,Class<T> c) {
		String sql=SqlMake.makeSql(sqlId);
		logger.info(sql);
		T t=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		Map<String, Object> map=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql, Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql, map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		if (map != null) {
			t=EntityManager.getModel(map, c);
		}
		return t;
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModelBySqlId(String sqlId,HttpServletRequest request,Class<T> c) {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		T t=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		Map<String, Object> map=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		if (map != null) {
			t=EntityManager.getModel(map, c);
		}
		return t;
	}
	public <T> T getModelBySqlId(String sqlId,Map<String, String> mapParamters,Class<T> c) {
		SqlResult sqlResult=SqlMake.makeSql(mapParamters, sqlId);
		String sql=sqlResult.getSql();
		Object[] paramters=sqlResult.getParamters();
		logger.info(sql+(paramters.length==0?"":JSON.toJSONString(paramters)));
		T t=null;
		List<Object> baseEntities=null;
		SqlMode sqlMode=null;
		if (AppFastFilter.isWatch) {
			baseEntities=AppFastFilter.threadLocal.get();
			sqlMode=new SqlMode();
			if (baseEntities == null) {//多线程sql
				sqlMode.setIsAsyn(true);
				sqlMode.setRequest_id(Thread.currentThread().getName());
			}else {
				sqlMode.setRequest_id(((RequestMode)baseEntities.get(0)).getId());
			}
			sqlMode.setSql(sql);
			sqlMode.setStartTime(System.currentTimeMillis());
		}
		Map<String, Object> map=null;
		boolean iscache=false;
		if (springMVCPlusArgsConfig.isRedis()) {
			map=RedisManager.get(sql+JSON.toJSONString(paramters), Map.class);
			if (map!=null) {
				iscache=true;
				if (AppFastFilter.isWatch) {
					sqlMode.setRedis(iscache);
				}
			}
		}
		if (map==null) {
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,paramters);
			if (!list.isEmpty()) {
				map=list.get(0);
			}
		}
		if (springMVCPlusArgsConfig.isRedis()) {
			if (!iscache) {
				RedisManager.saveSelectValue(sql+JSON.toJSONString(paramters), map);
			}
		}
		if (AppFastFilter.isWatch) {
			sqlMode.setEndTime(System.currentTimeMillis());
			if (sqlMode.isIsAsyn()) {//如果是多线程直接存储
				WatcherUtil.insert(sqlMode);
			}else {//放到list里面
				baseEntities.add(sqlMode);
			}
		}
		if (map != null) {
			t=EntityManager.getModel(map, c);
		}
		return t;
	}
}
