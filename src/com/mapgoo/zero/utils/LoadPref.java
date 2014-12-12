package com.mapgoo.zero.utils;

import android.content.Context;

import com.mapgoo.zero.MGApp;

/**
 * 概述: 加载/第一次加载的pref管理类
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月13日
 */
public class LoadPref {
	private static Context mContext;

	private final static String PREF_FILE_NAME = "load";

	private final String ISFIRSTLOAD = "isFirstLoad";
	private final String VERSION_NAME = "versionName";
	
	private final String LAST_LOGIN_USER_ID = "lastLoginUserId";
	private final String CUR_OBJECT_ID = "curObjectId";

	private LoadPref() {

	}

	private static LoadPref mInstance = null;

	private static PrefUtils mPrefUtils = null;

	// 单例、全局配置
	public static LoadPref getInstance(Context context) {
		mContext = MGApp.pThis;
		mPrefUtils = new PrefUtils(mContext, PREF_FILE_NAME);

		if (mInstance == null) { // 只有等未创建过该实例时才使用同步的方法来构造对象， 创建了以后就没必要了

			synchronized (LoadPref.class) {
				if (mInstance == null)
					mInstance = new LoadPref();
			}
		}

		return mInstance;
	}

	// -----------------写字段必须要调用的方法----------------
	// 开始事物
	public LoadPref beginTransaction() {
		mPrefUtils.beginTransaction();

		return this;
	}

	// 提交
	public LoadPref commit() {
		mPrefUtils.commit();

		return this;
	}

	// -------------------------------------------------------

	public boolean isFristLoad() {

		return mPrefUtils.getPrefBoolean(ISFIRSTLOAD, true);

	}

	public LoadPref setFirstLoad(Boolean isFristLoad) {

		mPrefUtils.setPrefBoolean(ISFIRSTLOAD, isFristLoad);

		return this;
	}

	public String getVersionName() {

		return mPrefUtils.getPrefString(VERSION_NAME, "1.0.0");
	}

	public LoadPref setVersionName(String versionName) {

		mPrefUtils.setPrefString(VERSION_NAME, versionName);

		return this;
	}

	/**
	 * 概述: 获取当前已登录的用户id
	 *
	 * @auther yao
	 * @return
	 */
	public String getLastLoginUserId() {

		return mPrefUtils.getPrefString(LAST_LOGIN_USER_ID, "");
	}

	/**
	 * 概述: 设置/记住 当前的登录的用户id
	 *
	 * @auther yao
	 * @param userId
	 * @return
	 */
	public LoadPref setLastLoginUserId(String userId) {

		mPrefUtils.setPrefString(LAST_LOGIN_USER_ID, userId);

		return this;
	}
	
	/**
	 * 概述: 获取当前已选的设备id
	 *
	 * @auther yao
	 * @return
	 */
	public String getCurObjectId() {
		return mPrefUtils.getPrefString(CUR_OBJECT_ID, "");
	}
	
	/**
	 * 概述: 设置当前选中的设备id
	 *
	 * @auther yao
	 * @param objectId
	 * @return
	 */
	public LoadPref setCurObjectId(String objectId) {
		mPrefUtils.setPrefString(CUR_OBJECT_ID, objectId);

		return this;
	}

}
