package com.pobing.uilibs.extend.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {
	/**
	 * 获取类方法
	 * @param invokeClass	类class
	 * @param invokeMethod	类方法名
	 * @param paramClasses	类方法参数
	 * @return				类方法Mehod
	 */
	public static Method getMethod(Class<?> invokeClass,String invokeMethod,Class<?>... paramClasses){
		try {
			Method method = invokeClass.getDeclaredMethod(invokeMethod,paramClasses);
			method.setAccessible(true);
			return method;
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 调用类实例方法
	 * @param invokeMethod		调用的方法
	 * @param invokeInstance	被调用实例
	 * @param params			调用传入参数
	 * @return					调用结果
	 */
	public static Object invokeMethod(Method invokeMethod,Object invokeInstance,Object... params){
		try {
			invokeMethod.setAccessible(true);
			return invokeMethod.invoke(invokeInstance,params);
		}
		catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取实例中某个字段变量
	 * @param dataClass		类class
	 * @param dataInstance	类实例
	 * @param fieldName		获取数据的字段名
	 * @return				字段值
	 */
	public static Object fieldGet(Class<?> dataClass,Object dataInstance,String fieldName){
		try {
			Field field = dataClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(dataInstance);
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取实例中某个字段变量
	 * @param dataField		字段field
	 * @param dataInstance	类实例
	 * @return				字段值
	 */
	public static Object fieldGet(Field dataField,Object dataInstance){
		try {
			dataField.setAccessible(true);
			return dataField.get(dataInstance);
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取实例中某个字段变量
	 * @param dataField		字段field
	 * @param dataInstance	类实例
	 * @return				字段值
	 */
	public static boolean fieldSet(Field dataField,Object dataInstance,Object fieldValue){
		try {
			dataField.setAccessible(true);
			dataField.set(dataInstance,fieldValue);
			return true;
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 设置实例中某个字段变量值
	 * @param dataClass		类class
	 * @param dataInstance	类实例
	 * @param fieldName		设置数据的字段名
	 * @param fieldValue	设置数据的字段值
	 * @return				设置是否成功
	 */
	public static boolean fieldSet(Class<?> dataClass,Object dataInstance,String fieldName,Object fieldValue){
		try {
			Field field = dataClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(dataInstance, fieldValue);
			return true;
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取class的字段
	 * @param dataClass		类class
	 * @param fieldName		获取数据的字段名
	 * @return	字段Field 获取失败则为null。获取完成后自动将该字段设为可访问
	 */
	public static Field getField(Class<?> dataClass,String fieldName){
		try {
			Field field = dataClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		}
		catch (SecurityException e) {
			
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	
}
