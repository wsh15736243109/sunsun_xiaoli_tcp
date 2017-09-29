package com.itboye.pondteam.volley;

import android.util.Log;

/**
 * 测试用Log，发布后不会打印任何内容
 * @author Young
 *
 */
public class DebugLog {

//	public static void v(String k,Object v){
//		if (Debug.isDebug==false) {
//			return;
//		}
//		if (v!=null) {
//			Log.v(k, v.toString());
//		}else{
//			Log.v(k, "null");
//		}
//		
//	}
	/**
	 * @param k
	 * @param v
	 */
	public static void v(String k, Object...v){
		if (Debug.isDebug==false) {
			return;
		}
		if (v!=null) {
			StringBuilder sb=new StringBuilder();
			for (Object object : v) {
				if (object!=null) {
					sb.append(object.toString());
				}else{
					sb.append("null");
				}
				
			}
			Log.v(k, sb.toString());
		}else{
			Log.v(k, "null");
		}
//		if (v!=null) {
//			Log.v(k, v.toString());
//		}else{
//			Log.v(k, "null");
//		}
//		
	}

}
