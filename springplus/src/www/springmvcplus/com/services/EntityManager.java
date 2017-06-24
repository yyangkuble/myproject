package www.springmvcplus.com.services;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.FileUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.LogUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.EntityRootPakage;
import www.springmvcplus.com.util.system.SqlUtil;

public class EntityManager {
	//所有Map<BaseEnity.class,tableName>
	public static Map<Class<?>, String> tableMap=new HashMap<Class<?>, String>();
	//所有Map<entityName,BaseEnity.class>
	public static Map<String, Class<?>> entityNameMap=new HashMap<String, Class<?>>();
	//所有Map<BaseEnity.class,attrName>
	public static Map<Class<?>, String> idMap=new HashMap<Class<?>, String>();
	//自动自动增长id 类型//int:table自增长， time:以时间为字符串2017011614445955122，uuid:以字符串为id    默认为time
	public static Map<Class<?>, GeneratedType> idGeneratedType=new HashMap<Class<?>, GeneratedType>();
	//所有Map<BaseEnity.class,Map<attrname,columnName>>
	public static Map<Class<?>, Map<String, String>> columnMap=new HashMap<Class<?>, Map<String,String>>();
	//www.springmvcplus.com.modes下的所有实例
	public static List<String> sysEntityList=new ArrayList<String>();
	public static String getTable(Class<?> class1) {
		Entity entity = class1.getAnnotation(Entity.class);
		Table table = class1.getAnnotation(Table.class);
		if (entity != null || table != null) {
			//确定表名
			String tablename = tableMap.get(class1);
			if(tablename==null){//如果不存在初始化entity
				if (table==null) {
					tablename=class1.getSimpleName();
				}else if(table.name()==null || table.name().equals("")) {//如果没有名字
					tablename=class1.getSimpleName();
				}else {
					tablename=table.name();
				}
			}else {
				return tablename;
			}
			tableMap.put(class1, tablename);
			//确定表字段
			Field[] fields = class1.getDeclaredFields();
			Map<String, String> thiscolumnMap=new HashMap<String, String>();
			for (Field field : fields) {
				if(field.getAnnotation(Transient.class)==null){
					String columnName="";
					Column column=field.getAnnotation(Column.class);
					if (column == null || column.name().equals("")) {
						columnName= field.getName();
					}else {
						columnName= column.name();
					}
					thiscolumnMap.put(field.getName(), columnName);
					
					Id id=field.getAnnotation(Id.class);
					if (id!=null) {
						idMap.put(class1, field.getName());
						 Generated generated=field.getAnnotation(Generated.class);
							if (generated != null) {
								GeneratedType generatedType = generated.value();
								idGeneratedType.put(class1, generatedType);
							}
					}
				}
			}
			columnMap.put(class1, thiscolumnMap);
			entityNameMap.put(class1.getSimpleName(), class1);
			System.out.println("初始化表："+tablename);
			return tablename;
		}else {
			return null;
		}
	}
	
