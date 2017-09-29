package com.itboye.pondteam.volley;

import android.util.Base64;

/**
 * 加密解密,对json数据进行
 */
public class DataEncryptionUtil {
	
	/**
	 * json字符串 -> 加密数据 (处理两次)
	 * @param jsonStr json字符串
	 * @return	加密字符串
	 */
	public static String encodeData(String jsonStr) {
		String Base64Str = new String(Base64.encode(jsonStr.getBytes(), Base64.DEFAULT));
		String str = new String(Base64.encode(Base64Str.getBytes(), Base64.DEFAULT));
		return str;
	}
	
	/**
	 * 加密数据  -> json字符串  (处理两次)
	 * @param str	加密字符串
	 * @return		json字符串
	 */
	public static String decodeData(String str){
		String Base64Str = new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
		String jsonStr = new String(Base64.decode(Base64Str.getBytes(), Base64.DEFAULT));
		return jsonStr;
	}
}




























