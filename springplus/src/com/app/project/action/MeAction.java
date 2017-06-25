package com.app.project.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.TestResultLog;
import com.app.project.mode.TestResultLogOther;
import com.app.project.mode.User;
import com.app.project.mode.UserYeji;
import com.app.project.util.PublicUtil;
import com.app.project.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.ApiParameter;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.StringUtil;

@Controller
@RequestMapping("/me")
public class MeAction {
	@Resource
	MyService myService;
	
	@RequestMapping("/ranking")
	public void ranking(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Map<String, Object> map=new HashMap<>();
		String visitCountRankingCount = myService.getSingleResultBySqlId("visitCountRankingCount", parameter);
		map.put("visitCountRankingCount", visitCountRankingCount);//访量总数
		Map<String, Object> map2 = myService.getMapBySqlId("visitCountRanking",parameter);
		map.put("visitCountRanking", map2.get("rownum"));//访量排名
		map.put("userId",map2.get("userId") );//userId
		map.put("yejiDate",parameter.get("yejiDate") );
		//件数
		String policyNumberAndPremiumRankingCount = myService.getSingleResultBySqlId("policyNumberAndPremiumRankingCount", parameter);
		map.put("policyNumberRankingCount", policyNumberAndPremiumRankingCount);
		Map<String, Object> map3 = myService.getMapBySqlId("policyNumberRanking", parameter);
		map.put("policyNumberRanking", map3.get("rownum"));
		//保费
		map.put("policyPremiumRankingCount", policyNumberAndPremiumRankingCount);
		Map<String, Object> map4 = myService.getMapBySqlId("premiumRanking", parameter);
		map.put("policyPremiumRanking", map4.get("rownum"));
		ResponseUtil.print(response, new Result(map));
	}
	
	@RequestMapping("/updateYeji")
	public void updateYeji(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		List<UserYeji> list = myService.getListModelsBySqlId("updateSelectUseryeji",parameter,UserYeji.class);
		List<UserYeji> userYejis = new ArrayList<>();
		String yearOne=DateUtil.dateFormat(new Date(), "yyyy")+"-01";
		UserYeji userYeji = new UserYeji();
		userYeji.setYejiDate(yearOne);
		userYeji.setYejimonth(Integer.valueOf(yearOne.split("-")[1]));
		userYejis.add(userYeji);
		String yearEnd=DateUtil.dateFormat(new Date(), "yyyy-MM");
		do {
			yearOne = DateUtil.dateMath(yearOne, DateUtil.Month, 1, "yyyy-MM");
			userYeji = new UserYeji();
			userYeji.setYejiDate(yearOne);
			userYeji.setYejimonth(Integer.valueOf(yearOne.split("-")[1]));
			userYejis.add(userYeji);
			if (yearOne.equals(yearEnd)) {
				break;
			}
		} while (true);
		
		for (int i = 0; i < userYejis.size(); i++) {
			UserYeji userYeji2 = userYejis.get(i);
			for (UserYeji userYeji3:list) {
				if (userYeji3.getYejiDate().equals(userYeji2.getYejiDate())) {
					userYejis.remove(i);
					userYejis.add(i, userYeji3);
				}
			}
		}
		ResponseUtil.print(response, new Result(userYejis));
	}
	public static void main(String[] args) {
	}
	
}
