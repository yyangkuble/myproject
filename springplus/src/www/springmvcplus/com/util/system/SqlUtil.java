package www.springmvcplus.com.util.system;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Id;

import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.common.table.Char;
import www.springmvcplus.com.common.table.Number;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;
import www.springmvcplus.com.common.table.TimeStamp;
import www.springmvcplus.com.common.table.Varchar;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;
import www.springmvcplus.com.services.AppFastInit;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.StringUtil;

public class SqlUtil {
	/**
	 * 组装分页语句
	 * @param sql
	 * @param page
	 * @param maxrow
	 * @return
	 */
	public static String makeFenye(String sql,Integer page,Integer maxrow) {
		if (page==null || maxrow==null || page<1 || maxrow <1) {
			page=1;
			maxrow=30;
		}
		if (AppFastInit.dbtype.equals("mysql")) {
			sql=sql+" limit "+(page-1)*maxrow+","+maxrow;
		}else if (AppFastInit.dbtype.equals("db2")) {
			int start=((page-1)*maxrow+1);
			int end=start+maxrow;
			sql="select * from (select rownumber() over() as rowid,fenyetablezi.* from("+sql+") fenyetablezi) as fenyetable  WHERE fenyetable.rowid BETWEEN "+start+" AND "+end+"";
		}else if (AppFastInit.dbtype.equals("oracle")) {
			
		}else {
			sql=sql+" limit "+(page-1)*maxrow+","+maxrow;
			System.out.println("没有找到合适的分页数据库语句，默认以mysql分页处理，如果不正确请手动添加");
		}
		return sql;
	}
	/**
	 * 分页查询结果总数量
	 * @param sql
	 * @return
	 */
	public static String makeFenyeCount(String sql){
		sql="select count(*) from ("+sql+") t_alldatarows";
		return sql;
	}
	/**
	 * 系统启动创建表
	 * @param c
	 */
	public static void createTable(Class<?> c) {
		TableCreate tableCreate = c.getAnnotation(TableCreate.class);
		if (tableCreate != null) {
			MyService myService = SpringBeanUtil.getBean(MyService.class);
			TableCreateType tableCreateType = tableCreate.value();
			String tableName=EntityManager.tableMap.get(c);
			//如果是oracle数据库
			SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
			if (AppFastInit.dbtype.equals("oracle")) {
				if (tableCreateType==TableCreateType.dropAndCreate || springMVCPlusArgsConfig.isDeleteTable()) {
					boolean isNotExist = myService.getSingleResult(" select count(1) from user_tables where table_name = upper('"+tableName+"')").equals("1")?false:true;
					if (!isNotExist) {
						myService.execute("drop table "+tableName);
					}
				}
				boolean isNotExist = myService.getSingleResult(" select count(1) from user_tables where table_name = upper('"+tableName+"')").equals("1")?false:true;
				if (isNotExist) {
					Map<String, String> map = EntityManager.columnMap.get(c);
					String createsql="create table "+tableName+" (";
					String idAttrName = EntityManager.idMap.get(c);
					if (StringUtil.hashText(idAttrName)) {
						try {
							Field field = c.getDeclaredField(idAttrName);
							createsql+=map.get(idAttrName)+" "+SqlUtil.makeCreateTableSql(field)+" NOT NULL,";
						} catch (NoSuchFieldException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					for (String attrName : map.keySet()) {
						try {
							Field field = c.getDeclaredField(attrName);
							Id id = field.getAnnotation(Id.class);
							if (id==null) {
								createsql+=map.get(attrName)+" "+SqlUtil.makeCreateTableSql(field)+",";
							}
						} catch (NoSuchFieldException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (StringUtil.hashText(idAttrName)) {
						createsql=createsql+"CONSTRAINT "+tableName+"_PK PRIMARY KEY ("+map.get(idAttrName)+")"+")";
					}else {
						createsql=createsql.substring(0, createsql.length()-1)+")";
					}
					myService.execute(createsql);
				}
			}else if (AppFastInit.dbtype.equals("mysql")) {
				if (tableCreateType==TableCreateType.dropAndCreate || springMVCPlusArgsConfig.isDeleteTable()) {
					myService.execute("drop table if exists "+tableName);
				}
				Map<String, String> map = EntityManager.columnMap.get(c);
				String createsql="create table if not exists "+tableName+" (";
				String idAttrName = EntityManager.idMap.get(c);
				if (StringUtil.hashText(idAttrName)) {
					try {
						Field field = c.getDeclaredField(idAttrName);
						createsql+=map.get(idAttrName)+" "+SqlUtil.makeCreateTableSql(field)+" NOT NULL,";
					} catch (NoSuchFieldException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (String attrName : map.keySet()) {
					try {
						Field field = c.getDeclaredField(attrName);
						Id id = field.getAnnotation(Id.class);
						if (id==null) {
							createsql+=map.get(attrName)+" "+SqlUtil.makeCreateTableSql(field)+",";
						}
					} catch (NoSuchFieldException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (StringUtil.hashText(idAttrName)) {
					createsql=createsql+"PRIMARY KEY ("+map.get(idAttrName)+")"+")";
				}else {
					createsql=createsql.substring(0, createsql.length()-1)+")";
				}
				myService.execute(createsql);
			}
			
		}
	}
	/**
	 * 返回Entity时，通过反射将值更改为 实体类字段需要的类型，以便正确封装实例类对象
	 * @param t
	 * @param object
	 * @return
	 */
	public static <T> T caseType(Class<T> t,Object object) {
		if (object==null) {
			return null;
		}else {
			if (t.isAssignableFrom(String.class)) {
				return t.cast(String.valueOf(object)) ;
			}else if(Integer.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(Integer.valueOf(String.valueOf(object)));
			}else if(Double.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(Double.parseDouble(String.valueOf(object)));
			}else if(Float.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(Float.parseFloat(String.valueOf(object)));
			}else if(Long.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(Long.parseLong(String.valueOf(object)));
			}else if(Date.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(DateUtil.dateFormatForSql(String.valueOf(object)));
			}else if(Timestamp.class.getName().equals(t.getName()) && StringUtil.hashText(object)){
				return t.cast(DateUtil.timestampFormatForSql(String.valueOf(object)));
			}else {
				return null;
			}
		}
	}
	//根据实体类字段，获取该字段 对应数据中的那个类型
	public static String makeCreateTableSql(Field field) {
		Char char1 = field.getAnnotation(Char.class);
		www.springmvcplus.com.common.table.Date date = field.getAnnotation(www.springmvcplus.com.common.table.Date.class);
		www.springmvcplus.com.common.table.Double double1 = field.getAnnotation(www.springmvcplus.com.common.table.Double.class);
		Number number = field.getAnnotation(Number.class);
		Text text = field.getAnnotation(Text.class);
		TimeStamp timeStamp = field.getAnnotation(TimeStamp.class);
		Varchar varchar = field.getAnnotation(Varchar.class);
		if (char1 != null) {
			return getChar(char1.value());
		}else if (date != null) {
			return getDate();
		}else if (double1 != null) {
			return getDouble();
		}else if (number != null) {
			return getNumber(number.value());
		}else if (text != null) {
			return getText();
		}else if (timeStamp != null) {
			return getTimeStamp();
		}else if (varchar != null) {
			return getVarchar(varchar.value());
		}else {
			String fieldType = field.getType().getName();
			if (String.class.getName().equals(fieldType)) {
				return getVarchar(255);
			}else if (Integer.class.getName().equals(fieldType)) {
				return getNumber(new int[]{0,0});
			}else if (Double.class.getName().equals(fieldType)) {
				return getDouble();
			}else if (Float.class.getName().equals(fieldType)) {
				return getDouble();
			}else if (Date.class.getName().equals(fieldType)) {
				return getDate();
			}else if (Timestamp.class.getName().equals(fieldType)) {
				return getTimeStamp();
			}else if (Long.class.getName().equals(fieldType)) {
				return getNumber(new int[]{0,0});
			}else {
				return getVarchar(255);
			}
		}
	}
	//获取char类型
	public static String getChar(int length) {
		if (AppFastInit.dbtype.equals("db2")) {
			return "character("+length+")";
		}else {
			return "char("+length+")";
		}
	}
	//获取varchar类型
	public static String getVarchar(int length) {
		if (AppFastInit.dbtype.equals("oracle")) {
			return "varchar2("+length+")";
		}else {
			return "varchar("+length+")";
		}
	}
	//获取数字类型
	public static String getNumber(int[] length) {
		if (AppFastInit.dbtype.equals("oracle")) {
			if (length[1]==0) {
				return "number";
			}else {
				return "number("+length[0]+","+length[1]+")";
			}
		}else if(AppFastInit.dbtype.equals("mysql")){
			if (length[1]==0) {
				return "bigint";
			}else {
				return "decimal("+length[0]+","+length[1]+")";
			}
		}else {
			return "";
		}
	}
	//获取浮点类型
	public static String getDouble() {
		if (AppFastInit.dbtype.equals("oracle")) {
			return "binary_double";
		}else if(AppFastInit.dbtype.equals("mysql")){
			return "double";
		}else {
			return "double";
		}
	}
	//获取长文本
	public static String getText() {
		if (AppFastInit.dbtype.equals("oracle")) {
			return "varchar2(4000)";
		}else if(AppFastInit.dbtype.equals("mysql")){
			return "text";
		}else {
			return "text";
		}
	}
	//获取日期类型，只有年月日
	public static String getDate() {
		return "date";
	}
	//获取日期时间类型，包括毫秒
	public static String getTimeStamp() {
		return "timestamp";
	}
	public static String inSqlStr(String ids) {
		if (StringUtil.hashText(ids)) {
			String inSql="in (";
			String[] split = ids.split(",");
			for (String id : split) {
				inSql+="'"+id+"',";
			}
			inSql=inSql.substring(0, inSql.length()-1);
			inSql+=")";
			return inSql;
		}
		return null;
	}
	public static String inSqlInt(String ids) {
		if (StringUtil.hashText(ids)) {
			String inSql="in (";
			String[] split = ids.split(",");
			for (String id : split) {
				inSql+="'"+id+"',";
			}
			inSql=inSql.substring(0, inSql.length()-1);
			inSql+=")";
			return inSql;
		}
		return null;
	}
}
