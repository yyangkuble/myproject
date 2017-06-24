package www.springmvcplus.com.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.util.FileUtil;
import www.springmvcplus.com.util.LogUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.EntityRootPakage;
import www.springmvcplus.com.util.system.JsoupUtil;

public class XmlSqlParse {
	static Pattern patternout = Pattern.compile("\\${1}\\{{1}\\s*\\w*\\s*\\}{1}");
	static Pattern patternif = Pattern.compile("(<if){1}\\s*(if=\"){1}(.*)(\">){1}");
	static Pattern patternelseif = Pattern.compile("(<elseif){1}\\s*(if=\"){1}(.*)(\">){1}");
	public static Map<String, Sql> sqlMap=new HashMap<String,Sql>();
	public static Map<String,Sql> getSqlTemplate() {
		if (!sqlMap.isEmpty()) {
			return sqlMap;
		}
		Set<String> sqlids=new HashSet<String>();
		List<File> list = new ArrayList<File>();
		List<File> userxmlRootPathlist = new ArrayList<File>();
		String userxmlSqlPaths=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class).getXmlSqlRootPackage().replaceAll("\\.", "/");
		for (String userxmlSqlPath:userxmlSqlPaths.split(",")) {
			File userfile=FileUtil.getFile(Thread.currentThread().getContextClassLoader().getResource(userxmlSqlPath).getPath());
			userxmlRootPathlist.add(userfile);
		}
		File systemfile = FileUtil.getFile(XmlSqlParse.class.getClassLoader().getResource("www/springmvcplus/com/sqls/plus.xml").getFile());
		list.add(systemfile);
		for (File userfile:userxmlRootPathlist) {
			for (File filexml : userfile.listFiles()) {
				if (filexml.getName().endsWith(".xml")) {
					list.add(filexml);
				}
			}
		}
		for (File file : list) {
				Element xml = JsoupUtil.getHtml(file);
				if (xml.tag().getName().equals("sqls")) {
					String modename = xml.attr("modeName");
					Elements elements = xml.getElementsByTag("sql");
					for (Element element : elements) {
						Sql sql = new Sql();
						sql.setModename(modename);
						sql.setDes(element.attr("des"));
						sql.setId(element.attr("id"));
						if (sqlids.contains(sql.getId())) {//如果已经存在
							throw new RuntimeException("sqlId["+sql.getId()+"]已经存在,请更换一个sqlId");
						}else {
							sqlids.add(element.attr("id"));
						}
						Map<String, String> apimap=new HashMap<String, String>();
						if (StringUtil.hashText(element.attr("api"))) {
							String[] apis = element.attr("api").split(",");
							for (String api : apis) {
								String[] apis2=api.split(":");
								apimap.put(apis2[0], apis2[1]);
							}
						}
						sql.setApi(apimap);
						String innerXML = element.html();
						innerXML=innerXML
								.replaceAll("<script>", "<% ")
								.replaceAll("</script>", " %>")
								.replaceAll("<text>", " %>")
								.replaceAll("</text>", "<% ")
								.replaceAll("</if>", "<%}%>")
								.replaceAll("</else>", "<%}%>")
								.replaceAll("</elseif>", "<%}%>")
								.replaceAll("<else>", "<%else{%>");
						Matcher matcher = patternout.matcher(innerXML);
						while (matcher.find()) {
							String var=matcher.group();
							var=var.substring(2,var.length()-1);
							innerXML=innerXML.replaceAll("\\$\\{"+var+"\\}", "<%="+var+"%>");
						}
						matcher = patternif.matcher(innerXML);
						while (matcher.find()) {
							String var=matcher.group(3);
							innerXML=innerXML.replaceAll("(<if){1}\\s*(if=\"){1}("+var+"){1}(\">){1}", "<%if("+var+"){%>");
						}
						matcher = patternelseif.matcher(innerXML);
						while (matcher.find()) {
							String var=matcher.group(3);
							innerXML=innerXML.replaceAll("(<elseif){1}\\s*(if=\"){1}("+var+"){1}(\">){1}", "<%else if("+var+"){%>");
						}
						innerXML=innerXML.replaceAll("(<%\\}%>){1}\\s{0,}(<%else\\{%>){1}", "<%}else{%>")
								.replaceAll("(<%\\}%>){1}\\s{0,}(<%else if\\(){1}", "<%}else if(");
						sql.setSqlTemplate(innerXML);
						sqlMap.put(sql.id,sql);
					}
				}
			
		}
		
		return sqlMap;
	}
	public static ScriptEngine engine;
	public static void initScriptEngine(){
		LogUtil.info(XmlSqlParse.class, "初始化sql模板");
		ScriptEngineManager scriptEngineManager=new ScriptEngineManager(Thread.currentThread().getContextClassLoader());
		engine=scriptEngineManager.getEngineByName("javascript");
		String templateNativeJsPath=SqlMake.class.getClassLoader().getResource("www/springmvcplus/com/util/system/template-native.js").getPath();
		try {
			engine.eval(new FileReader(FileUtil.getFile(templateNativeJsPath)));
			engine.eval("template.config('compress',true);template.config('escape',false);");
			Map<String,Sql> sqls = XmlSqlParse.getSqlTemplate();
			for (Sql sql : sqls.values()) {
				String sqlId=sql.getId();
				String sqlTemplate=sql.getSqlTemplate();
				sqlTemplate=sqlTemplate.replaceAll("\\s", " ").replaceAll("( ){2,}", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").trim();
				System.out.println(sqlTemplate);
				engine.eval("var "+sqlId+" = template.compile(\""+sqlTemplate+"\");");
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
