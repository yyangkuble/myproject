package com.app.project;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import www.springmvcplus.com.common.SqlResult;
import www.springmvcplus.com.common.XmlSqlParse;
import www.springmvcplus.com.util.BaseJunitTest;
import www.springmvcplus.com.util.SqlMake;
import www.springmvcplus.com.util.TestUtil;

public class StringTest extends BaseJunitTest {
	@org.junit.Test
	public void test() throws Exception {
		XmlSqlParse.initScriptEngine();
		Map<String, String> map=new HashMap<>();
		map.put("customType", "营销");
		SqlResult makeSql = SqlMake.makeSql(map, "selectCoustom");
		System.out.println(makeSql);
	}
	
}
