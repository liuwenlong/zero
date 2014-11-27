package com.mapgoo.snowleopard.bean;

import java.io.Serializable;
import java.sql.SQLException;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mapgoo.snowleopard.utils.DatabaseHelper;

/**
 * 概述: 用户模型, 用户GSON实体类转换、ORM的使用
 * 
 * @orignalAuthor yao
 * @createTime 2014年11月6日 下午6:44:26
 * 
 * @improvedAuther yao
 * @modifyTime 2014年11月6日
 */
@DatabaseTable(tableName = "accounts")
public class User implements Serializable {

	private static final long serialVersionUID = 103983357602894002L;

	@DatabaseField
	private int userId;

	// this indexes the city field so queries on city
	// will go faster for large tables
	@DatabaseField(index = true, id = true)
	private String userMobile;

	@DatabaseField
	private String authToken;

	@DatabaseField
	private int loginCount;

	@DatabaseField
	// @DatabaseField(dataType = DataType.DATE_TIME)
	private String loginDate; // TIPS，注意时间的转换

	@DatabaseField(canBeNull = true)
	private String userPwd;

	public User() {
	}

	private static Dao<User, String> userDao = null;

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It
	 * will create it or just give the cached value.
	 */
	public static Dao<User, String> getDao(DatabaseHelper mDatabaseHelper) {

		if (mDatabaseHelper == null)
			return null;

		if (userDao == null) {
			try {
				userDao = mDatabaseHelper.getDao(User.class);
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(), "Can't userDao", e);
				throw new RuntimeException(e);
			}
		}

		return userDao;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}
