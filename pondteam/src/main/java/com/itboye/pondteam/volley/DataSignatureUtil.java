package com.itboye.pondteam.volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 数字签名
 */
public class DataSignatureUtil {

	/**
	 * MD5加密
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String sourceStr) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(sourceStr.getBytes()); 
		byte b[] = md.digest(); 
		int i; 
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) 
				i+= 256; 
			if(i<16) 
				buf.append("0"); 
			buf.append(Integer.toHexString(i));
		} 
		String result = buf.toString();
		System.out.println("result: " + result);//32位的加密
		//   System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 
		return result;
	}
}
