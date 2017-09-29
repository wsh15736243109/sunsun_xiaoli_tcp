package com.pobing.uilibs.extend.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SystemUtils {
	public static String getCpuInfo() {
		String str = null;
		FileReader localFileReader = null;
		BufferedReader localBufferedReader = null;
		try {
			localFileReader = new FileReader("/proc/cpuinfo");
			if (localFileReader != null)
				try {
					localBufferedReader = new BufferedReader(localFileReader,
							1024);
					str = localBufferedReader.readLine();
					localBufferedReader.close();
					localFileReader.close();
				} catch (IOException localIOException) {
					Log.e("Could not read from file /proc/cpuinfo",
							localIOException.toString());
				}
		} catch (FileNotFoundException localFileNotFoundException) {
			Log.e("BaseParameter-Could not open file /proc/cpuinfo",
					localFileNotFoundException.toString());
		}
		if (str != null) {
			int i = str.indexOf(':') + 1;
			str = str.substring(i);
			return str.trim();
		}
		return "";
	}

	public static int getSystemVersion() {
		int SDK_INT = 2;
		try {
			SDK_INT = android.os.Build.VERSION.class.getField("SDK_INT")
					.getInt(null);
		} catch (Exception e) {
			try {
				SDK_INT = Integer
						.parseInt((String) android.os.Build.VERSION.class
								.getField("SDK").get(null));
			} catch (Exception e2) {
				SDK_INT = 2;
				e2.printStackTrace();
			}
		}
		return SDK_INT;
	}

	public static boolean isServiceRunning(Context context, String serviceName) {
		if (context != null) {
			ActivityManager manager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			if (manager != null) {
				List<RunningServiceInfo> infos = manager.getRunningServices(30);
				for (RunningServiceInfo info : infos) {
					if (info.service.getClassName().contains(serviceName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isEmulator(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			String imei = tm.getDeviceId();
			if (imei == null || imei.equals("000000000000000")) {
				return true;
			}
		}
		return false;
	}

	public static int getScreenWidth(Context context) {
		if (context != null) {
			DisplayMetrics dm = new DisplayMetrics();
			if (dm != null) {
				WindowManager wm = (WindowManager) context
						.getSystemService("window");
				wm.getDefaultDisplay().getMetrics(dm);
				return dm.widthPixels;
			}
		}
		return 0;
	}

	public static int getScreenHeight(Context context) {
		if (context != null) {
			DisplayMetrics dm = new DisplayMetrics();
			if (dm != null) {
				WindowManager wm = (WindowManager) context
						.getSystemService("window");
				wm.getDefaultDisplay().getMetrics(dm);
				return dm.heightPixels;
			}
		}
		return 0;
	}

}
