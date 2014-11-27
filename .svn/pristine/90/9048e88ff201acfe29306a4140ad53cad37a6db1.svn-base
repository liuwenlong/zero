package com.mapgoo.snowleopard.utils;

import android.content.Context;

import com.mapgoo.snowleopard.MGApp;

public class UserPref {
	private static Context mContext;

	private final static String PREF_FILE_NAME = "user";

	private final String USER_TOKEN = "userToken";
	private final String USER_TYPE = "usertype";
	private final String USER_ID = "USERID";
	private final String CUR_USER = "curUser";
	private final String USERS = "users";
	private final String USER_OBJECTID = "mUserObjectId";

	// 用户信息 s------------------------------------------------
	private final String USER_ALIAS = "userAlias";
	private final String USER_LEVEL = "userLevel";
	private final String USER_AVATER_URL = "userAvaterUrl";
	// 用户信息 e------------------------------------------------

	// TODO 待修改字段-----------------------------------------------
	private final String USERNAME_AND_PASSWORD = "USERANDPWD";
	private final String USER_HOLDID = "USERHOLDID";
	private final String USER_BINDID = "USERBINDID";
	// -----------------------------------------------------------------

	// 是否允许用户自动登陆， 登陆界面中，点击了记住密码便记为允许自动登陆
	private final String ALLOW_AUTO_LOGIN = "allowAutoLogin";

	private UserPref() {
	}

	private static UserPref mInstance = null;
	private static PrefUtils mPrefUtils;

	public static UserPref getInstance() {
		// 全局读写SharedPreference的Context
		mContext = MGApp.pThis;
		mPrefUtils = new PrefUtils(mContext, PREF_FILE_NAME);

		if (mInstance == null) {
			synchronized (UserPref.class) {
				if (mInstance == null)
					mInstance = new UserPref();
			}
		}

		return mInstance;
	}

	// -----------------写字段必须要调用的方法----------------
	// 开始事务
	public UserPref beginTransaction() {
		mPrefUtils.beginTransaction();

		return this;
	}

	// 提交
	public void commit() {
		mPrefUtils.commit();
	}

	// -------------------------------------------------------

	public String getUserObjectid() {
		return mPrefUtils.getPrefString(USER_OBJECTID, "");
	}

	public UserPref setUserObjectid(String userObjectId) {
		mPrefUtils.setPrefString(USER_OBJECTID, userObjectId);

		return this;
	}

	public String getUserType() {
		return mPrefUtils.getPrefString(USER_TYPE, "1"); // 默认为账户登陆
	}

	public UserPref setUserType(String userType) {
		mPrefUtils.setPrefString(USER_TYPE, userType);

		return this;
	}

	public String getUserID() {
		return mPrefUtils.getPrefString(USER_ID, "");
	}

	public UserPref setUserId(String userId) {
		mPrefUtils.setPrefString(USER_ID, userId);

		return this;
	}

	public String getUserToken() {
		return mPrefUtils.getPrefString(USER_TOKEN, "");
	}

	public UserPref setUserToken(String userToken) {
		mPrefUtils.setPrefString(USER_TOKEN, userToken);

		return this;
	}

	public String getUserHoldId() {
		return mPrefUtils.getPrefString(USER_HOLDID, "");
	}

	public UserPref setUserHoldId(String userHoldId) {
		mPrefUtils.setPrefString(USER_HOLDID, userHoldId);

		return this;
	}

	public String getUserBindId() {
		return mPrefUtils.getPrefString(USER_BINDID, "");
	}

	public UserPref setUserBindId(String userBindId) {
		mPrefUtils.setPrefString(USER_BINDID, userBindId);

		return this;
	}

	// FIXME 后期考虑去除该方法，因为前面已经存有用户名和密码了
	public String getUserNameAndPassword() {
		return mPrefUtils.getPrefString(USERNAME_AND_PASSWORD, "");
	}

	public UserPref setUserNameAndPassword(String userNameAndPassword) {
		mPrefUtils.setPrefString(USERNAME_AND_PASSWORD, userNameAndPassword);

		return this;
	}

	// FIXME 后期考虑用其他的记住用户上次所选设备的方式
	public String getUserLastObjectId(String userIdKey) {

		return mPrefUtils.getPrefString(userIdKey + "_USEROBJECTID", "");
	}

	public UserPref setUserLastObjectId(String userIdKey, String lastObjectId) {
		mPrefUtils.setPrefString(userIdKey + "_USEROBJECTID", lastObjectId);

		return this;
	}

	public Boolean isAllowAutoLogin() {
		return mPrefUtils.getPrefBoolean(ALLOW_AUTO_LOGIN, false);
	}

	public UserPref setAllowAutoLogin(Boolean isAllowAutoLogin) {
		mPrefUtils.setPrefBoolean(ALLOW_AUTO_LOGIN, isAllowAutoLogin);

		return this;
	}

	public String getUserJpushInfo(String userIdKey) {
		return mPrefUtils.getPrefString(userIdKey, "");
	}

	public UserPref setUserJpushInfo(String userIdKey, String userJpushInfo) {
		mPrefUtils.setPrefString(userIdKey, userJpushInfo);

		return this;
	}

	public String getUserAvaterURL() {
		return mPrefUtils.getPrefString(USER_AVATER_URL, "");
	}

	public UserPref setUserAvaterURL(String newURL) {
		mPrefUtils.setPrefString(USER_AVATER_URL, newURL);

		return this;
	}

	public String getUserAlias() {
		return mPrefUtils.getPrefString(USER_ALIAS, "");
	}

	public UserPref setUserAlias(String newAlias) {
		mPrefUtils.setPrefString(USER_ALIAS, newAlias);

		return this;
	}

	public String getUserLevel() {
		return mPrefUtils.getPrefString(USER_LEVEL, "");
	}

	public UserPref setUserLevel(String newLevel) {
		mPrefUtils.setPrefString(USER_LEVEL, newLevel);

		return this;
	}

}