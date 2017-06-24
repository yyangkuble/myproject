package com.app.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.BaseJunitTest;
import www.springmvcplus.com.util.MyJSON;
public class TestService extends BaseJunitTest {
	@Resource
	MyService myService;
	@Test
	public void name() {
		//明天测试Oracle和db2
		String sql="select * from(select * from test11) a where id=? and zifu=? and xiaoshu=? and riqi=? and datetest=? and doubletest=? and floattest=?";
		List<Object> list=new ArrayList<Object>();
		list.add("1005");
		list.add("省道");
		list.add("13.56");
		list.add("2017-04-04 00:27:14");
		list.add("2017-04-04");
		list.add("1235.2312");
		list.add("14.35");
		Map<String, Object> map=myService.getMap(sql, list.toArray());
		System.out.println(MyJSON.toJSONString(map));
	}

}
