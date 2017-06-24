package www.springmvcplus.com.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class ResponseUtil {
	public static void print(HttpServletResponse response,Object object) {
		try {
			response.getWriter().print(MyJSON.toJSONString(object));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
