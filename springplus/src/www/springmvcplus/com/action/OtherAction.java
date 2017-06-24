package www.springmvcplus.com.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.common.XmlSqlParse;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.StringUtil;

@Controller
@RequestMapping("/springmvcplus")
public class OtherAction {
	@Resource
	MyService myService;
	@Autowired  
    private Producer captchaProducer = null;
	
	@RequestMapping(value = "/Verification_Code_image")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpSession session = request.getSession();  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        
        String capText = captchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
    }  
	@RequestMapping("/initsql")
	public void initsql(HttpServletRequest request,HttpServletResponse response) {
		String sqlTemplate=request.getParameter("sqlTemplate");
		SqlResult sqlResult = SqlMake.makeSql(request, sqlTemplate);
		try {
			response.getWriter().print(MyJSON.toJSONString(sqlResult));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/sqlScriptInit")
	public void sqlScriptInit() {
		XmlSqlParse.initScriptEngine();
	}
	
}
