package com.app.project.util;

import io.rong.RongCloud;
import io.rong.models.TokenResult;

import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;

import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.StringUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PublicUtil {
	private static String getFirstSpell(String chinese) {   
        StringBuffer pybf = new StringBuffer();   
        char[] arr = chinese.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < arr.length; i++) {   
                if (arr[i] > 128) {   
                        try {   
                                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
                                if (temp != null) {   
                                        pybf.append(temp[0].charAt(0));   
                                }   
                        } catch (BadHanyuPinyinOutputFormatCombination e) {   
                                e.printStackTrace();   
                        }   
                } else {   
                        pybf.append(arr[i]);   
                }   
        }   
        return pybf.toString().replaceAll("\\W", "").trim();   
	}  
	//获取首字母
	public static String getShouzimu(String name) {
		if (StringUtil.hashText(name)) {
			String pinyin=getFirstSpell(name);
			if (StringUtil.hashText(pinyin)) {
				return StringUtil.valueOf(pinyin.charAt(0));
			}
		}
		return null;
	}
	
	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };  
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };  
	  
	//获取星座
	public static String getConstellation(String birthday) {
		Date date = DateUtil.dateFormat(birthday, "yyyy-MM-dd");
		int month=date.getMonth()+1;
		System.out.println(month);
		int day=date.getDate();
		System.out.println(day);
	    return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];  
	} 
	   //由出生日期获得年龄  
    public static int getAge(String birthdayStr) {  
    	Date birthDay = DateUtil.dateFormat(birthdayStr, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    } 
    
    public static TokenResult getRongyunToken(String userid,String username,String imgurl) {
    	String appKey = "p5tvi9dsp4yl4";//替换成您的appkey
		String appSecret = "Zc5OpGWHDH0";//替换成匹配上面key的secret
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
		TokenResult userGetTokenResult;
		try {
			userGetTokenResult = rongCloud.user.getToken(userid, StringUtil.valueOf(username), StringUtil.valueOf(imgurl));
			return userGetTokenResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getAge("1989-12-19"));
	}
}
