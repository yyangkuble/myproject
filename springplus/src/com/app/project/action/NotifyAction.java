package com.app.project.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notify")
public class NotifyAction {
	
	@RequestMapping("/noGroupAddUser")
	public void noGroupAddUser(HttpServletRequest request,HttpServletResponse response) {
		
	}
	
}
