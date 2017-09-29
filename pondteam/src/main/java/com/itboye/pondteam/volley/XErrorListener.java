package com.itboye.pondteam.volley;


/**
 * 
 * @author w
 *
 * @param @请求错误，异常，失败等时
 */
public interface XErrorListener{
	/**
	 * 
	 * @param exception 不为null 
	 * @param code ， code 可能是默认值（0），
	 * @param msg msg可能是默认值 （null）
	 */
	void onErrorResponse(Exception exception, String code, String msg);
}
