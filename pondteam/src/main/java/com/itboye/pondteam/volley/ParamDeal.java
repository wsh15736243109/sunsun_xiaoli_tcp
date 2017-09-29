package com.itboye.pondteam.volley;

import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 数据传输前后的操作 加密解密,对map数据进行 数字签名 获取时间戳
 */
public class ParamDeal {
	public final String alg = "md5"; // 解码方式

	public ParamDeal() {

	}

	/**
	 * 生成 json 字符串 --> 数据加密
	 * 
	 * @param data_array
	 *            数值对
	 * @return 加密信息
	 */
	public String dataEncrypt(Map data_array) {
		// 生成 json 字符串
		Gson gson = new Gson();
		String jsonStr = gson.toJson(data_array);
		DebugLog.v("Json字符串", jsonStr);
		String data = DataEncryptionUtil.encodeData(jsonStr);
		return data;
	}

	/**
	 * 这里还没用到,用到了再接出去 数据解密 --> 生成map对象
	 * 
	 * @param data
	 *            加密的数据
	 * @param clz
	 *            传入的map的class
	 * @return map对象
	 */
	public Map dataDecrypt(String data, Class<? extends Map> clz) {
		String jsonStr = DataEncryptionUtil.decodeData(data);

		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clz);
	}

	/**
	 * @param time
	 *            时间戳
	 * @param type
	 *            文档的type BY_User_login
	 * @param data
	 *            json数据
	 * @param client_id
	 *            给管理员有的 by565fa4e56a9241
	 * @param notify_id
	 *            随意 111
	 * @return sign 签名
	 */
	@SuppressWarnings("finally")
	public String getMD5Sign(String time, String type, String data,
							 String client_id, String notify_id) {
		String sign = "";
		try {
			sign = DataSignatureUtil.getMD5(time + type + data + client_id
					+ notify_id);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			return sign;
		}
	}
}
