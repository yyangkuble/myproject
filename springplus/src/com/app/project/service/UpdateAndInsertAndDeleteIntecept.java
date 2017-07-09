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
import com.app.project.mode.Group;
import com.app.project.mode.GroupJournal;
import com.app.project.mode.GroupJournalComment;
import com.app.project.mode.GroupNotice;
import com.app.project.mode.GroupTrip;
import com.app.project.mode.NotifyMessage;
import com.app.project.mode.User;
import com.app.project.mode.UserCarPolicyLog;
import com.app.project.mode.UserFriendsAsk;
import com.app.project.mode.UserImgsShare;
import com.app.project.mode.UserTrip;
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
				if (!userTrip.getVisitTime().equals(baseDao.getSingleResult("select visittime from usertrip where id='"+userTrip.getId()+"'"))) {
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
			}else {//修改客户
				if (StringUtil.hashText(custom.getBirthDay())) {
					int countName = Integer.valueOf(baseDao.getSingleResult("select count(*) from custom where userId ='"+custom.getUserId()+"' and name='"+custom.getName()+"'"));
					if (countName != 0) {//名称重复不能添加
						result.setErrorCode(1);
						result.setErrorMessage("名称重复");
					}else {
						if (StringUtil.hashText(custom.getBirthDay())) {
							custom.setAge(PublicUtil.getAge(custom.getBirthDay()));
							custom.setZodiac(PublicUtil.getConstellation(custom.getBirthDay()));
						}
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
		if (entity instanceof UserImgsShare) {
			UserImgsShare userImgsShare = (UserImgsShare) result.getData();
			userImgsShare.setShareUrl("share/"+userImgsShare.getId());
		}
		//添加回答
		if (entity instanceof Answer && handleType == HandleType.save) {
			Answer answer = (Answer) entity;
			baseDao.execute("update ask set answerCount=answerCount+1,bestAnswerId='"+baseDao.getSingleResult("select a.id from answer a join user b on a.userId = b.id where a.askId = '"+answer.getAskId()+"' order by b.iszhuanjia desc,a.yesCount desc limit 0,1")+"' where id = '"+answer.getAskId()+"'");
			
		}
		//添加 回答的 评论
		if (entity instanceof AnswerComments && handleType == HandleType.save) {
			AnswerComments answer = (AnswerComments) entity;
			baseDao.execute("update answer set commentCount=commentCount+1 where id = '"+answer.getAnswerId()+"'");
		}
		//点赞
		if (entity instanceof AnswerYes && handleType == HandleType.save) {
			AnswerYes answer = (AnswerYes) entity;
			baseDao.execute("update answer set yesCount=yesCount+1 where id = '"+answer.getAnswerId()+"'");
			baseDao.execute("update ask set allYesCount=allYesCount+1,bestAnswerId='"+baseDao.getSingleResult("select a.id from answer a join user b on a.userId = b.id where a.askId = (select askid from answer where id = '"+answer.getAnswerId()+"') order by b.iszhuanjia desc,a.yesCount desc limit 0,1")+"' where id = (select askid from answer where id = '"+answer.getAnswerId()+"')");
		}
		
		
		if (entity instanceof UserCarPolicyLog) {
			UserCarPolicyLog userCarPolicyLog = (UserCarPolicyLog) result.getData();
			userCarPolicyLog.setCustomName(baseDao.getSingleResult("select name from custom where id ='"+userCarPolicyLog.getCustomId()+"'"));
			userCarPolicyLog.setCreateTime(userCarPolicyLog.getCreateTime().substring(0, 10));
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
					user2.setRongCloudGroupId(group.getRongCloudGroupId());
					user2.setGroupName(group.getGroupName());
				}
				//判断是否修改了  提醒任务  默认为半个小时
				if (user.getIsUpdateTripWarn()) {
					List<Job> jobs = baseDao.getListModels("select a.*,case when b.visitTime is null then c.startTime else b.visitTime end as servicetime from job a left join usertrip b on a.serviceId = b.id left join grouptrip c on a.serviceId=c.id where userId = '"+user.getId()+"'",Job.class);
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
