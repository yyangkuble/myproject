package www.springmvcplus.com.action;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.ApiParameter;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.services.LogManager;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.services.service.SystemService;
import www.springmvcplus.com.util.EntityMake;
import www.springmvcplus.com.util.ExcelUitil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.EntityRootPakage;

import com.alibaba.fastjson.JSON;
@Controller
@ApiDesc("公用接口")
public class SystemAction {
	@Resource
	MyService myService;
	@Resource
	SpringMVCPlusArgsConfig springMVCPlusArgsConfig;
	@RequestMapping("/{jspName}")
	public String url(HttpServletRequest request ,HttpServletResponse response,@PathVariable("jspName") String jspName) {
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String string = (String) names.nextElement();
			request.setAttribute(string, request.getParameter(string));
		}
		return jspName;
	}
	@RequestMapping("plus/{jspName}")
	public String sys_url(HttpServletRequest request ,HttpServletResponse response,@PathVariable("jspName") String jspName) {
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String string = (String) names.nextElement();
			request.setAttribute(string, request.getParameter(string));
		}
		return "plus/"+jspName;
	}
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @param sqlId  sqlId
	 * @param page 第几页，从第一页开始
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{sqlId}/{page}")
	public void select(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId,@PathVariable("page") int page) {
		int maxrow=20;
		if (StringUtil.hashText(request.getParameter("maxrow"))) {
			maxrow=Integer.valueOf(request.getParameter("maxrow"));
		}
		List<Map<String, Object>> list=null;
		int allpage=1;
		if (maxrow != 0) {//不是全部
			Map<String, Object> fenyemap=myService.getListMapsBySqlId(sqlId,request, page, maxrow);
			list=(List<Map<String, Object>>) fenyemap.get("list");
			int allrow=Integer.valueOf(fenyemap.get("count").toString());
			allpage=allrow%maxrow==0?allrow/maxrow:(allrow/maxrow+1);
		}else {
			list=myService.getListMapsBySqlId(sqlId,request);
		}
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			map.put("allpage", allpage);
			map.put("max", maxrow);
			response.getWriter().print(MyJSON.toJSONString(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @param sqlId  sqlId
	 * @param page 第几页，从第一页开始
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{sqlId}/all")
	public void select_all(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		List<Map<String, Object>> list=myService.getListMapsBySqlId(sqlId,request);
		try {
			response.getWriter().print(MyJSON.toJSONString(list));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @param sqlId  sqlId
	 * @param page 第几页，从第一页开始
	 * @throws IOException 
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{sqlId}/excel")
	public void select_excel(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) throws IOException {
		SqlResult sqlResult=SqlMake.makeSql(request, sqlId);
		int maxrow=1000;//每次从数据库取出1000条数据，防止取出数据太多  出现内存溢出
		String excelname=request.getParameter("excelname");//excel名称
		excelname = ExcelUitil.saveExcel(sqlResult.getSql(),sqlResult.getParamters(), excelname, maxrow);
		try {
			response.getWriter().print(excelname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 只有一个值的
	 * @param request
	 * @param response
	 * @param sqlId sqlId
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{sqlId}/one")
	public void select_one(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		String singleResult = myService.getSingleResultBySqlId(sqlId,request);
		
		try {
			response.getWriter().print(singleResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 只有一个值的
	 * @param request
	 * @param response
	 * @param sqlId sqlId
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{entityName}/byid")
	public void select_byid(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		try {
			String tableName = EntityManager.tableMap.get(Class.forName(classpath));
			String idAttrName = EntityManager.idMap.get(Class.forName(classpath));
			String idcolumName=EntityManager.columnMap.get(Class.forName(classpath)).get(idAttrName);
			String sql="select * from "+tableName +" where "+idcolumName+"='"+request.getParameter(idcolumName)+"'";
			Map<String, Object> map = myService.getMap(sql);
			try {
				response.getWriter().print(MyJSON.toJSONString(map));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 只有一个值的
	 * @param request
	 * @param response
	 * @param sqlId sqlId
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{entityName}_all")
	public void select_entity_all(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		try {
			String tableName = EntityManager.tableMap.get(Class.forName(classpath));
			String idAttrName = EntityManager.idMap.get(Class.forName(classpath));
			String sql="";
			if (StringUtil.hashText(idAttrName)) {
				String idcolumName=EntityManager.columnMap.get(Class.forName(classpath)).get(idAttrName);
				sql="select * from "+tableName +" order by "+idcolumName;
			}else {
				sql="select * from "+tableName;
			}
			List<Map<String, Object>> list = myService.getListMaps(sql);
			try {
				response.getWriter().print(MyJSON.toJSONString(list));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 只有一行数据
	 * @param request
	 * @param response
	 * @param sqlId
	 */
	@RequestMapping(method=RequestMethod.POST,value="/select/{sqlId}/onerow")
	public void select_one_row(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		Map<String, Object> map = myService.getMapBySqlId(sqlId,request);
		try {
			response.getWriter().print(MyJSON.toJSONString(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(method=RequestMethod.POST,value="/exc/{sqlId}")
	public void exc_bysqlId(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		int result = myService.updateBySqlId(sqlId,request);
		try {
			LogManager.saveLog(request, myService);
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(method=RequestMethod.POST,value="/excs")//sqls:'[{"sql":"update/User",args:{"id":"userid","name":"changeName"}},{"sql":"exc/sqlId",args:{"id":"userid","name":"changeName"},...]'
	public void excs(HttpServletRequest request,HttpServletResponse response) {
		SystemService service=SpringBeanUtil.getBean(SystemService.class);
		int result = service.excs(request.getParameter("sqls"));
		try {
			LogManager.saveLog(request, myService);
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@ApiParameter(desc="统一上传文件",parameters={"filename:文件名字可以任意"})
	@RequestMapping(method=RequestMethod.POST,value="/uploadfile")
	public void uploadfile(HttpServletRequest request,HttpServletResponse response) {
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
		String filename="";
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                	String[] temp=file.getOriginalFilename().split("\\.");
                	filename=IdManage.getUUid()+"."+temp[temp.length-1];
                    String path=springMVCPlusArgsConfig.getInputFilePath()+filename;
                    //上传
                    try {
						file.transferTo(new File(path));
						LogManager.saveLog(request, myService);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                 
            }
        }
        try {
			response.getWriter().print(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/insert/{entityName}/byexcel")
	public void insertByExcel(HttpServletRequest request,HttpServletResponse response,@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		String excelPath=request.getParameter("excelPath");
		try {
			int result = myService.saveByExcel(Class.forName(classpath), excelPath);
			response.getWriter().print(result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
