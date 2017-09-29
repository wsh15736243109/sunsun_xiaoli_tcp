/**
 * 
 */
package com.itboye.pondteam.volley;

/**
 * @author w
 *
 */
public interface ICompleteListener {
	
//	void complete(ResultEntity result);
	void success(ResultEntity result);
	void failure(ResultEntity result);
}
