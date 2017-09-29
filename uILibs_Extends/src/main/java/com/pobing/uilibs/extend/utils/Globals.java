package com.pobing.uilibs.extend.utils;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Globals {

    private static Application sApplication;
    private static ClassLoader sClassLoader;

    public static synchronized Application getApplication() {
    	
    	if(sApplication == null)
    		sApplication = getSystemApp();

    	return sApplication;
    }

    public static synchronized ClassLoader getClassLoader() {
        if (sClassLoader == null) {
            Application app = getApplication();
            return app.getClassLoader();
        }

        return sClassLoader;
    }

    private static Application getSystemApp(){
    	try {
	    	Class<?> activitythread = Class.forName("android.app.ActivityThread");
	    	
	    	Method m_currentActivityThread = activitythread.getDeclaredMethod("currentActivityThread");
	    	Field f_mInitialApplication = activitythread.getDeclaredField("mInitialApplication");
	    	f_mInitialApplication.setAccessible(true);
	    	Object current = m_currentActivityThread.invoke(null);
	    	
	    	Object app = f_mInitialApplication.get(current);
	    	
	    	return (Application)app;
	    	
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    

    private static int getVersionCode(){
        String packageName =  Globals.getApplication().getPackageName();
        int versionCode = 0;
        try {
            versionCode = Globals.getApplication().getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

}
