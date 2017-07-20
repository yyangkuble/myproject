package com.app.project.action;

import io.rong.models.TokenResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.project.mode.Group;
import com.app.project.mode.User;
import com.app.project.mode.UserFriendsAsk;
import com.app.project.mode.UserTrip;
import com.app.project.mode.UserVisitLog;
import com.app.project.service.UpdateAndInsertAndDeleteIntecept.HandleType;
import com.app.project.util.PublicUtil;
import com.app.project.util.Result;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.ApiParameter;
import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.dao.BaseDao;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.AESUtil;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.EntityMake;
import www.springmvcplus.com.util.MyJSON;
import www.springmvcplus.com.util.ResponseUtil;
import www.springmvcplus.com.util.StringUtil;

@Controller
@RequestMapping("/user")
@ApiDesc("用户管理")
public class UserAction {
	@Resource
	MyService myService;
	/**
	 * 登录
	 * @param request
	 * @param response
	 */
	@ApiParameter(desc="登录",parameters={"tel:电话","password:密码"})
	@RequestMapping("/login1")
	public void login(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		User user = myService.getModelBySqlId("login", parameter, User.class);
		Result result = new Result();
		if (user == null) {
			int telcount = Integer.valueOf(myService.getSingleResultBySqlId("checkTel",parameter));
			if (telcount == 0) {
				result.setErrorCode(1);//电话不存在
				result.setErrorMessage("电话不存在");
			}else{
				result.setErrorCode(1);//密码错误
				result.setErrorMessage("密码错误");
			}
		}else {
			if (StringUtil.hashText(user.getGroupId())) {
				Group group = myService.getModel("select * from group_ where id = "+user.getGroupId(),Group.class);
				user.setRongCloudGroupId(group.getRongCloudGroupId());
				user.setGroupName(group.getGroupName());
				if (!StringUtil.hashText(user.getZhifubao())) {
					user.setZhifubao("");
					user.setRealName("");
				}
			}
			user.setUserLevel(1);
			result.setData(user);
		}
		try {
			response.getWriter().print(MyJSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/loginpc")
	public void loginpc(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, String> parameter=new HashMap<>();
		parameter.put("tel", request.getParameter("username"));
		parameter.put("password", request.getParameter("password"));
		User user = myService.getModelBySqlId("login", parameter, User.class);
		Result result = new Result();
		if (user == null) {
			int telcount = Integer.valueOf(myService.getSingleResultBySqlId("checkTel",parameter));
			if (telcount == 0) {
				result.setErrorCode(1);//电话不存在
				result.setErrorMessage("电话不存在");
			}else{
				result.setErrorCode(1);//密码错误
				result.setErrorMessage("密码错误");
			}
		}else {
			result.setData(user);
			request.getSession().setAttribute("user", user);
		}
		try {
			response.getWriter().print(MyJSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 注册
	 * @param request
	 * @param response
	 */
	@ApiParameter(desc="注册和更新 传递id更新，不传id添加用户， 具体参数见用户表",parameters={})
	@RequestMapping("/regist")
	public void regist(HttpServletRequest request,HttpServletResponse response) {
		User user = AESUtil.converEntity(request, User.class);
		Result result = new Result();
		if (StringUtil.hashText(user.getId())) {//如果是更新
			int res=myService.update(user);
			if (res==0) {
				result.setErrorCode(1);//未修改成功
			}else {
				result.setData(myService.getMapBySqlId("findUserById",AESUtil.converParameter(request)));
			}
		}else {
			Integer telCount = Integer.valueOf(myService.getSingleResultBySqlId("checkTel", EntityMake.makeMapByEnity(user)));
			if (telCount>0) {
				result.setErrorCode(1);
				result.setErrorMessage("电话重复，添加失败");
			}else {
				user.setTripWarn("半小时前");
				user.setWifiVideo(1);
				user.setMoney(0.00);
				user.setHistoryMoney(0.00);
				user.setUserLevel(1);//试用用户
				user.setCustomLevelChange(1);//客户自动升级
				user.setCustomWarn(1);//客户自动升级提醒
				//试用7天
				user.setVipTimeEnd(DateUtil.dateMath(DateUtil.getDate(), DateUtil.Date, 7, "yyyy-MM-dd HH:mm:ss"));
				int res=myService.save(user);
				if (res==0) {
					result.setErrorCode(1);//未保存成功
					result.setErrorMessage("系统故障");
				}else {
					//是否是邀请来的，如果是邀请来的，修改邀请记录
					Integer count = Integer.valueOf(myService.getSingleResult("select count(*) from UserFriendsAsk where friendTel = '"+user.getTel()+"'"));
					if (count >0 ) {
						//修改邀请记录
						myService.update("update UserFriendsAsk set friendUserId = '"+user.getId()+"' where friendTel = '"+user.getTel()+"'");
					}
					
					//添加融云token
					User updateUser=new User();
					updateUser.setId(user.getId());
					TokenResult rongyunToken = PublicUtil.getRongyunToken(user.getId(), user.getName(), user.getImgUrl());
					if (rongyunToken.getCode()==200) {
						updateUser.setRongCloudToken(StringUtil.valueOf(rongyunToken.getToken()));
					}else {
						updateUser.setRongCloudToken("获取token失败");
					}
					myService.update(updateUser);
					//查询用户信息
					Map<String, String> map = AESUtil.converParameter(request);
					map.put("tel", user.getTel());
					result.setData(myService.getMapBySqlId("selectUserByTel",map));
				}
			}
		}
		try {
			response.getWriter().print(MyJSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/askFriend")
	public void askFriend(UserFriendsAsk userFriendsAsk,HttpServletResponse response) {
			Result result = new Result();
			userFriendsAsk.setFriendMoney(0);
			userFriendsAsk.setIsVip(0);
			userFriendsAsk.setMyMoney(0);
			userFriendsAsk.setCreateTime(DateUtil.getDate());
			//电话不能注册，也没有被邀请过
			Integer count = Integer.valueOf(myService.getSingleResult("select count(*) from UserFriendsAsk where friendTel = '"+userFriendsAsk.getFriendTel()+"'"));
			if (count>0) {
				result.setErrorCode(1);
				result.setErrorMessage("您已经被邀请过");
			}else {
				//是否已经注册
				count = Integer.valueOf(myService.getSingleResult("select count(*) from User where tel = '"+userFriendsAsk.getFriendTel()+"'"));
				if (count>0) {
					result.setErrorCode(1);
					result.setErrorMessage("您已经是我们的用户");
				}else {
					myService.save(userFriendsAsk);
				}
			}
			ResponseUtil.print(response, result);
	}
	@RequestMapping("/UpdateNotifyMessageByUserId")
	public void UpdateNotifyMessageByUserId(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> map = AESUtil.converParameter(request);
		myService.update("update NotifyMessage set isread=1 where toUserId='"+map.get("userId")+"'");
		ResponseUtil.print(response, new Result());
	}
	/**
	 * 修改客户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changePassword")
	public void changeUserInfo(HttpServletRequest request,HttpServletResponse response) {
		User user = AESUtil.converEntity(request, User.class);
		Result result = new Result();
		if (StringUtil.hashText(user.getTel())) {//如果是更新
			myService.update("update user set password='"+user.getPassword()+"' where tel = '"+user.getId()+"'");
			result.setData(myService.getMapBySqlId("selectUserByTel",EntityMake.makeMapByEnity(user)));
		}
		ResponseUtil.print(response, result);
	}
	/**
	 * 获取融云token
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getRongCloudToken")
	public void getRongCloudToken(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Map<String, Object> map = myService.getMapBySqlId("findUserById",parameter);
		if (parameter.get("isRefresh").equals("yes")) {
			TokenResult rongyunToken = PublicUtil.getRongyunToken(map.get("id").toString(), map.get("name").toString(),StringUtil.valueOf(map.get("imgUrl")));
			if (rongyunToken.getCode()==200) {
				parameter.put("token",rongyunToken.getToken() );
				User updateUser=new User();
				updateUser.setId(map.get("id").toString());
				updateUser.setRongCloudToken(rongyunToken.getToken());
				myService.update(updateUser);
			}else {
				parameter.put("token","获取token失败");
			}
		}else {
			parameter.put("token", map.get("rongCloudToken").toString());
		}
		ResponseUtil.print(response, parameter);
	}
	
	/**
	 * 组日历下面的业绩接口
	 * groupId：组id
	 * startDate： 开始日期
	 * endDate： 结束日期
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findGroupUsersYeji")
	public void findGroupUsersYeji(HttpServletRequest request ,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Result result = new Result();
		String sql="select a.id as userId,a.groupAuth,imgUrl,a.name,concat(b.policyNumber,'/',b.premium) as yeji from user a left join useryeji b on a.id=b.userId where a.groupid = "+parameter.get("groupId")+" and (b.yejiDate='"+parameter.get("startDate").substring(0,7)+"' or b.yejiDate is null) order by b.premium desc";
		List<Map<String, Object>> listMaps = myService.getListMaps(sql);
		
		for (Map<String, Object> user : listMaps) {
			List<Map<String, Object>> list = myService.getListMaps("select concat(count(id),level) as customlevelnumber from (select distinct b.id,b.level from usertrip a join custom b on a.visitCustomId = b.id where a.userid ='"+user.get("userId")+"' and a.visitDate between '"+parameter.get("startDate")+"' and '"+parameter.get("endDate")+"') aa group by level ");
			String customlevelnumber="";
			for (Map<String, Object> map : list) {
				customlevelnumber+=map.get("customlevelnumber");
			}
			user.put("customlevelnumber", customlevelnumber);
		}
		result.setData(listMaps);
		ResponseUtil.print(response, result);
	}
	

	@RequestMapping("/base")
	public void name(HttpServletRequest request ,HttpServletResponse response) {
		Map<String, String> parameter = AESUtil.converParameter(request);
		Result result = new Result();
		
		
		ResponseUtil.print(response, result);
	}
	
	public static void main(String[] args) {
		Map<String, String> paramters=new HashMap<>();
		paramters.put("tel", "12345678912");
		paramters.put("password", "123456");
		String jsonText = MyJSON.toJSONString(paramters);
		//接下来对jsonText进行aes加密，具体如何加密，Android联系宋荣洋，ios联系沈震
		//将加密后的文本，使用p字段传参给后台即可
	}
	
	
}
