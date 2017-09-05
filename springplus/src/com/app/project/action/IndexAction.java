package com.app.project.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.Menu;
import com.app.project.mode.User;
import com.app.project.mode.UserImgsShare;
import com.app.project.util.Getui;
import com.app.project.util.Result;

import www.springmvcplus.com.services.LogManager;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.StringUtil;


@Controller
public class IndexAction {
	@Resource
	MyService myService;
	
	
	@RequestMapping("/{tel}/friends")
	public String askfriends(HttpServletRequest request,@PathVariable String tel) {
		User user = myService.getModel("select name,imgUrl,id from user where tel='"+tel+"'", User.class);
		request.setAttribute("user", user);
		return "askfriend";
	}
	@RequestMapping("/share/{id}")
	public String share(HttpServletRequest request,@PathVariable("id") String shareId) {
		UserImgsShare userImgsShare = myService.getModel("select * from UserImgsShare where id='"+shareId+"'", UserImgsShare.class);
		request.setAttribute("share", userImgsShare);
		return "share";
	}
	@RequestMapping("/jiaoxue")
	public String jiaoxue() {
		return "jiaoxue";
	}
	
	@RequestMapping("/")
	public String index(HttpServletRequest request,HttpServletResponse response) {
		String s1=request.getHeader("user-agent");
		String iosurl="";
		String androidurl="";
		List<Map<String, Object>> listMaps = myService.getListMaps("select * from appurl");
		for (Map<String, Object> map : listMaps) {
			if (map.get("type").equals("ios")) {
				iosurl=StringUtil.valueOf(map.get("url"));
			}else {
				androidurl=StringUtil.valueOf(map.get("url"));
			}
		}
		request.setAttribute("iosurl", iosurl);
		request.setAttribute("androidurl", androidurl);
		if(s1.contains("Android") || s1.contains("iPhone") || s1.contains("iPad")) {
			if (s1.contains("Android")) {
				request.setAttribute("type", "Android");
			}else {
				request.setAttribute("type", "ios");
			}
			return "mobileindex";
		} else {
			return "pcindex";
		}
	}
	@RequestMapping("/download/app")
	public String downloadapp(HttpServletRequest request) {
		String s1=request.getHeader("user-agent");
		if (s1.contains("Android")) {
			request.setAttribute("type", "android");
		}else {
			request.setAttribute("type", "ios");
		}
		String url = myService.getSingleResult("select * from appurl where type = '"+request.getAttribute("type")+"'");
		return "redirect:"+url;
	}
	
	/*@RequestMapping("/")
	public String index(HttpServletRequest request) {
		List<Map<String, Object>> listMaps = myService.getListMaps("SELECT CONCAT('drop table ',table_name,';') as sqls FROM information_schema.`TABLES` WHERE table_schema='test'");
		for (Map<String, Object> map : listMaps) {
			System.out.println(map.get("sqls"));
		}
		return "redirect:main";
	}*/
	@RequestMapping("/main")
	public String main(HttpServletRequest request ,HttpServletResponse response) {
		User user=(User)request.getSession().getAttribute("user");
		if (user != null) {
			List<Menu> menus = myService.getListModels("select * from sys_menu where id in(select distinct d.parentid from sys_user_role b join sys_role_menu c on b.roleid=c.roleid join sys_menu d on c.menuid=d.id where b.userid='"+user.getId()+"' and d.isused=1) order by orderby", Menu.class);
			request.setAttribute("menusparent", menus);
			return "plus/main";
		}else {
			return "plus/login";
		}
	}
	@RequestMapping("/user/login")
	public void login(HttpServletRequest request ,HttpServletResponse response) {
		HttpSession session =request.getSession();
		String result = null;
		Map<String, String> parameter=new HashMap<String, String>();
		if(session.getAttribute("KAPTCHA_SESSION_KEY") ==null){//非法求情
			result="{\"errormessage\":\"非法请求,请您停止访问\"}";
		}else if (session.getAttribute("KAPTCHA_SESSION_KEY").equals(request.getParameter("Verification_Code"))) {
			parameter.put("username", request.getParameter("username"));
			parameter.put("password", request.getParameter("password"));
			User user = myService.getModelBySqlId("weblogin", parameter, User.class);
			if (user==null) {
				int usercount = Integer.valueOf(myService.getSingleResultBySqlId("checkUserName", parameter));
				result=StringUtil.valueOf(usercount);
			}else {
				session.setAttribute("user", user);
				//用于验证用户是否登录，没有登录转到登录页面
				session.setAttribute("username", user.getUsername());
				result=MyJSON.toJSONString(user);
			}
		}else {
			result="{\"errormessage\":\"验证码不正确\"}";
		}
		try {
			LogManager.saveLog(request, myService);
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/plus/setAlias")
	public void setAlias(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Result setAlias = Getui.setAlias(parameter.get("userId"), parameter.get("cid"));
		ResponseUtil.print(response, setAlias);
	}
	
}