	public static Map<String, Object> insertsql(Object baseEntity) {
		String tablename=getTable(baseEntity.getClass());
		GeneratedType idgeneratedtype =idGeneratedType.get(baseEntity.getClass());
		String idAttrName=idMap.get(baseEntity.getClass());
		Map<String, String> thiscolumnMap= columnMap.get(baseEntity.getClass());
		String columstring="";
		String noargs="";
		List<Object> args=new ArrayList<Object>();
		
		for (String attrName : thiscolumnMap.keySet()) {
			Field field=null;
			Object value=null;
			try {
				field = baseEntity.getClass().getDeclaredField(attrName);
				field.setAccessible(true);
				value=field.get(baseEntity);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (attrName.equals(idAttrName) && idgeneratedtype != null) {
				if (StringUtil.hashText(value)) {
					columstring+=thiscolumnMap.get(attrName)+",";
					noargs+="?,";
					args.add(value);
				}else{
					columstring+=thiscolumnMap.get(attrName)+",";
					if (idgeneratedtype==GeneratedType.dbBase) {
						noargs+="null,";
					}else if (idgeneratedtype==GeneratedType.uuid) {
						String id=IdManage.getUUid();
						try {
							field.set(baseEntity, id);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						noargs+="'"+id+"',";
					}else {
						String id=IdManage.getTimeUUid();
						try {
							field.set(baseEntity, id);
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						noargs+="'"+id+"',";
					}
				}
			}else{
				columstring+=thiscolumnMap.get(attrName)+",";
				noargs+="?,";
				args.add(value);
			}
		}
		String sql="insert into "+tablename+" ("+columstring.substring(0,columstring.length()-1)+")values("+noargs.substring(0, noargs.length()-1)+")";
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("sql", sql);
		result.put("args", args.toArray());
		return result;
	}
	public static Object getMethodValue(Field field,Object baseEntity) {
        try {
        	String fieldName=field.getName();
        	Class[] parameterTypes = new Class[1]; 
        	parameterTypes[0] = field.getType();  
        	StringBuffer sb = new StringBuffer();  
        	sb.append("get");  
        	sb.append(fieldName.substring(0, 1).toUpperCase());  
        	sb.append(fieldName.substring(1));  
			Method method = baseEntity.getClass().getMethod(sb.toString());
			Object invoke = method.invoke(baseEntity, null);
			return invoke;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return null;
	}
	public static void setMethodValue(Field field,Object baseEntity,Object value) {
        try {
        	String fieldName=field.getName();
        	Class[] parameterTypes = new Class[1]; 
        	parameterTypes[0] = field.getType();  
        	StringBuffer sb = new StringBuffer();  
        	sb.append("set");  
        	sb.append(fieldName.substring(0, 1).toUpperCase());  
        	sb.append(fieldName.substring(1));  
			Method method = baseEntity.getClass().getMethod(sb.toString(), parameterTypes);
			method.invoke(baseEntity, value);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static Map<String, Object> deltesql(Object baseEntity) {
		String tablename=getTable(baseEntity.getClass());
		Map<String, String> thiscolumnMap= columnMap.get(baseEntity.getClass());
		String wherestring="";
		ArrayList<Object> args=new ArrayList<Object>();
		for (String attrName : thiscolumnMap.keySet()) {
			try {
				Field field=baseEntity.getClass().getDeclaredField(attrName);
				field.setAccessible(true);
				Object fieldValue=field.get(baseEntity);
				if (fieldValue != null) {
					wherestring+=" "+thiscolumnMap.get(attrName)+"=? and";
					args.add(fieldValue);
				}
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
		}
		String sql="delete from "+tablename+" where"+wherestring.substring(0, wherestring.length()-3);
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("sql", sql);
		result.put("args", args.toArray());
		return result;
	}
	public static <T> List<T> getList(List<Map<String, Object>> list,Class<T> class1) {
		Map<String, String> thiscolumnMap= columnMap.get(class1);
		List<T> list2=new ArrayList<T>();
		for (Map<String, Object> map : list) {
			try {
				T t = class1.newInstance();
				for (String attrName : thiscolumnMap.keySet()) {
					Field field = class1.getDeclaredField(attrName);
					field.setAccessible(true);
					field.set(t, SqlUtil.caseType(field.getType(),map.get(thiscolumnMap.get(attrName))));
				}
				list2.add(t);
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
		}
		return list2;
	}
	public static <T> T getModel(Map<String, Object> map,Class<T> class1) {
		Map<String, String> thiscolumnMap= columnMap.get(class1);
		T t=null;
			try {
				t = class1.newInstance();
				for (String attrName : thiscolumnMap.keySet()) {
					Field field = class1.getDeclaredField(attrName);
					field.setAccessible(true);
					field.set(t, SqlUtil.caseType(field.getType(),map.get(thiscolumnMap.get(attrName))));
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
	
	public static Map<String, Object> updateSql(Object entity) {
		String tablename=getTable(entity.getClass());
		Map<String, String> thiscolumnMap= columnMap.get(entity.getClass());
		String columstring="";
		String idattrname=idMap.get(entity.getClass());
		if (idattrname==null || idattrname.equals("")) {
			try {
				throw new Exception(entity.getClass().getName()+"没有发现有@Id注解的字段存在");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String idcolumnName="";
		Object idValue="";
		List<Object> objects=new ArrayList<Object>();
		for (String attrName : thiscolumnMap.keySet()) {
			if(attrName.equals(idattrname)){
				idcolumnName=thiscolumnMap.get(attrName);
				try {
					Field field = entity.getClass().getDeclaredField(attrName);
					field.setAccessible(true);
					idValue=field.get(entity);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					Field field=entity.getClass().getDeclaredField(attrName);
					field.setAccessible(true);
					Object value=field.get(entity);
					if (value!=null) {
						if (value.equals("")) {
							objects.add(null);
						}else {
							objects.add(value) ;
						}
						columstring+=thiscolumnMap.get(attrName)+"=?,";
					}
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
			}
			
		}
		objects.add(idValue);
		String sql="";
		if (!columstring.equals("")) {
			sql="update "+tablename+" set "+columstring.substring(0,columstring.length()-1)+" where "+idcolumnName+"=?";
		}
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("sql", sql);
		result.put("args", objects.toArray());
		return result;
	}
	public static void initEntitys() {
		LogUtil.info(EntityManager.class, "开始初始化实体类并创建表");
		//初始化实体类
		String userEntityPath=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class).getEntityRootPackage().replaceAll("\\.", "/");
		File file=FileUtil.getFile(Thread.currentThread().getContextClassLoader().getResource(userEntityPath).getPath());
		File fileFrame=FileUtil.getFile(Thread.currentThread().getContextClassLoader().getResource("www/springmvcplus/com/modes").getPath());
		File[] entityfiles = file.listFiles();
		File[] entityfilesFrame = fileFrame.listFiles();
		for (File file2 : entityfiles) {
			String classStr=file2.getName().replace(".class", "");
			try {
				Class<?> c=Class.forName(EntityRootPakage.getRootPackage(classStr)+classStr);
				EntityManager.getTable(c);
				SqlUtil.createTable(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (File file2 : entityfilesFrame) {
			String classStr=file2.getName().replace(".class", "");
			EntityManager.sysEntityList.add(classStr);
			try {
				Class<?> c=Class.forName(EntityRootPakage.getRootPackage(classStr)+classStr);
				EntityManager.getTable(c);
				SqlUtil.createTable(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

}
