/**
 * 
 */
package com.itboye.pondteam.volley;


/**
 * 基础网络数据获取仓库
 * 
 * @author w
 * 
 */
public class BaseNetRepository extends BaseRepository {

	private ICompleteListener listener;
	public static String SUCCESS = "0";

	protected static String tag = "itboye";

	public BaseNetRepository() {

	}

	public BaseNetRepository(ICompleteListener listener) {
		this.setListener(listener);
	}

	/**
	 * @return the lisenter
	 */
	public ICompleteListener getListener() {
		return listener;
	}

	/**
	 * @param lisenter
	 *            the lisenter to set
	 */
	public void setListener(ICompleteListener listener) {
		this.listener = listener;
	}

}
