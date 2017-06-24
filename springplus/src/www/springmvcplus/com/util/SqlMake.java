package www.springmvcplus.com.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.common.XmlSqlParse;

import com.alibaba.fastjson.JSON;

public class SqlMake {
	public static SqlResult makeSql(HttpServletRequest request,String sqlId) {
		Map<String, String> map=new HashMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = (String) paramNames.nextElement();
			String value=request.getParameter(key);
			map.put(key, value);
		}
		String result = null;
		try {
			result = (String) XmlSqlParse.engine.eval(sqlId+"("+JSON.toJSONString(map)+")");
			result= sqlwhere(result);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlResult sqlResult =makeSelectSql_(request, result);
		return sqlResult;
	}
	public static String makeSql(String sqlId) {
		Map<String, String> map=new HashMap<>();
		String result = null;
		try {
			result = (String) XmlSqlParse.engine.eval(sqlId+"("+JSON.toJSONString(map)+")");
			result= sqlwhere(result);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static SqlResult makeSql(Map<String, String> map,String sqlId) {
		String result = null;
		try {
			result = (String) XmlSqlParse.engine.eval(sqlId+"("+JSON.toJSONString(map)+")");
			result= sqlwhere(result);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlResult sqlResult =makeSelectSql_(map, result);
		return sqlResult;
	}
	static Pattern pattern = Pattern.compile("#{1}\\{{1}\\s*\\w*\\s*\\}{1}");
	private static SqlResult makeSelectSql_(HttpServletRequest request,String sql) {
		List<Object> paramters=new ArrayList<Object>();
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String var=matcher.group();
			var=var.substring(2,var.length()-1);
			String paramValue=StringUtil.valueOf(request.getParameter(var.trim()));
			paramters.add(paramValue);
			sql=sql.replaceAll("#\\{"+var+"\\}", "?");
		}
		SqlResult sqlResult=new SqlResult(sql,paramters.toArray());
		return sqlResult;
	}
	private static SqlResult makeSelectSql_(Map<String, String> request,String sql) {
		List<Object> paramters=new ArrayList<Object>();
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String var=matcher.group();
			var=var.substring(2,var.length()-1);
			String paramValue=StringUtil.valueOf(request.get(var.trim()));
			paramters.add(paramValue);
			sql=sql.replaceAll("#\\{"+var+"\\}", "?");
		}
		SqlResult sqlResult=new SqlResult(sql,paramters.toArray());
		return sqlResult;
	}
	private static String sqlwhere(String sqltemplate) {
		sqltemplate=initsqlwhere(sqltemplate);
		sqltemplate=sqltemplate.replaceAll("\\s{1,}where\\s{1,}and", " where").replaceAll("\\s{1,}where\\s{1,}or", " where");
		return sqltemplate.trim();
	}
	private static String initsqlwhere(String sqltemplate) {
		if (!sqltemplate.contains("<where>")) {
			return sqltemplate;
		}
		Document parse = Jsoup.parse(sqltemplate);
		sqltemplate=parse.body().html();
		Elements elements = parse.getElementsByTag("where");
		Element element=null;
		if (elements.size()>0) {
			element=elements.get(0);
		}
		if (element.hasText()) {
			String wheretext=StringUtil.valueOf(element.text());
			String outerhtml=element.outerHtml();
			if (wheretext.equals("")) {
				sqltemplate = sqltemplate.replace(outerhtml, "");
			}else {
				sqltemplate=sqltemplate.replace(outerhtml, "where "+wheretext);
			}
		}else {
			String innerXML = element.html();
			sqltemplate=sqltemplate.replace(element.outerHtml(), "where "+innerXML);
		}
		if (sqltemplate.contains("<where>")) {
			sqltemplate = initsqlwhere(sqltemplate);
		}
		return sqltemplate;
	}
}
