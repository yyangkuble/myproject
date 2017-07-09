package com.app.project;

import static org.junit.Assert.*;
import io.rong.RongCloud;
import io.rong.models.TokenResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.app.project.util.PublicUtil;

import www.springmvcplus.com.util.FileUtil;
import www.springmvcplus.com.util.TestUtil;

public class JavaTest {
	
	@Test
	public void registUser() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("tel", "12345789");
		map.put("name", "宋荣洋1");
		String body = TestUtil.aesTest("user/regist", map);
		System.out.println(body);
	}
	@Test
	public void selectOne() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupId", "456");
		String body = TestUtil.aesTest("selectList/GroupTrip", map);
		System.out.println(body);
	}
	@Test
	public void selectList() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("customId", "201706282100553790");
		String body = TestUtil.aesTest("selectList/findUserVisitLogByCustomId", map);
		System.out.println(body);
	}
	@Test
	public void changeUserInfo() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("id", "201705302153234200");
		map.put("name", "宋荣洋2");
		String body = TestUtil.aesTest("user/changeUserInfo", map);
		System.out.println(body);
	}
	@Test
	public void getToken() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("id", "201705302153234200");//P4ai7Ry6oye1F6u5KFwWHpos+MI/XccOb5RME2Sbivln1Cz0QbxpMG
		map.put("isRefresh", "no");
		String body = TestUtil.aesTest("user/getRongCloudToken", map);
		System.out.println(body);
	}
	
	@Test
	public void insertCustom() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706061110592710");
		map.put("name", "家庭保单需求客户12");
		map.put("birthDay", "1980-07-12");
		map.put("sex", "男");
		String body = TestUtil.aesTest("save/Custom", map);
		System.out.println(body);
	}
	@Test
	public void insertGroupJournal() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupId", "10022");
		map.put("title", "家庭保单需求客户12");
		map.put("userId", "1980-07-12");
		map.put("context", "男");
		map.put("imgurls", "imgurlssssssss.png");
		String body = TestUtil.aesTest("save/GroupJournal", map);
		System.out.println(body);
	}
	@Test
	public void selectCustom() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "123456");
		map.put("car", "无");
		map.put("orderType", "visitLateTime");
		String body = TestUtil.aesTest("custom/customSearch", map);
		System.out.println(body);
	}
	@Test
	public void userTripUpdateAndCreate() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "123456");
		String body = TestUtil.aesTest("user/userTripUpdateAndCreate", map);
		System.out.println(body);
	}
	@Test
	public void testgroupNotice() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("noticeText", "");
		map.put("id", "201706021147170270");
		String body = TestUtil.aesTest("update/GroupNotice", map);
		System.out.println(body);
	}
	@Test
	public void testaddCustom() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706021147170270");
		map.put("name", "宋荣洋");
		String body = TestUtil.aesTest("save/Custom", map);
		System.out.println(body);
	}
	@Test
	public void testmARK() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("testerUserId", "123");
		map.put("testedBirthday", "1989-12-19");
		map.put("testedName", "宋荣洋");
		map.put("testedTel", "18612290350");
		for (int i = 1; i < 65; i++) {
			map.put("question"+i, "3");
		}
		String body = TestUtil.aesTest("base/getMark", map);
		System.out.println(body);
	}
	@Test
	public void getQuestion() throws Exception {
		String aesTest = TestUtil.aesTest("base/getAllQuestion", null);
		System.out.println(aesTest);
	}
	@Test
	public void findGroupNotice() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupId", "123456");
		String body = TestUtil.aesTest("selectList/findGroupNotice", map);
		System.out.println(body);
	}
	@Test
	public void testName() throws Exception {
		TokenResult rongyunToken = PublicUtil.getRongyunToken("123432", "测试",null);
		System.out.println(rongyunToken);
	}
	@SuppressWarnings("resource")
	@Test
	public void testName1() throws Exception {
		File file = FileUtil.getFile("E:\\new8.txt");
		 try {
             String encoding="UTF-8";
                 InputStreamReader read = new InputStreamReader(
                 new FileInputStream(file),encoding);//考虑到编码格式
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String lineTxt = null;
                 while((lineTxt = bufferedReader.readLine()) != null){
                     System.out.println(lineTxt);
                 }
                 read.close();
     } catch (Exception e) {
         System.out.println("读取文件内容出错");
         e.printStackTrace();
     }
	}
	
	@Test
	public void insertGroupTest() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupName", "123");
		map.put("rongCloudGroupId", "dfdf");
		map.put("createUserId", "宋荣洋");
		String body = TestUtil.aesTest("save/Group", map);
		System.out.println(body);
	}
	@Test
	public void groupAddUser() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706082229201860");
		map.put("groupId", "10022");
		map.put("title", "王小五加入团队");
		String body = TestUtil.aesTest("group/groupAddUser", map);
		System.out.println(body);
	}
	@Test
	public void selectUserTripByDate() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706121129530820");
		map.put("startDate", "2017-06-01");
		map.put("endDate", "2017-06-30");
		String body = TestUtil.aesTest("base/selectUserTripByDate", map);
		System.out.println(body);
	}
	
	@Test
	public void ranking() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706061110592710");
		map.put("yejiDate", "2017-06");
		String body = TestUtil.aesTest("me/ranking", map);
		System.out.println(body);
	}
	@Test
	public void updateSelectUseryeji() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("id", "201706061110592710");
		String body = TestUtil.aesTest("selectOne/findUserById", map);
		System.out.println(body);
	}
	@Test
	public void ordercustom() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("id", "201706061110592710");
		String body = TestUtil.aesTest("base/searchCustomBaseData", map);
		System.out.println(body);
	}
	@Test
	public void findGroupUsersYeji() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupId", "10022");
		map.put("startDate", "2017-06-01");
		map.put("endDate", "2017-06-31");
		String body = TestUtil.aesTest("user/findGroupUsersYeji", map);
		System.out.println(body);
	}
	@Test
	public void personnelPool() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("groupId", "10022");
		String body = TestUtil.aesTest("base/personnelPool", map);
		System.out.println(body);
	}
	@Test
	public void findPayAsks() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706082229201860");
		map.put("mylisens", "yes");
		String body = TestUtil.aesTest("selectList/findPayAsks", map);
		System.out.println(body);
	}
	@Test
	public void findPayAsksyes() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706111446064700");
		map.put("payAskId", "1232");
		String body = TestUtil.aesTest("payAsk/yes", map);
		System.out.println(body);
	}
	
	@Test
	public void findAsks() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("answerId", "sdfasfas");
		String body = TestUtil.aesTest("selectList/answerComment", map);
		System.out.println(body);
	}
	
	
	@Test
	public void saveAnswer() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("userId", "201706082229201860");
		String body = TestUtil.aesTest("selectList/myAnswerAskList", map);
		System.out.println(body);
	}
	
	@Test
	public void saveuserTrip() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("isWarn", "1");
		map.put("state", "1");
		map.put("visitTime", "2017-07-09 22:36");
		map.put("visitType", "营销");
		map.put("visitProject", "增员");
		map.put("visitCustomId", "201706221835388470");
		map.put("userId", "201706091815124190");
		//map.put("id", "201707081632213320");
		String body = TestUtil.aesTest("save/UserTrip", map);
		System.out.println(body);
	}
	@Test
	public void savegroupTrip() throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("isWarn", "1");
		map.put("groupId", "10022");
		map.put("startTime", "2017-07-08 23:36");
		map.put("endTime", "2017-09-20 15:01");
		map.put("tripType", "产说会");
		map.put("tripUsers", "201706061110592710,201706082229201860,201706091111304030,201706121129530820");
		map.put("context", "test");
		map.put("tile", "Tuttydfgf1024");
		//map.put("id", "201707081632213320");
		String body = TestUtil.aesTest("save/GroupTrip", map);
		System.out.println(body);
	}
}
