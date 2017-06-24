package www.springmvcplus.com.services;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.util.SpringBeanUtil;

public class SpringMVCPlusInterceptor implements HandlerInterceptor {
	/** 
     * Handler执行完成之后调用这个方法 
     */ 
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception e)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	 /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */ 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/** 
     * Handler执行之前调用这个方法 
     */ 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		SpringMVCPlusArgsConfig config = SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
		//是否登录验证
		if (config.isCheckLogin()) {
			String rootPath= request.getContextPath();
			String url = request.getRequestURI().replace(rootPath, "");  
			if (AppFastInit.UrlFilter.matcher(url).matches()) {
				return true;
			}
			//获取Session  
			HttpSession session = request.getSession();  
			String username = (String)session.getAttribute("username");  
			if(username != null){  
				return true;  
			}  
			PrintWriter out = response.getWriter();
			out.println("<html>");  
			out.println("<script type=\"text/JavaScript\">");  
			out.println("window.open ('"+request.getContextPath()+"/plus/login','_top')");  
			out.println("</script>");  
			out.println("</html>");
			return false;
		}else {
			return true;
		}
	}

}
