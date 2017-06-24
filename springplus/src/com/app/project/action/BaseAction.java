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
@RequestMapping("/base")
public class BaseAction {
	@Resource
	MyService myService;
	
	@RequestMapping("/visitProject")
	public void visitProject(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = myService.getListMapsBySqlId("VisitProject");
		ResponseUtil.print(response, new Result(list));
	}
	
	@RequestMapping("/customhope")
	public void customhope(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = myService.getListMapsBySqlId("customhope");
		ResponseUtil.print(response, new Result(list));
	}
	@RequestMapping("/customtype")
	public void customtype(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = myService.getListMapsBySqlId("customtype");
		ResponseUtil.print(response, new Result(list));
	}
	@RequestMapping("/ordercustom")
	public void ordercustom(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> list = myService.getListMapsBySqlId("ordercustom");
		ResponseUtil.print(response, new Result(list));
	}
	@RequestMapping("/getQiniuToken")
	public void getQiniuToken(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> tonken=new HashMap<>();
		tonken.put("token", "q3QFc_5z2NbmR58TQunnOfCpayWkEBAHcNglH6ZG:yzj0KDjwhmFZfCwWc0I6_1j9zyE=:eyJzY29wZSI6Imluc3VyYW5jZXByb2plY3QiLCJkZWFkbGluZSI6MTgxMTc1NDMyMH0=");
		ResponseUtil.print(response, tonken);
		
	}
	
