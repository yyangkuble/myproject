package www.springmvcplus.com.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import www.springmvcplus.com.modes.RequestMode;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.system.WatcherUtil;

public class AppFastFilter implements Filter {
	public static boolean isWatch=false;//是否开启性能监控
	private static Pattern pattern =null;
	public static String baseurl="";
	public static ThreadLocal<List<Object>> threadLocal=new ThreadLocal<List<Object>>();
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		//请求id，请求url，请求时间，请求总时间，sql数量，sql耗费总时间，代码耗费总时间，线程id
		//请求表设计：请求id，请求url，请求时间，请求结束时间，线程id
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String url=httpRequest.getRequestURL().toString();
		if (pattern.matcher(url).matches()) {//如果是静态资源 不进行性能监控
			chain.doFilter(request, response);
		}else {
			List<Object> entitys=null;
			RequestMode requestMode=null;
			if (AppFastFilter.isWatch) {
				entitys=new ArrayList<Object>();
				requestMode=new RequestMode();
				requestMode.setId(IdManage.getTimeUUid());
				requestMode.setUrl(url);
				requestMode.setStartTime(System.currentTimeMillis());
				entitys.add(requestMode);
				AppFastFilter.threadLocal.set(entitys);
			}
			chain.doFilter(request, response);
			if (AppFastFilter.isWatch) {
				requestMode.setEndTime(System.currentTimeMillis());
				requestMode.setthreadId(Thread.currentThread().getId());
				if (!url.contains("watch_")) {
					WatcherUtil.insert(entitys);
				}
			}
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		pattern = Pattern.compile(".*\\.(?i)("+filterConfig.getInitParameter("isWatchFilter").trim()+")");
		baseurl=filterConfig.getServletContext().getRealPath("/");
	}

}
