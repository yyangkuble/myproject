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
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.EntityMake;
import www.springmvcplus.com.util.ExcelUitil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.EntityRootPakage;

import com.alibaba.fastjson.JSON;
import com.app.project.service.SelectIntecept;
import com.app.project.service.UpdateAndInsertAndDeleteIntecept;
import com.app.project.util.Result;
@Controller
@ApiDesc("加密公用接口")
public class AESSystemAction {
	@Resource
	MyService myService;
	
	@Resource
	SelectIntecept selectIntecept;
	
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @param sqlId  sqlId
	 * @param page 第几页，从第一页开始
	 */
	@RequestMapping("/selectList/{sqlId}")
	public void select_fenye(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		Map<String, String> mapParamerts=AESUtil.converParameter(request);
		Result result = new Result();
		selectIntecept.selectBefore(sqlId, mapParamerts,result);
		myService.selectList(sqlId, mapParamerts,result);
		selectIntecept.selectEnd(sqlId, result);
		ResponseUtil.print(response, result);
	}
	@RequestMapping("/selectOne/{sqlId}")
	public void select_one(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("sqlId") String sqlId) {
		Map<String, String> mapParamerts=AESUtil.converParameter(request);
		Result result = new Result();
		selectIntecept.selectBefore(sqlId, mapParamerts,result);
		myService.selectOne(sqlId, mapParamerts,result);
		selectIntecept.selectEnd(sqlId, result);
		ResponseUtil.print(response, result);
	}
	@RequestMapping(method=RequestMethod.POST,value="/save/{entityName}")
	public void insert(HttpServletRequest request,HttpServletResponse response,@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		UpdateAndInsertAndDeleteIntecept intecept=SpringBeanUtil.getBean(UpdateAndInsertAndDeleteIntecept.class);
		Result result = new Result();
		System.out.println("初始化:"+classpath);
		try {
			Object baseEntity = AESUtil.converEntity(request, Class.forName(classpath));
			intecept.saveAndUpdateBefore(baseEntity,result, UpdateAndInsertAndDeleteIntecept.HandleType.save);
			if (result.getErrorCode()==0) {
				int count = myService.save(baseEntity);
				if (count != 1) {
					result.setErrorCode(1);
				}else {
					result.setData(myService.getModelById(baseEntity));
				}
				LogManager.saveLog(request, myService);
				intecept.saveAndUpdateEnd(baseEntity,result, UpdateAndInsertAndDeleteIntecept.HandleType.save);
			}
			ResponseUtil.print(response, result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(method=RequestMethod.POST,value="/update/{entityName}")
	public void update(HttpServletRequest request,HttpServletResponse response,@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		UpdateAndInsertAndDeleteIntecept intecept=SpringBeanUtil.getBean(UpdateAndInsertAndDeleteIntecept.class);
		Result result = new Result();
		System.out.println("初始化:"+classpath);
		try {
			Object baseEntity = AESUtil.converEntity(request, Class.forName(classpath));
			intecept.saveAndUpdateBefore(baseEntity,result, UpdateAndInsertAndDeleteIntecept.HandleType.update);
			if (result.getErrorCode()==0) {
				int count = myService.update(baseEntity);
				if (count != 1) {
					result.setErrorCode(1);
				}else {
					result.setData(myService.getModelById(baseEntity));
				}
				LogManager.saveLog(request, myService);
				intecept.saveAndUpdateEnd(baseEntity,result, UpdateAndInsertAndDeleteIntecept.HandleType.update);
			}
			ResponseUtil.print(response, result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(method=RequestMethod.POST,value="/delete/{entityName}")
	public void delete(HttpServletRequest request,HttpServletResponse response,@PathVariable("entityName") String entityName) {
		String classpath=EntityRootPakage.getRootPackage(entityName)+entityName;
		UpdateAndInsertAndDeleteIntecept intecept=SpringBeanUtil.getBean(UpdateAndInsertAndDeleteIntecept.class);
		Result result = new Result();
		System.out.println("初始化:"+classpath);
		try {
			Object baseEntity = AESUtil.converEntity(request, Class.forName(classpath));
			intecept.deleteBefore(baseEntity,result);
			if (result.getErrorCode()==0) {
				int count = myService.delete(baseEntity);
				if (count != 1) {
					result.setErrorCode(1);
				}else {
					result.setData("Success");
				}
				LogManager.saveLog(request, myService);
				intecept.deleteEnd(baseEntity,result);
			}
			ResponseUtil.print(response, result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
