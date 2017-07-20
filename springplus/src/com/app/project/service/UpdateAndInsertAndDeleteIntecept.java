package com.app.project.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.modes.Job;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.IdManage;
import www.springmvcplus.com.util.JobManager;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.StringUtil;
import www.springmvcplus.com.util.system.SqlUtil;

import com.alibaba.fastjson.JSON;
import com.app.project.jobs.UserTripJobs;
import com.app.project.mode.Answer;
import com.app.project.mode.AnswerComments;
import com.app.project.mode.AnswerYes;
import com.app.project.mode.Ask;
import com.app.project.mode.Custom;
import com.app.project.mode.CustomImportanceDay;
import com.app.project.mode.Group;
import com.app.project.mode.GroupJournal;
import com.app.project.mode.GroupJournalComment;
import com.app.project.mode.GroupNotice;
import com.app.project.mode.GroupTrip;
import com.app.project.mode.MoneyLog;
import com.app.project.mode.NotifyMessage;
import com.app.project.mode.User;
import com.app.project.mode.UserCarPolicyLog;
import com.app.project.mode.UserFriendsAsk;
import com.app.project.mode.UserImgsShare;
import com.app.project.mode.UserTrip;
import com.app.project.mode.UserVisitLog;
import com.app.project.util.Getui;
import com.app.project.util.PublicUtil;
import com.app.project.util.Result;
import com.app.project.util.UserTripJobUitl;
import com.ibm.db2.jcc.t4.ob;
@Service
public class UpdateAndInsertAndDeleteIntecept {
	public static enum HandleType{update,delete,save}
	@Resource
	BaseDao baseDao;
	
