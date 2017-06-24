package www.springmvcplus.com.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.project.mode.GroupJournal;

import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.util.system.SqlUtil;

public class EntityMake {
	public static <T> T makeModeByRequest(HttpServletRequest request,Class<T> c) {
		EntityManager.getTable(c);
		T t=null;
		try {
			t = c.newInstance();
			Map<String, String> thiscolumnMap= EntityManager.columnMap.get(c);
			for (String attrName:thiscolumnMap.keySet()) {
				String value=request.getParameter(attrName);
				if (value != null) {
					Field field = c.getDeclaredField(attrName);
					field.setAccessible(true);
					field.set(t, SqlUtil.caseType(field.getType(),value));
				}
			}
		} catch (InstantiationException e) {
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
		return t;
	}
	public static <T> T makeModeByRequest(Map<String, String> request,Class<T> c) {
		T t=null;
		try {
			t = c.newInstance();
			Map<String, String> thiscolumnMap= EntityManager.columnMap.get(c);
			for (String attrName:thiscolumnMap.keySet()) {
				String value=request.get(attrName);
				if (value != null) {
					Field field = c.getDeclaredField(attrName);
					field.setAccessible(true);
					field.set(t, SqlUtil.caseType(field.getType(),value));
				}
			}
		} catch (InstantiationException e) {
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
		return t;
	}
	public static Map<String, String> makeMapByEnity(Object entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		Map<String, String> map=new HashMap<String, String>();
		try {
			for (Field field : fields) {
					field.setAccessible(true);
					Object objValue = field.get(entity);
					if (objValue != null) {
						map.put(field.getName(),StringUtil.valueOf(objValue));
					}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
