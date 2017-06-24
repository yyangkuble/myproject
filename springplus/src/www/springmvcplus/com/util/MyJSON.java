package www.springmvcplus.com.util;

import com.alibaba.fastjson.serializer.SerializerFeature;

public class MyJSON {
	public static String toJSONString(Object object) {
		
		return com.alibaba.fastjson.JSON.toJSONString(object);
	}
	
	public static String toJSONStringPrettyFormat(Object object) {
		
		return com.alibaba.fastjson.JSON.toJSONString(object, SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat);
	}
}