	@RequestMapping("/getAllQuestion")
	public void getAllQuestion(HttpServletRequest request,HttpServletResponse response) {
		List<Map<String, Object>> listMaps = myService.getListMaps("select id,question from test_question order by id");
		List<Map<String, Object>> listMaps2 = myService.getListMaps("select questionId,answer,orderby as ABC,mark as score from test_answer order by id,orderby");
		Map<String, Object> map= new HashMap<>();
		map.put("questions", listMaps);
		map.put("answer", listMaps2);
		ResponseUtil.print(response, map);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMark")
	public void getMark(HttpServletRequest request,HttpServletResponse response) {
		TestResultLog resultLog=new TestResultLog();
		Map<String, String> map = AESUtil.converParameter(request);
		//添加基础信息
		resultLog.setTesterUserId(map.get("testerUserId"));
		resultLog.setTestedBirthday(map.get("testedBirthday"));
		resultLog.setTestedName(map.get("testedName"));
		resultLog.setTestedTel(map.get("testedTel"));
		resultLog.setTestedZodiac(PublicUtil.getConstellation(map.get("testedBirthday")));
		resultLog.setTestTime(DateUtil.getDate());
		resultLog.setId(IdManage.getTimeUUid());
		//计算评分和评语
		for (int i = 1; i <= 64; i++) {
			myService.save("insert into test_aswer_log values('"+resultLog.getId()+"','"+i+"',"+map.get("question"+i)+")");
		}
		float allscore=0;
		List<Map<String, Object>> listMaps = myService.getListMaps("select a.questionType2,sum(b.score) as projectavg from test_question a,test_aswer_log b where a.id=b.questionid and b.resultLogId='"+resultLog.getId()+"' group by questiontype2");
		for (Map<String, Object> map2 : listMaps) {
			allscore+=Double.valueOf(StringUtil.valueOf(map2.get("projectavg")));
			TestResultLogOther testResultLogOther = new TestResultLogOther();
			Float float1=Float.valueOf(StringUtil.valueOf(map2.get("projectavg")));
			testResultLogOther.setScore(Math.round(float1));
			testResultLogOther.setProjectType2(map2.get("questionType2").toString());
			testResultLogOther.setResultLogId(resultLog.getId());
			testResultLogOther.setComment(myService.getSingleResult("select features from test_mark where projecttype2='"+testResultLogOther.getProjectType2()+"' and startcore <=  "+testResultLogOther.getScore()+" and endcore >="+testResultLogOther.getScore()+""));
			if (map2.get("questionType2").equals("情绪EQ")) {
				testResultLogOther.setProjectType1("个人特质");
				testResultLogOther.setOrderby(1);
			}
			if (map2.get("questionType2").equals("服从度")) {
				testResultLogOther.setProjectType1("个人特质");
				testResultLogOther.setOrderby(2);
			}
			if (map2.get("questionType2").equals("独立性")) {
				testResultLogOther.setProjectType1("个人特质");
				testResultLogOther.setOrderby(3);
			}
			if (map2.get("questionType2").equals("个性")) {
				testResultLogOther.setProjectType1("业务特质");
				testResultLogOther.setOrderby(4);
			}
			if (map2.get("questionType2").equals("抗压性")) {
				testResultLogOther.setProjectType1("业务特质");
				testResultLogOther.setOrderby(5);
			}
			if (map2.get("questionType2").equals("冒险性")) {
				testResultLogOther.setProjectType1("业务特质");
				testResultLogOther.setOrderby(6);	
			}
			if (map2.get("questionType2").equals("自信心")) {
				testResultLogOther.setProjectType1("业务特质");
				testResultLogOther.setOrderby(7);
			}
			if (map2.get("questionType2").equals("领导欲")) {
				testResultLogOther.setProjectType1("业务特质");
				testResultLogOther.setOrderby(8);
			}
			myService.save(testResultLogOther);
		}
		List<TestResultLogOther> testResultLogOthers=myService.getListModels("select * from test_resultlogother where resultLogId = '"+resultLog.getId()+"' order by orderby", TestResultLogOther.class);
		resultLog.setList(testResultLogOthers);
		long allvagScore=Math.round(allscore/8.0);
		resultLog.setScore(Integer.valueOf(StringUtil.valueOf(allvagScore)));
		if (resultLog.getScore() <= 13) {
			resultLog.setComment("未觉醒的人才");
		}else if (resultLog.getScore()>=14 && resultLog.getScore()<=17) {
			resultLog.setComment("可造之才");
		}else if (resultLog.getScore()>=18 && resultLog.getScore()<=19) {
			resultLog.setComment("业务高手");
		}else if (resultLog.getScore()>=20 && resultLog.getScore()<=22) {
			resultLog.setComment("天生好手");
		}else {
			resultLog.setComment("万中选一");
		}
		myService.save(resultLog);
		System.out.println(JSON.toJSON(resultLog));
		ResponseUtil.print(response, resultLog);
	}
	//小秘书提醒
	@RequestMapping("/secretaryRemind")
	public void secretaryRemind(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> secretaryRemind=new HashMap<>();
		Map<String, String> map = AESUtil.converParameter(request);
		List<Map<String, Object>> secretaryRemindBirthay= myService.getListMapsBySqlId("secretaryRemindBirthay", map);
		List<Map<String, Object>> secretaryRemindCar = myService.getListMapsBySqlId("secretaryRemindCar", map);
		List<Map<String, Object>> secretaryRemindImportanceday = myService.getListMapsBySqlId("secretaryRemindImportanceday", map);
		secretaryRemind.put("secretaryRemindBirthay", secretaryRemindBirthay);
		secretaryRemind.put("secretaryRemindCar", secretaryRemindCar);
		secretaryRemind.put("secretaryRemindImportanceday", secretaryRemindImportanceday);
		ResponseUtil.print(response, secretaryRemind);
	}
	//拜访建议
	@RequestMapping("/proposalVisitCustomAll")
	public void proposalVisitCustom(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> secretaryRemind=new HashMap<>();
		Map<String, String> map = AESUtil.converParameter(request);
		map.put("type", "birthay");
		List<Map<String, Object>> birthay = myService.getListMapsBySqlId("proposalVisitCustom", map);
		map.put("type", "healthy");
		List<Map<String, Object>> healthy = myService.getListMapsBySqlId("proposalVisitCustom", map);
		map.put("type", "money");
		List<Map<String, Object>> money = myService.getListMapsBySqlId("proposalVisitCustom", map);
		map.put("type", "family");
		List<Map<String, Object>> family = myService.getListMapsBySqlId("proposalVisitCustom", map);
		map.put("type", "longVisit");
		List<Map<String, Object>> longVisit = myService.getListMapsBySqlId("proposalVisitCustom", map);
		map.put("type", "custionData");
		List<Map<String, Object>> custionData = myService.getListMapsBySqlId("proposalVisitCustom", map);
		
		secretaryRemind.put("birthay", birthay);
		secretaryRemind.put("healthy", healthy);
		secretaryRemind.put("money", money);
		secretaryRemind.put("family", family);
		secretaryRemind.put("longVisit", longVisit);
		secretaryRemind.put("custionData", custionData);
		System.out.println(secretaryRemind);
		ResponseUtil.print(response, secretaryRemind);
	}
	//组的根据日历获取时间
	@RequestMapping("/selectGroupTripByDate")
	public void selectGroupTripByDate(HttpServletRequest request,HttpServletResponse response) {
		Result result=new Result();
		List<Map<String, Object>> list=new ArrayList<>();
		Map<String, String> map = AESUtil.converParameter(request);
		String startDate = map.get("startDate");
		String endDate = map.get("endDate");
		long endtimelong = DateUtil.dateFormat(endDate, "yyyy-MM-dd").getTime();
		while (true) {
			Map<String, Object> mapresult=new HashMap<>();
			System.out.println(startDate);
			map.put("date", startDate);
			map.put("endDate", DateUtil.dateMath(startDate, DateUtil.Date, 1, "yyyy-MM-dd"));
			List<Map<String, Object>> listMaps = myService.getListMapsBySqlId("selectGroupTripByDate", map);
			mapresult.put("date", startDate);
			mapresult.put("data", listMaps);
			list.add(mapresult);
			startDate = DateUtil.dateMath(startDate, DateUtil.Date, 1, "yyyy-MM-dd");
			long time = DateUtil.dateFormat(startDate, "yyyy-MM-dd").getTime();
			if (time>endtimelong) {
				break;
			}
		}
		result.setData(list);
		System.out.println(list);
		ResponseUtil.print(response, result);
	}
	//用户的根据日历获取时间
	@RequestMapping("/selectUserTripByDate")
	public void selectUserTripByDate(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> map = AESUtil.converParameter(request);
		Result result = new Result();
		List<Map<String, Object>> list=new ArrayList<>();
		String startDate = map.get("startDate");
		String endDate = map.get("endDate");
		long endtimelong = DateUtil.dateFormat(endDate, "yyyy-MM-dd").getTime();
		while (true) {
			Map<String, Object> mapresult=new HashMap<>();
			System.out.println(startDate);
			List<Map<String, Object>> listMaps = myService.getListMaps("select b.level from usertrip a join custom b on a.visitCustomId = b.id where a.userId='"+map.get("userId")+"' and a.visitDate ='"+startDate+"' order by b.level");
			mapresult.put("date", startDate);
			mapresult.put("data", listMaps);
			list.add(mapresult);
			startDate = DateUtil.dateMath(startDate, DateUtil.Date, 1, "yyyy-MM-dd");
			long time = DateUtil.dateFormat(startDate, "yyyy-MM-dd").getTime();
			if (time>endtimelong) {
				break;
			}
		}
		System.out.println(list);
		result.setData(list);
		ResponseUtil.print(response,result );
	}
	public static void main(String[] args) {
	}
}
