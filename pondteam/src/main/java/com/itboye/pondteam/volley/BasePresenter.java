/**
 * 
 */
package com.itboye.pondteam.volley;

import java.util.Observable;
import java.util.Observer;

/**
 * @author w
 *
 */
public abstract class BasePresenter extends Observable {

	public static final String Tag_Error = "error";
	public static final String Tag_Success = "success";
	
	public BasePresenter(Observer observer){
		this.addObserver(observer);
	}
}