	public void saveAndUpdateBefore(Object entity,Result result,HandleType handleType) {
		//公共字段增加初始化
		if (entity != null && handleType == HandleType.save) {
			try {
				Field field = entity.getClass().getDeclaredField("createTime");
				field.setAccessible(true);
				field.set(entity, DateUtil.getDate());
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
			}
		}
		if (entity instanceof CustomImportanceDay) {
			CustomImportanceDay customImportanceDay = (CustomImportanceDay) entity;
			System.out.println(customImportanceDay);
		}
		if (entity instanceof MoneyLog) {//体现
			MoneyLog moneyLog = (MoneyLog) entity;
			if (handleType== HandleType.save) {
				//判断现在的金额是否可以体现
				Double money = Double.valueOf(baseDao.getSingleResult("select money-"+moneyLog.getMoney()+" from user where id='"+moneyLog.getUserId()+"'"));
				if (money>=0) {//如果剩余余额大于0可以体现
					moneyLog.setContext("提取"+moneyLog.getMoney()+"元");
					moneyLog.setMoneyTime(DateUtil.dateFormat(DateUtil.dateMath(new Date(), DateUtil.Date, 1), "yyyy-MM-dd HH:mm:ss"));
					moneyLog.setState("进行中");
					moneyLog.setTitle("提取现金");
				}else {
					result.setErrorCode(1);
					result.setErrorMessage("余额不足，不可以体现");
				}
			}
		}
		if (entity instanceof User && handleType==HandleType.update) {
			User user = (User) entity;
			if (StringUtil.hashText(user.getTripWarn())) {//判断是否修改了tripWarn    修改了之后需要更改用户的提醒任务
				String tripwarn = baseDao.getSingleResult("select tripwarn from user where id = '"+user.getId()+"'");
				if (!tripwarn.equals(user.getTripWarn())) {
					user.setIsUpdateTripWarn(true);
				}
			}
		}
		if (entity instanceof Ask && handleType == HandleType.save) {
			Ask ask = (Ask) entity;
			ask.setAllYesCount(0);
			ask.setAnswerCount(0);
		}
		if (entity instanceof Answer && handleType == HandleType.save) {
			Answer answer = (Answer) entity;
			answer.setCommentCount(0);
			answer.setYesCount(0);
		}
		if (entity instanceof GroupJournalComment) {
			GroupJournalComment groupJournalComment = (GroupJournalComment) entity;
			if (groupJournalComment.getContext().equals("_Fabulous_")) {//判断是否重复点赞
				String singleResult = baseDao.getSingleResult("select count(*) from GroupJournalComment where journalId='"+groupJournalComment.getJournalId()+"' and userId='"+groupJournalComment.getUserId()+"' and context ='_Fabulous_'");
				if (Integer.valueOf(singleResult) >0) {
					result.setErrorCode(1);
					result.setErrorMessage("重复点赞");
				}
			}
		}
		
		if (entity instanceof GroupTrip) {
			GroupTrip groupTrip = (GroupTrip) entity;
			if (StringUtil.hashText(groupTrip.getStartTime())) {
				groupTrip.setStartDate(groupTrip.getStartTime().substring(0,10));
			}
			if (StringUtil.hashText(groupTrip.getEndTime())) {
				groupTrip.setEndDate(groupTrip.getEndTime().substring(0,10));
			}
			if (handleType==handleType.update) {
				if (!groupTrip.getStartTime().equals(baseDao.getSingleResult("select starttime from groupTrip where id='"+groupTrip.getId()+"'"))) {
					groupTrip.setIsVisitTimeUpdate(true);
				}
			}
		}
		
		if (entity instanceof GroupNotice) {
			if (handleType == handleType.save) {
				GroupNotice groupNotice = (GroupNotice) entity;
				System.out.println(groupNotice);
			}
		}
		if (entity instanceof UserTrip) {
			UserTrip userTrip = (UserTrip) entity;
			if (handleType==HandleType.save) {
				userTrip.setState(1);
			}
			if (StringUtil.hashText(userTrip.getVisitTime())) {
				userTrip.setVisitDate(userTrip.getVisitTime().substring(0, 10));
			}
			if (handleType==handleType.update) {
				if (!StringUtil.valueOf(userTrip.getVisitTime()).equals(baseDao.getSingleResult("select visittime from usertrip where id='"+userTrip.getId()+"'"))) {
					userTrip.setIsVisitTimeUpdate(true);
				}
			}
		}
		/*if(entity instanceof User){
			if (handleType == handleType.update) {
				User user = (User) entity;
				if (StringUtil.hashText(user.getType()) && user.getType()==1) {//如果是同意添加入团
					baseDao.update("update NotifyMessage set isread=1 where id='"+user.getNotifyId()+"'");
				}
			}
		}*/
		//通知消息
		if (entity instanceof NotifyMessage) {
			if (handleType == HandleType.save) {//如果新增
				NotifyMessage notifyMessage = (NotifyMessage) entity;
				notifyMessage.setIsread(0);//新添加消息为未读
			}
		}
		//创建客户
		if (entity instanceof Custom) {
			Custom custom = (Custom) entity;
			if (handleType==HandleType.save) {//如果是增加
				//判断名字是否重复
				int countName = Integer.valueOf(baseDao.getSingleResult("select count(*) from custom where userId ='"+custom.getUserId()+"' and name='"+custom.getName()+"'"));
				if (countName != 0) {//名称重复不能添加
					result.setErrorCode(1);
					result.setErrorMessage("名称重复");
					custom.setAddState(0);
				}else {
					//添加默认的字段
					custom.setAbc(PublicUtil.getShouzimu(custom.getName()));
					custom.setLevel("D");
					custom.setAddState(1);
					if (StringUtil.hashText(custom.getBirthDay())) {
						custom.setAge(PublicUtil.getAge(custom.getBirthDay()));
						custom.setZodiac(PublicUtil.getConstellation(custom.getBirthDay()));
					}
				}
			}
			
		}
		
	}
	public static void main(String[] args) {
	}
	/**
	 * sqlId
	 * @param sqlId
	 * @param t
	 * @return
	 */
	public void saveAndUpdateEnd(Object entity,Result result,HandleType handleType) {
		//体现
		if (entity instanceof MoneyLog) {
			MoneyLog moneyLog =  (MoneyLog) result.getData();
			if (handleType==HandleType.save) {
				baseDao.update("update user set money=money-"+moneyLog.getMoney()+" where id='"+moneyLog.getUserId()+"'");
			}
		}
		
		if (entity instanceof UserImgsShare) {
			UserImgsShare userImgsShare = (UserImgsShare) result.getData();
			userImgsShare.setShareUrl("share/"+userImgsShare.getId());
		}
		//添加回答
		if (entity instanceof Answer && handleType == HandleType.save) {
			Answer answer = (Answer) entity;
			baseDao.execute("update ask set answerCount=answerCount+1,bestAnswerId='"+baseDao.getSingleResult("select a.id from answer a join user b on a.userId = b.id where a.askId = '"+answer.getAskId()+"' order by b.iszhuanjia desc,a.yesCount desc limit 0,1")+"' where id = '"+answer.getAskId()+"'");
			NotifyMessage notifyMessage=new NotifyMessage();
			Ask ask=baseDao.getModel("select title from ask where id='"+answer.getAskId()+"'", Ask.class);
			notifyMessage.setContext(ask.getTitle());
			User user = baseDao.getModel("select name,imgUrl from user where id='"+answer.getUserId()+"'",User.class);
			notifyMessage.setTitle(user.getName()+"回答了您的提问");
			notifyMessage.setFromUserId(answer.getUserId());
			notifyMessage.setIsread(0);
			notifyMessage.setNotifyType("4");
			notifyMessage.setToUserId(ask.getUserId());
			baseDao.save(notifyMessage);
			Getui.sendMessage(notifyMessage.getToUserId(), notifyMessage.getTitle());
		}
		//添加 回答的 评论
		if (entity instanceof AnswerComments && handleType == HandleType.save) {
			AnswerComments answer = (AnswerComments) entity;
			baseDao.execute("update answer set commentCount=commentCount+1 where id = '"+answer.getAnswerId()+"'");
		}
		//点赞
		if (entity instanceof AnswerYes && handleType == HandleType.save) {
			AnswerYes answer = (AnswerYes) entity;
			Integer count = Integer.valueOf(baseDao.getSingleResult("select count(*) from AnswerYes where answerId='"+answer.getAnswerId()+"' and userId='"+answer.getUserId()+"'"));
			if (count > 0) {
				result.setErrorCode(1);
				result.setErrorMessage("重复点赞");
			}else {
				baseDao.execute("update answer set yesCount=yesCount+1 where id = '"+answer.getAnswerId()+"'");
				baseDao.execute("update ask set allYesCount=allYesCount+1,bestAnswerId='"+baseDao.getSingleResult("select a.id from answer a join user b on a.userId = b.id where a.askId = (select askid from answer where id = '"+answer.getAnswerId()+"') order by b.iszhuanjia desc,a.yesCount desc limit 0,1")+"' where id = (select askid from answer where id = '"+answer.getAnswerId()+"')");
			}
			NotifyMessage notifyMessage=new NotifyMessage();
			Answer ask=baseDao.getModel("select * from Answer where id='"+answer.getAnswerId()+"'", Answer.class);
			if (ask.getAnswerType().equals("text")) {
				notifyMessage.setContext(ask.getContext());
			}else {
				notifyMessage.setContext("语音");
			}
			User user = baseDao.getModel("select name,imgUrl from user where id='"+answer.getUserId()+"'",User.class);
			notifyMessage.setTitle(user.getName()+"给你点了赞");
			notifyMessage.setFromUserId(answer.getUserId());
			notifyMessage.setIsread(0);
			notifyMessage.setNotifyType("3");
			notifyMessage.setToUserId(ask.getUserId());
			baseDao.save(notifyMessage);
			Getui.sendMessage(notifyMessage.getToUserId(), notifyMessage.getTitle());
		}
		
		if (entity instanceof GroupNotice) {
			GroupNotice groupNotice=(GroupNotice) entity;
			
			List<User> users = baseDao.getListModels("select name,imgUrl from user where groupid='"+groupNotice.getGroupId()+"'",User.class);
			for (User user : users) {
				NotifyMessage notifyMessage=new NotifyMessage();
				notifyMessage.setContext(groupNotice.getNoticeText());
				notifyMessage.setTitle("团队通知");
				notifyMessage.setFromUserId("");
				notifyMessage.setIsread(0);
				notifyMessage.setNotifyType("9");
				notifyMessage.setToUserId(user.getId());
				baseDao.save(notifyMessage);
				Getui.sendMessage(notifyMessage.getToUserId(), notifyMessage.getTitle());
				
			}
		}
		if (entity instanceof UserCarPolicyLog) {
			UserCarPolicyLog userCarPolicyLog = (UserCarPolicyLog) result.getData();
			userCarPolicyLog.setCustomName(baseDao.getSingleResult("select name from custom where id ='"+userCarPolicyLog.getCustomId()+"'"));
			userCarPolicyLog.setCreateTime(userCarPolicyLog.getCreateTime().substring(0, 10));
			UserCarPolicyLog useCarPolicyLogparam = (UserCarPolicyLog) entity;
			if (StringUtil.hashText(useCarPolicyLogparam.getImgurl())) {
				baseDao.update("update custom set imgUrls=imgUrls++CAST(',"+useCarPolicyLogparam.getImgurl()+"' as char) where id='"+useCarPolicyLogparam.getCustomId()+"'");
			}
		}
		if (entity instanceof GroupJournal) {
			GroupJournal groupJournal = (GroupJournal) result.getData();
			User user = baseDao.getModel("select b.name,b.imgurl from groupJournal a join user b on a.userId = b.id where a.id='"+groupJournal.getId()+"'",User.class);
			groupJournal.setUserImgUrl(user.getImgUrl());
			groupJournal.setUserName(user.getName());
		}
		if (entity instanceof GroupJournalComment) {
			GroupJournalComment groupJournal = (GroupJournalComment) result.getData();
			User user = baseDao.getModel("select b.name,b.imgurl from GroupJournalComment a join user b on a.userId = b.id where a.id='"+groupJournal.getId()+"'",User.class);
			groupJournal.setUserImgUrl(user.getImgUrl());
			groupJournal.setUserName(user.getName());
			
			NotifyMessage notifyMessage=new NotifyMessage();
			GroupJournal ask=baseDao.getModel("select * from GroupJournal where id='"+groupJournal.getJournalId()+"'", GroupJournal.class);
			notifyMessage.setContext(ask.getTitle());
			User user2 = baseDao.getModel("select name,imgUrl from user where id='"+groupJournal.getUserId()+"'",User.class);
			if (groupJournal.getContext().equals("_Fabulous_")) {//点赞
				notifyMessage.setTitle(user2.getName()+"给你点了赞");
				notifyMessage.setNotifyType("6");
			}else {
				notifyMessage.setTitle(user2.getName()+"评论了你的日志");
				notifyMessage.setNotifyType("2");
			}
			notifyMessage.setFromUserId(user2.getId());
			notifyMessage.setIsread(0);
			notifyMessage.setToUserId(ask.getUserId());
			baseDao.save(notifyMessage);
			Getui.sendMessage(notifyMessage.getToUserId(), notifyMessage.getTitle());
			
			
		}
		//如果是添加组，成功后自动修改用户的权限和用户组id
		if (entity instanceof Group  && result != null) {
			if (handleType == HandleType.save) {
				Group group = (Group) result.getData();
				String createUserId = group.getCreateUserId();
				User user = new User();
				user.setGroupId(group.getId());
				user.setGroupAuth(0);
				user.setId(createUserId);
				System.out.println(MyJSON.toJSONString(user));
				baseDao.update(user);
			}
		}
		if(entity instanceof User){
			if (handleType == handleType.update) {
				User user = (User) entity;
				if (StringUtil.hashText(user.getType()) && user.getType()==1) {//如果是同意添加入团
					User user2 =  (User)result.getData();
					user2.setPassword(null);
					Group group = baseDao.getModel("select * from group_ where id = "+user2.getGroupId(),Group.class);
					if (group!=null) {
						user2.setRongCloudGroupId(group.getRongCloudGroupId());
						user2.setGroupName(group.getGroupName());
					}
				}
				//判断是否修改了  提醒任务  默认为半个小时
				if (user.getIsUpdateTripWarn()) {
					List<Job> jobs = baseDao.getListModels("select a.*,case when b.visitTime is null then c.startTime else b.visitTime end as servicetime from job a left join usertrip b on a.serviceId = b.id left join grouptrip c on a.serviceId=c.id where a.userId = '"+user.getId()+"'",Job.class);
					baseDao.delete("delete from job where userId = '"+user.getId()+"'");
					for (Job job : jobs) {
						Date timeRun = JobManager.getDateByVisitDate(job.getServicetime(), user.getTripWarn());
						Map<String,Object> map = JSON.parseObject(job.getJsonData(), Map.class);
						JobManager.addJob(job.getJobId(), UserTripJobs.class, timeRun, map);
					}
				}
			}
		}
		
		if (entity instanceof UserTrip) {
			UserTrip userTripParam=(UserTrip) entity;
			UserTrip userTrip = (UserTrip) result.getData();
			Map<String, Object> map = baseDao.getMap("select b.name as customName,b.level as customLevel,UNIX_TIMESTAMP()-cast(UNIX_TIMESTAMP(a.visitTime) as SIGNED INTEGER) as triptimeout from usertrip a join custom b on a.visitCustomId = b.id where a.id = '"+userTrip.getId()+"'");
			userTrip.setVisitCustomName(StringUtil.valueOf(map.get("customName")));
			userTrip.setVisitCustomLevel(StringUtil.valueOf(map.get("customLevel")));
			userTrip.setTriptimeout(StringUtil.valueOf(map.get("triptimeout")));
			if (handleType==HandleType.save) {
				//添加提醒任务
				if (userTrip.getIsWarn()==1) {
					UserTripJobUitl.addOrUpdateJob(userTrip,"userTrip");
				}
			}
			if (handleType==HandleType.update && userTripParam.getIsVisitTimeUpdate()) {
				UserTripJobUitl.addOrUpdateJob(userTrip,"userTrip");
			}
			Map<String, String> paramap=new HashMap<>();
			paramap.put("id", userTrip.getId());
			result.setData(baseDao.getMapBySqlId("userTripById", paramap));
		}
		if (entity instanceof GroupTrip) {
			GroupTrip groupTrip = (GroupTrip) result.getData();
			GroupTrip userTripParam=(GroupTrip) entity;
			if (handleType==HandleType.save) {
				//添加提醒任务
				if (groupTrip.getIsWarn()==1) {
					UserTripJobUitl.addOrUpdateJob(groupTrip,"groupTrip");
				}
			}
			if (handleType==HandleType.update && userTripParam.getIsVisitTimeUpdate()) {
				UserTripJobUitl.addOrUpdateJob(groupTrip,"groupTrip");
			}
			Object ids=groupTrip.getTripUsers();
			if (StringUtil.hashText(ids)) {
				List<Map<String, Object>> listMap = baseDao.getListMaps("select id,name,tel,imgurl from user where id "+SqlUtil.inSqlStr(ids.toString()));
				groupTrip.setTripUsers(listMap);
			}
		}
	}
	
	public void deleteBefore(Object entity,Result result) {
		
	}
	/**
	 * sqlId
	 * @param sqlId
	 * @param t
	 * @return
	 */
	public void deleteEnd(Object entity,Result result) {
		if (Group.class.isInstance(entity)) {
			Group group =(Group) entity;
			//解散团队
			baseDao.update("update user set groupId=null,groupAuth=null where groupId = "+group.getId());
		}
	}
	
}
