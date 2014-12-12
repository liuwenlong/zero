package com.mapgoo.zero.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceUtil {

	public static String getDeviceModel() {
		String str = Build.MODEL;
		if (str == null)
			str = "";
		return str;
	}

	public static String getIMEI(Context context) {
		return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
	}

	public static String getIMSI(Context paramContext) {
		return ((TelephonyManager) paramContext.getSystemService("phone")).getSubscriberId();
	}

	public static String getMobileUUID(Context paramContext) {
		String str1 = "";
		WifiManager wifiManager = (WifiManager) paramContext.getSystemService("wifi");
		if (wifiManager != null) {
			WifiInfo localWifiInfo = wifiManager.getConnectionInfo();
			if ((localWifiInfo != null) && (localWifiInfo.getMacAddress() != null))
				str1 = localWifiInfo.getMacAddress().replace(":", "");
		}
		String str2 = ((TelephonyManager) paramContext.getSystemService("phone")).getDeviceId();
		String str3 = str1 + str2;
		Object localObject = "";
		try {
			LineNumberReader localLineNumberReader = new LineNumberReader(new InputStreamReader(new ProcessBuilder(new String[] {
					"/system/bin/cat", "/proc/cpuinfo" }).start().getInputStream()));
			for (int i = 1;; i++) {
				if (i < 100) {
					String str5 = localLineNumberReader.readLine();
					if (str5 != null) {
						if (str5.indexOf("Serial") <= -1)
							continue;
						String str6 = str5.substring(1 + str5.indexOf(":"), str5.length()).trim();
						localObject = str6;
					}
				}
				String str4 = str3 + (String) localObject;
				if ((str4 != null) && (str4.length() > 64))
					str4 = str4.substring(0, 64);
				return str4;
			}
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}

	public static int getSDKVersionInt() {
		return Build.VERSION.SDK_INT;
	}

	public static int[] getScreenSize(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		return getScreenSize(displayMetrics);
	}
	
	
	public static int[] getScreenSize(DisplayMetrics displayMetrics) {
		int[] screenSizeArr = new int[2];
		screenSizeArr[0] = displayMetrics.widthPixels;
		screenSizeArr[1] = displayMetrics.heightPixels;
		return screenSizeArr;
	}
	
	public static int getScreenWidth(Context context) {
		return getScreenSize(context)[0];
	}
	

	@SuppressLint("DefaultLocale")
	public static boolean isARMCPU() {
		String str = Build.CPU_ABI;
		return (str != null) && (str.toLowerCase().contains("arm"));
	}

	public static boolean isAppInstalled(Context context, String packageName) {
		if (context == null)
			return false;
		
		try {
			context.getPackageManager().getPackageInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException nameNotFoundException) {
			return false;
		}

	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		if ((context == null) || (intent == null))
			return false;

		PackageManager packageManager = context.getPackageManager();

		if ((packageManager == null) || packageManager.queryIntentActivities(intent, 65536).size() <= 0)
			return false;

		return true;
	}

	public static boolean isIntentAvailable(Context context, String intentAction) {
		return isIntentAvailable(context, new Intent(intentAction));
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
		if (connectivityManager == null)
			return false;
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null) && (networkInfo.isAvailable());
	}

	public static boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals("mounted");
	}
}