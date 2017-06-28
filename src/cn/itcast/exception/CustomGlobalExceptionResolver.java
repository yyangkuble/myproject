package cn.itcast.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomGlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception e) {
		// TODO Auto-generated method stub
		ModelAndView modelAndView = new ModelAndView();
		if (e instanceof CustomException) {
			CustomException myException = (CustomException) e;
			modelAndView.addObject("msg", myException.getMessage());
			modelAndView.addObject("code", myException.getCode());
		}else{
			modelAndView.addObject("msg", "ÏµÍ³·±Ã¦");
		}
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
