package com.mapgoo.zero.bean;

import android.text.TextUtils;

public class XsyUser  extends User{
	
//	private String mUerName;
//	private String mIMEI;
	public String mPassword;
//	private String mImageUrl;
	public String picture;	
	public String createTime;	
	public String	purview;
	public String holdId;		
	public String holdName;	
	public String token;	
	public String remark;	
	public String peopleNo;	
	public String userId;	
	public String loginCount;
	public String userName;
	public String userPass;	
	public String userType;	
	
	public XsyUser(){}
	
	
	
public void setpicture(String str){
	picture = str;
}
public String getpicture(String str){
	return picture;
}

public void setpeopleNo(String str){
	peopleNo = str;
}
public String getpeopleNo(String str){
	return peopleNo;
}

public void setuserId(String str){
	userId = str;
}
public String getuserId(String str){
	return userId;
}

public void setuserName(String str){
	userName = str;
}
public String getuserName(String str){
	return token;
}

public void settoken(String str){
	token = str;
}
public String gettoken(String str){
	return token;
}

//	public void setUerName(String str){
//		if(TextUtils.isEmpty(str))
//			return;
//		mUerName = str;
//	}
//	public String getUerName(String str){
//		return mUerName;
//	}
//	
//	public void setIMEI(String str){
//		if(TextUtils.isEmpty(str))
//			return;
//		mIMEI = str;
//	}
//	public String getIMEI(String str){
//		return mIMEI;
//	}
//
//	public void setPassword(String str){
//		if(TextUtils.isEmpty(str))
//			return;
//		mPassword = str;
//	}
//	public String getPassword(String str){
//		return mPassword;
//	}
//	
//	public void setImageUrl(String str){
//		if(TextUtils.isEmpty(str))
//			return;
//		mImageUrl = str;
//	}
//	public String getImageUrl(String str){
//		return mImageUrl;
//	}
}
