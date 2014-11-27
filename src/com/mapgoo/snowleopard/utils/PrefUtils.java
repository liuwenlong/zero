package com.mapgoo.snowleopard.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 概述: SharedPreference操作工具包
 * 
 * @author yao
 * @ref yao
 * @modify yao
 * @createdTime 2014年7月22日
 * @modifyTime 2014年7月22日
 */
public class PrefUtils {

	private SharedPreferences mPreferences;

	private Editor mEditor;

	public PrefUtils(Context context, String preferenceFileName) {
		mPreferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
	}

	// -----------------写字段必须要调用的方法----------------
	// 开始事物
	@SuppressLint("CommitPrefEdits")
	public void beginTransaction() {
		mEditor = mPreferences.edit();
	}

	// 提交
	public void commit() {
		mEditor.commit();
	}

	// -------------------------------------------------------

	public String getPrefString(final String key, final String defaultValue) {

		return mPreferences.getString(key, defaultValue);
	}

	public void setPrefString(final String key, final String value) {

		mEditor.putString(key, value);
	}

	public boolean getPrefBoolean(final String key, final boolean defaultValue) {

		return mPreferences.getBoolean(key, defaultValue);
	}

	public boolean hasKey(final String key) {
		return mPreferences.contains(key);
	}

	public void setPrefBoolean(final String key, final boolean value) {

		mEditor.putBoolean(key, value);
	}

	public void setPrefInt(final String key, final int value) {

		mEditor.putInt(key, value);
	}

	public int getPrefInt(final String key, final int defaultValue) {

		return mPreferences.getInt(key, defaultValue);
	}

	public void setPrefFloat(final String key, final float value) {

		mEditor.putFloat(key, value);
	}

	public float getPrefFloat(final String key, final float defaultValue) {

		return mPreferences.getFloat(key, defaultValue);
	}

	public void setSettingLong(final String key, final long value) {

		mEditor.putLong(key, value);
	}

	public long getPrefLong(final String key, final long defaultValue) {

		return mPreferences.getLong(key, defaultValue);
	}

	public void clear() {

		mEditor.clear();
	}

}
