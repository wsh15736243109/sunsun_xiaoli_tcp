package com.itboye.pondteam.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences封装类
 * 
 * 所有的commit操作使用了SharedPreferencesCompat.apply进行了替代;尽可能异步操作
 */
public class SPUtils {
	
	public SPUtils() {
		/**
		 * 实例化失败
		 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String DEFAULT_FILE_NAME = "htb_by_data";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context	上下文
	 * @param file_name		文件名，null 或 ""  为默认：share_by_data
	 * @param key		关键字
	 * @param object	数据
	 */
	public static void put(Context context, String file_name, String key, Object object){
		if (null == file_name || "".equals(file_name)) {
			file_name = DEFAULT_FILE_NAME;
		}
		SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		if (object instanceof String) { //instanceof 用来 指出对象是否是特定类的一个实例
			editor.putString(key, (String) object);
		} else if (object instanceof Integer){
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean){
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float){
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long){
			editor.putLong(key, (Long) object);
		} else{
			editor.putString(key, object.toString());
		}
		
		SharedPreferencesCompat.apply(editor);
	}
	
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context		上下文
	 * @param file_name		文件名，null 或 ""  为默认：share_f21_data
	 * @param key			关键字
	 * @param defaultObject	默认值
	 * @return
	 */
	public static Object get(Context context, String file_name, String key, Object defaultObject){
		if (null == file_name || "".equals(file_name)) {
			file_name = DEFAULT_FILE_NAME;
		}
		SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
		if (defaultObject instanceof String){
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer){
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean){
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float){
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long){
			return sp.getLong(key, (Long) defaultObject);
		}
		return null;
	}
	
	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context	上下文
	 * @param file_name		文件名，null 或 ""  为默认：share_f21_data
	 * @param key		关键字
	 */
	public static void remove(Context context, String file_name, String key){
		if (null == file_name || "".equals(file_name)) {
			file_name = DEFAULT_FILE_NAME;
		}
		SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}
	
	/**
	 * 清除所有数据
	 * @param context	上下文
	 * @param file_name		文件名，null 或 ""  为默认：share_f21_data
	 */
	public static void clear(Context context, String file_name){
		if (null == file_name || "".equals(file_name)) {
			file_name = DEFAULT_FILE_NAME;
		}
		SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}
	
	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配
	 */
	private static class SharedPreferencesCompat{
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod(){
			try{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e){
				
			}
			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor){
			try{
				if (sApplyMethod != null){
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e){
				
			} catch (IllegalAccessException e){
				
			} catch (InvocationTargetException e){
				
			}
			editor.commit();
		}
	}
}












