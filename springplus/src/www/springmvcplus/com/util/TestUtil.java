package www.springmvcplus.com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;

public class TestUtil {
	public static String baseUrlG="http://39.108.95.173/";
	public static String baseUrl="http://127.0.0.1:8080/springplus/";
	public static String aesTest(String url,Map<String, String> map) {
		try {
			String aesString = AESUtil.aesEncryptString(MyJSON.toJSONString(map), "16BytesLengthKey");
			Connection connect = Jsoup.connect(baseUrl+url);
			String text = connect.data("p", aesString).post().text();
			return MyJSON.toJSONStringPrettyFormat(JSON.parse(text));
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "";
	}
}
