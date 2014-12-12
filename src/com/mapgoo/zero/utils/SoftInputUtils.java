package com.mapgoo.zero.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 概述： 输入法键盘操作工具类
 * 
 * @author yao
 */
public class SoftInputUtils {

	private static InputMethodManager mInputMethodManager;

	private static InputMethodManager getInputMethodManager(Context context) {

		if (mInputMethodManager == null) {
			synchronized (SoftInputUtils.class) {
				if (mInputMethodManager == null)
					mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			}
		}

		return mInputMethodManager;
	}

	/**
	 * 概述: 隐藏输入法键盘
	 * 
	 * @auther yao
	 * @param context
	 */
	public static void hideSoftInput(Context context) {

		if (mInputMethodManager == null)
			mInputMethodManager = getInputMethodManager(context);

		if (mInputMethodManager != null && ((Activity) context).getCurrentFocus() != null
				&& ((Activity) context).getCurrentFocus().getWindowToken() != null) {

			View v = ((Activity) context).getCurrentFocus();
			if (v == null) {
				return;
			}

			mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 概述: 拉起输入法键盘
	 * 
	 * @auther yao
	 * @param context
	 */
	public static void showSoftInput(Context context) {

		if (mInputMethodManager == null)
			mInputMethodManager = getInputMethodManager(context);

		mInputMethodManager.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 概述: 获取焦点, 
	 * 
	 * @auther yao
	 * @param context
	 * @param editText
	 */
	public static void requestFocus(Context context, EditText editText) {
		editText.requestFocus();
		editText.setSelection(editText.getText().length());

		showSoftInput(context);
	}

}
