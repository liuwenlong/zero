package com.mapgoo.zero.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.mapgoo.zero.MGApp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 概述: 各种通用工具集合
 * 
 * @author yao
 * @ref yao
 * @modify yao
 * @createdTime 2014年8月4日
 * @modifyTime 2014年8月4日
 */
public class Utils {
	
	public static String getVersionName(Context context) {
		String pkName = context.getPackageName();
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return versionName;
	}

	public static String getString(int resId) {
		return MGApp.pThis.getString(resId);
	}

	private static String mToastString;

	private static Handler mShowToastHandler = new Handler() {
		public void handleMessage(Message msg) {
			Toast.makeText(MGApp.pThis, mToastString, Toast.LENGTH_SHORT).show();
		}
	};

	public static void showToast(String str) {
		mToastString = str;
		mShowToastHandler.sendEmptyMessage(0);
	}

	public static void showToast(int resId) {
		mToastString = MGApp.pThis.getString(resId);
		mShowToastHandler.sendEmptyMessage(0);
	}

	// hide soft input
	public static void hideSoftInput(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (inputMethodManager != null) {
			View v = ((Activity) context).getCurrentFocus();
			if (v == null) {
				return;
			}

			inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * Returns a certain percentage from and integer.
	 * 
	 * @param value
	 *            the full integer value.
	 * @param percent
	 *            the percentage to get from the value.
	 * @return the given percentage from the given value.
	 */
	public static int getPercent(int value, float percent) {
		return (int) (value * (percent / 100.0f));
	}

	/**
	 * Converts density pixels to normal pixels.
	 * 
	 * @param dp
	 *            the value in density pixels
	 * @param dm
	 * @return density pixel value in pixels.
	 */
	public static int getPixels(int dp, DisplayMetrics dm) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
	}

	/**
	 * 截取当前屏幕
	 * @param context
	 * @param decorview
	 * @return
	 */
	public static Bitmap screenShot(Activity context, View decorview) {

		// //1.构建Bitmap
		WindowManager windowManager = context.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();
		Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		// // 2.获取屏幕
		// View decorview = context.getWindow().getDecorView();
		// View decorview = context.getWindow().getDecorView();
		decorview.setDrawingCacheEnabled(true);
		Bmp = decorview.getDrawingCache();

		return Bmp;
	}
	
	/**
	 * 获取现在的时间连续字符串
	 * @return
	 */
	public static String getNow() {
		String formatedDate = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
		
		formatedDate = sdf.format(new Date());

		return formatedDate;
	}

}
