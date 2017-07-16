package com.app.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.SqlUtil;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.User;
import com.app.project.util.Result;
import com.ibm.db2.jcc.t4.ob;
@Service
public class SelectIntecept {
	@Resource
	BaseDao baseDao;
	
	/**
	 * 
	 * @param sqlId
	 * @param t
	 */
	public void selectBefore(String sqlId,Map<String, String> parameter,Result result) {
		if (sqlId.equals("selectCoustom")) {
			String whim = parameter.get("whimStr");
			if (StringUtil.hashText(whim)) {
				parameter.put("whimStart", StringUtil.valueOf(Integer.valueOf(whim.split("-")[0])*10000));
				parameter.put("whimEnd", StringUtil.valueOf(Integer.valueOf(whim.split("-")[1])*10000));
			}
		}
	}
	/**
	 * 有可能是map，有可能是listmap
	 * sqlId
	 * @param sqlId
	 * @param t
	 * @return
	 */
	public void selectEnd(String sqlId,Map<String, String> parameter,Result result) {
		Object listOrMap=result.getData();
		List<Map<String, Object>> listResult;
		Map<String, Object> mapResult;
		if (sqlId.equals("findUserVisitLogByCustomId")) {
			System.out.println(listOrMap);
		}
		//查询用户业绩
		if (sqlId.equals("selectUserYejiByMonth")) {
			if (listOrMap != null) {
				mapResult=(Map<String, Object>) listOrMap;
				mapResult.put("visitCount", baseDao.getSingleResultBySqlId("selectVisitCount", parameter));
			}else {
				mapResult=new HashMap<>();
				mapResult.put("visitCount", baseDao.getSingleResultBySqlId("selectVisitCount", parameter));
			}
		}
		if (sqlId.equals("myAnswerAskList")) {//我的问题  回答
			listResult=(List<Map<String, Object>>) listOrMap;
			for (Map<String, Object> map : listResult) {
				parameter.put("askId", map.get("askId").toString());
				List<Map<String, Object>> myAnswerList = baseDao.getListMapsBySqlId("myAnswerList", parameter);
				map.put("myAnswerList", myAnswerList);
			}
			
		}
		if (sqlId.equals("groupJournalAll")) {
			listResult=(List<Map<String, Object>>) listOrMap;
			for (Map<String, Object> resultmap : listResult) {
				Map<String, String> map=new HashMap<>();
				map.put("journalId", StringUtil.valueOf(resultmap.get("id")));
				//日志点赞
				Map<String, Object> GroupJournalFabulous = baseDao.getListMapsBySqlId("GroupJournalFabulous", map,1,4);
				resultmap.put("GroupJournalFabulous", GroupJournalFabulous);
				//日志评论
				Map<String, Object> GroupJournalComment = baseDao.getListMapsBySqlId("GroupJournalComment", map,1,4);
				resultmap.put("GroupJournalComment", GroupJournalComment);
			}
		}
		//将纪念日查询出来
		if (sqlId.equals("findCustomById") && listOrMap != null) {
			Map<String, Object> resultmap=(Map<String, Object>) listOrMap;
			List<Map<String, Object>> list = baseDao.getListMaps("select * from customimportanceday where customId='"+resultmap.get("id")+"'");
			resultmap.put("customimportanceday", list);
		}
		//日历查询群组 查询一天的，需要带上具体的用户名称
		if (sqlId.equals("selectGroupTripByOneDate") && listOrMap != null) {
			listResult=(List<Map<String, Object>>) listOrMap;
			for (Map<String, Object> map : listResult) {
				String ids=(String) map.get("tripUsers");
				if (StringUtil.hashText(ids)) {
					List<Map<String, Object>> listMap = baseDao.getListMaps("select id,name,tel,imgurl from user where id "+SqlUtil.inSqlStr(ids));
					map.put("tripUsers", listMap);
				}
			}
		}
		//查询所有团队行程
		if (sqlId.equals("GroupTrip") && listOrMap != null) {
			List<Map<String, Object>> resultList=(List<Map<String, Object>>) listOrMap;
			for (Map<String, Object> resultmap : resultList) {
				String ids=(String) resultmap.get("tripUsers");
				if (StringUtil.hashText(ids)) {
					List<Map<String, Object>> listMap = baseDao.getListMaps("select id,name,tel,imgurl from user where id "+SqlUtil.inSqlStr(ids));
					resultmap.put("tripUsers", listMap);
				}
			}
		}
		if (sqlId.equals("selectUserByTel")) {
			mapResult=(Map<String, Object>) listOrMap;
			if (! StringUtil.hashText(mapResult.get("zhifubao"))) {
				mapResult.put("zhifubao", "");
				mapResult.put("realName", "");
			}
		}
		
		if (sqlId.equals("plancustomlist")) {
			listResult=(List<Map<String, Object>>) listOrMap;
			for (Map<String, Object> map : listResult) {
				Object idtype = map.get("idtype");
				if (idtype.equals("trip")) {
					List<Map<String, Object>> listMaps = baseDao.getListMaps("select visitDate,visitProject from usertrip where visitCustomId='"+map.get("customid")+"' and state=1 and left(visittime,7)='"+map.get("tripdate")+"' order by visitTime desc limit 0,3");
					map.put("customTrip", listMaps);
				}
			}
		}
		
	}
	
	
	
}
