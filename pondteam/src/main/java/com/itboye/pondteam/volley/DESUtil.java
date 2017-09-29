package com.itboye.pondteam.volley;



import android.annotation.SuppressLint;
import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * @author w
 * @version 2016-9-24 
 */
public class DESUtil {
	
	
	/**
	 * DES
	 * @param key	
	 * @return	
	 */
	@SuppressLint("TrulyRandom")
	public static String encode(String content, String key) {
		try{
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密
			//正式执行加密操作
			byte[] finalBytes = cipher.doFinal(content.getBytes("UTF-8"));
			
			return Base64.encodeToString(finalBytes, Base64.DEFAULT);
			
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		return null;

	}

	/**
	 * DES
	 * 
	 * @param data base64 加密后的密文
	 *            
	 * @param key
	 *           
	 * @return 
	 */
	public static String decode(String data, String key) {
		if (data == null)
			return null;
		try {
			byte[] originEncodeBytes = Base64.decode(data, Base64.DEFAULT);
			// DES算法要求有一个可信任的随机数源
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// 真正开始解密操作
			byte[] origin = cipher.doFinal(originEncodeBytes);
			return new String(origin,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}