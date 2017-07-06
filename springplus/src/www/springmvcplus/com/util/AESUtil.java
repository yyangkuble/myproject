package www.springmvcplus.com.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class AESUtil {
	
	private static final String IV_STRING = "A-16-Byte-String";
	private static final String charset = "UTF-8";
	 
	public static String aesEncryptString(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] contentBytes = content.getBytes(charset);
		byte[] keyBytes = key.getBytes(charset);
		byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
	    return Base64.encodeBase64String(encryptedBytes);
	}

	public static String aesDecryptString(String content, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		
	    byte[] encryptedBytes = Base64.decodeBase64(content);
	    byte[] keyBytes = key.getBytes(charset);
		byte[] decryptedBytes = aesDecryptBytes(encryptedBytes, keyBytes);
	    return new String(decryptedBytes, charset);		
	}
	
	public static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
	    return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
	}
	
	public static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
	    return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
	}
	
	private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
		
	    byte[] initParam = IV_STRING.getBytes(charset);
	    IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(mode, secretKey, ivParameterSpec);

 	 	return cipher.doFinal(contentBytes);
	}
	
	public static Map<String, String> converParameter(HttpServletRequest request) {
		String param=request.getParameter("p");
		if (StringUtil.hashText(param)) {
			try {
				if (request.getRequestURI().endsWith("base/personnelPool")) {
					System.out.println("d");
				}
				param=AESUtil.aesDecryptString(param, "16BytesLengthKey");
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException
					| InvalidAlgorithmParameterException
					| IllegalBlockSizeException | BadPaddingException
					| UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map parseObject = JSON.parseObject(param, Map.class);
			Map<String, String> map=new HashMap<>();
			for (Object objectkey : parseObject.keySet()) {
				map.put(String.valueOf(objectkey), String.valueOf(parseObject.get(objectkey)));
			}
			return map;
		}else{
			return new HashMap<String, String>();
		}
	}
	public static <T> T converEntity(HttpServletRequest request,Class<T> c) {
		String param=request.getParameter("p");
		if (StringUtil.hashText(param)) {
			try {
				param=AESUtil.aesDecryptString(param, "16BytesLengthKey");
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException
					| InvalidAlgorithmParameterException
					| IllegalBlockSizeException | BadPaddingException
					| UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			T t=JSON.parseObject(param, c);
			return t;
		}else{
			return null;
		}
	}
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String string = AESUtil.aesEncryptString("你好 how", "16BytesLengthKey");
		System.out.println(string);
		System.out.println(AESUtil.aesDecryptString(string, "16BytesLengthKey"));
	}
	
}