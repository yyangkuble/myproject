package www.springmvcplus.com.services.service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.app.project.mode.Group;
import com.app.project.service.SelectIntecept;
import com.app.project.service.UpdateAndInsertAndDeleteIntecept;
import com.app.project.util.Result;

import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.services.LogManager;
import www.springmvcplus.com.util.EntityMake;
import www.springmvcplus.com.util.FileUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.StringUtil;

@Component
public class MyService {
	@Resource
	BaseDao baseDao;
	
	public void execute(String sql) {
		baseDao.execute(sql);
	}

	public void selectList(String sqlId,Map<String, String> map,Result result) {
		baseDao.selectList(sqlId, map,result);
	}
	public void selectOne(String sqlId,Map<String, String> map,Result result) {
		baseDao.selectOne(sqlId, map,result);
	}
	/**
	 * ����
	 * @param sql
	 */
	public <T> int update(T entity) {
		return baseDao.update(entity);
	}
	public <T> int system_update(T entity,Result result) {
		UpdateAndInsertAndDeleteIntecept intecept=SpringBeanUtil.getBean(UpdateAndInsertAndDeleteIntecept.class);
		intecept.saveAndUpdateBefore(entity,result, UpdateAndInsertAndDeleteIntecept.HandleType.update);
		if (result.getErrorCode()==0) {
			int count = baseDao.update(entity);
			if (count != 1) {
				result.setErrorCode(1);
			}else {
				result.setData(baseDao.getModelById(entity));
			}
			return count;
		}
		intecept.saveAndUpdateEnd(entity,result, UpdateAndInsertAndDeleteIntecept.HandleType.update);
		return 0;
	}
	public int update(String sql) {
		return baseDao.update(sql);
	}
	public int update(String sql,Object[] paramters) {
		return baseDao.update(sql, paramters);
	}
	public <T> T getModelById(Object id,Class<T> c) {
		return baseDao.getModelById(id, c);
	}
	public <T> T getModelById(T t) {
		return baseDao.getModelById(t);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int save(String sql) {
		return baseDao.save(sql);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int save(String sql,Object[] paramters) {
		return baseDao.save(sql, paramters);
	}
	/**
	 * 持久化对象
	 * @param entity
	 */
	public <T> int save(T entity) {
		
		
		int count = baseDao.save(entity);
		if (entity instanceof Group) {
			Group group = (Group) entity;
			String id = baseDao.getSingleResultBySqlId("lastId");
			group.setId(Integer.valueOf(id));
		}
		//select @@IDENTITY
		return count;
	}
	public <T> int system_save(T entity,Result result) {
		UpdateAndInsertAndDeleteIntecept intecept=SpringBeanUtil.getBean(UpdateAndInsertAndDeleteIntecept.class);
		intecept.saveAndUpdateBefore(entity,result, UpdateAndInsertAndDeleteIntecept.HandleType.save);
		if (result.getErrorCode()==0) {
			int count = baseDao.save(entity);
			if (entity instanceof Group) {
				Group group = (Group) entity;
				String id = baseDao.getSingleResultBySqlId("lastId");
				group.setId(Integer.valueOf(id));
			}
			if (count != 1) {
				result.setErrorCode(1);
			}else {
				result.setData(baseDao.getModelById(entity));
			}
			return count;
		}
		intecept.saveAndUpdateEnd(entity,result, UpdateAndInsertAndDeleteIntecept.HandleType.save);
		return 0;
	}
	/**
	 * ����
	 * @param sql
	 */
	public int delete(String sql) {
		return baseDao.delete(sql);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int delete(String sql,Object[] paramters) {
		return baseDao.delete(sql, paramters);
	}
	public <T> int delete(T entity) {
		return baseDao.delete(entity);
	}
	
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMaps(String sql) {
		return baseDao.getListMaps(sql);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMaps(String sql,Object[] paramters) {
		return baseDao.getListMaps(sql, paramters);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMaps(String sql,int page,int maxrow) {
		return baseDao.getListMaps(sql, page, maxrow);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMaps(String sql,Object[] paramters,int page,int maxrow) {
		return baseDao.getListMaps(sql, paramters, page, maxrow);
	}
	public Map<String, Object> getMap(String sql,Object[] paramters) {
		return baseDao.getMap(sql, paramters);
	}
	public Map<String, Object> getMap(String sql) {
		return baseDao.getMap(sql);
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(String sql,Class<T> c) {
		return baseDao.getListModels(sql, c);
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(Class<T> c) {
		return baseDao.getListModels(c);
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModels(String sql,Object[] paramters,Class<T> c) {
		return baseDao.getListModels(sql, paramters, c);
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResult(String sql) {
		return baseDao.getSingleResult(sql);
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResult(String sql,Object[] paramters) {
		return baseDao.getSingleResult(sql, paramters);
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModel(String sql,Class<T> c) {
		return baseDao.getModel(sql, c);
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModel(String sql,Object[] paramters,Class<T> c) {
		return baseDao.getModel(sql, paramters, c);
	}
	/**
	 * ���
	 * @param sql
	 */
	public void executeBySqlId(String sqlId) {
		baseDao.executeBySqlId(sqlId);
	}

	
	
	public int updateBySqlId(String sqlId) {
		return baseDao.updateBySqlId(sqlId);
	}
	public int updateBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.updateBySqlId(sqlId, request);
	}
	public int updateBySqlId(String sqlId,Map<String, String> map) {
		return baseDao.updateBySqlId(sqlId, map);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int saveBySqlId(String sqlId) {
		return baseDao.saveBySqlId(sqlId);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int saveBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.saveBySqlId(sqlId, request);
	}
	public int saveBySqlId(String sqlId,Map<String, String> map) {
		return baseDao.saveBySqlId(sqlId, map);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int deleteBySqlId(String sqlId) {
		return baseDao.deleteBySqlId(sqlId);
	}
	/**
	 * ����
	 * @param sql
	 */
	public int deleteBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.deleteBySqlId(sqlId, request);
	}
	public int deleteBySqlId(String sqlId,Map<String, String> map) {
		return baseDao.deleteBySqlId(sqlId, map);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId) {
		return baseDao.getListMapsBySqlId(sqlId);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.getListMapsBySqlId(sqlId, request);
	}
	public List<Map<String, Object>> getListMapsBySqlId(String sqlId,Map<String, String> map) {
		return baseDao.getListMapsBySqlId(sqlId, map);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMapsBySqlId(String sqlId,int page,int maxrow) {
		return baseDao.getListMapsBySqlId(sqlId, page, maxrow);
	}
	/**
	 * ����һ��  List<Map<String, Object>>
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getListMapsBySqlId(String sqlId,HttpServletRequest request,int page,int maxrow) {
		return baseDao.getListMapsBySqlId(sqlId, request, page, maxrow);
	}
	public Map<String, Object> getListMapsBySqlId(String sqlId,Map<String, String> mapParamerts,int page,int maxrow) {
		return baseDao.getListMapsBySqlId(sqlId, mapParamerts, page, maxrow);
	}
	public Map<String, Object> getMapBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.getMapBySqlId(sqlId, request);
	}
	public Map<String, Object> getMapBySqlId(String sqlId,Map<String, String> mapParamters) {
		return baseDao.getMapBySqlId(sqlId, mapParamters);
	}
	public Map<String, Object> getMapBySqlId(String sqlId) {
		return baseDao.getMapBySqlId(sqlId);
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModelsBySqlId(String sqlId,Class<T> c) {
		return baseDao.getListModelsBySqlId(sqlId, c);
	}
	/**
	 * ����һ��list mode
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> List<T> getListModelsBySqlId(String sqlId,HttpServletRequest request,Class<T> c) {
		return baseDao.getListModelsBySqlId(sqlId, request, c);
	}
	public <T> List<T> getListModelsBySqlId(String sqlId,Map<String, String> map,Class<T> c) {
		return baseDao.getListModelsBySqlId(sqlId, map, c);
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResultBySqlId(String sqlId) {
		return baseDao.getSingleResultBySqlId(sqlId);
	}
	/**
	 * ����
	 * @param sql
	 * @param c
	 * @return
	 */
	public String getSingleResultBySqlId(String sqlId,HttpServletRequest request) {
		return baseDao.getSingleResultBySqlId(sqlId, request);
	}
	public String getSingleResultBySqlId(String sqlId,Map<String, String> map) {
		return baseDao.getSingleResultBySqlId(sqlId, map);
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModelBySqlId(String sqlId,Class<T> c) {
		return baseDao.getModelBySqlId(sqlId, c);
	}
	/**
	 * 通过sql查询转换成mode，实体类必须要实现
	 * @param sql
	 * @param c
	 * @return
	 */
	public <T> T getModelBySqlId(String sqlId,HttpServletRequest request,Class<T> c) {
		return baseDao.getModelBySqlId(sqlId, request, c);
	}
	public <T> T getModelBySqlId(String sqlId,Map<String, String> mapParamters,Class<T> c) {
		return baseDao.getModelBySqlId(sqlId, mapParamters, c);
	}
	private static Pattern pattern = Pattern.compile("\\({1}\\s*\\w*\\s*\\){1}");
	@SuppressWarnings("resource")
	public <T> Integer saveByExcel(Class<T> c,String excelPath) {
		excelPath=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class).getInputFilePath()+excelPath;
		Map<Integer,String> keys=new TreeMap<Integer,String>();
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(FileUtil.getFile(excelPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		//获取标题
		Row titleRow = sheet.getRow(0);
		int colums_num = titleRow.getLastCellNum();
		for(int i=0;i<colums_num;i++){
			String title=titleRow.getCell(i).getStringCellValue();
			Matcher matcher = pattern.matcher(title);
			if (matcher.find()) {
				String var = matcher.group();
				var = StringUtil.valueOf(var.substring(1,var.length()-1)).trim();
				keys.put(i, var);
			}
		}
		int result=0;
		int allrow=sheet.getLastRowNum();
		//循环每一行的值
		for(int j=1;j<=allrow;j++){//从第一行开始，应为0行是标题
			Map<String, String> map=new TreeMap<String, String>();
			Row dataRow = sheet.getRow(j);
			if (dataRow != null) {
				for(int i=0;i<colums_num;i++){
					if (keys.keySet().contains(i)) {
						if (dataRow.getCell(i) != null) {
							dataRow.getCell(i).setCellType(CellType.STRING);
						}
						map.put(keys.get(i), dataRow.getCell(i)==null?null:dataRow.getCell(i).getStringCellValue());
					}
				}
				boolean isallnull=true;
				for (String value : map.values()) {
					if (value != null && !value.trim().equals("")) {
						isallnull =false;
						break;
					}
				}
				if (isallnull == false) {
					Object baseEntity = EntityMake.makeModeByRequest(map, c);
					result+=system_save(baseEntity, new Result());
				}
			}
		}
		return result;
	}
}
