package com.mapgoo.snowleopard.utils;

import android.content.Context;

import com.mapgoo.snowleopard.MGApp;

/**
 * 概述: 本地安全密码/手势密码的pref管理类, 存的手势密码，经过md5加密
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月13日
 */
public class LockPatternPref {
	private static Context mContext;

	private final static String PREF_FILE_NAME = "lock_pattern";

	private final String LOCK_PATTERN_VAL = "lock_pattern_md5_val";

	private LockPatternPref() {

	}

	private static LockPatternPref mInstance = null;

	private static PrefUtils mPrefUtils = null;

	// 单例、全局配置
	public static LockPatternPref getInstance(Context context) {
		mContext = MGApp.pThis;
		mPrefUtils = new PrefUtils(mContext, PREF_FILE_NAME);

		if (mInstance == null) { // 只有等未创建过该实例时才使用同步的方法来构造对象， 创建了以后就没必要了

			synchronized (LockPatternPref.class) {
				if (mInstance == null)
					mInstance = new LockPatternPref();
			}
		}

		return mInstance;
	}

	// -----------------写字段必须要调用的方法----------------
	// 开始事物
	public LockPatternPref beginTransaction() {
		mPrefUtils.beginTransaction();

		return this;
	}

	// 提交
	public LockPatternPref commit() {
		mPrefUtils.commit();

		return this;
	}

	public String getLockPattern() {
		return mPrefUtils.getPrefString(LOCK_PATTERN_VAL, "");
	}

	public LockPatternPref setLockPattern(String newLockPatternMD5Val) {
		mPrefUtils.setPrefString(LOCK_PATTERN_VAL, newLockPatternMD5Val);
		return this;
	}

}
