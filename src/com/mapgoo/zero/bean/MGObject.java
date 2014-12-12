package com.mapgoo.zero.bean;

import java.io.Serializable;
import java.sql.SQLException;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mapgoo.zero.utils.DatabaseHelper;

/**
 * 概述: 设备信息-接口：获取账号下的设备列表
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月12日
 */
@DatabaseTable(tableName = "user_objects")
public class MGObject implements Serializable {

	private static final long serialVersionUID = -3534328592272177863L;

	@DatabaseField(index = true, id = true)
	private String objectId;

	@DatabaseField
	private String imei;

	@DatabaseField
	private String sim;
	
	@DatabaseField
	private String relationShip;

	@DatabaseField
	private boolean isManagement;
	
	@DatabaseField
	private String avatarImageURL;

	@DatabaseField
	private String nickName;

	// 所属的用户id
	@DatabaseField
	// 不用被反序列化
	private String ownerUID;
	
	private static Dao<MGObject, String> objectDao = null;

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It
	 * will create it or just give the cached value.
	 */
	public static Dao<MGObject, String> getDao(DatabaseHelper mDatabaseHelper) {

		if (mDatabaseHelper == null)
			return null;

		if (objectDao == null) {
			try {
				objectDao = mDatabaseHelper.getDao(MGObject.class);
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(), "Can't get dao", e);
				throw new RuntimeException(e);
			}
		}

		return objectDao;
	}

	public MGObject() { // for ormlite
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public boolean getIsManagement() {
		return isManagement;
	}

	public void setIsManagement(boolean isManagement) {
		this.isManagement = isManagement;
	}

	public String getAvatarImageURL() {
		return avatarImageURL;
	}

	public void setAvatarImageURL(String avatarImageURL) {
		this.avatarImageURL = avatarImageURL;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOwnerUID() {
		return ownerUID;
	}

	public void setOwnerUID(String ownerUID) {
		this.ownerUID = ownerUID;
	}

}
