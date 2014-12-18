package com.mapgoo.zero.bean;

import java.io.Serializable;

import android.text.TextUtils;

public class LaorenInfo implements Serializable {
	public String mName;//姓名
	public String mOld;//年龄
	public String mLeixing;//类型
	public String mDingwei;//定位
	public String mXingbie;//性别
	public String mShenfen;//身份证
	public String mAdress;//地址
	public String mPhone;//电话
	public String mImage;//头像
	public String mLocationTime;//头像
	public String mLocationAdress;//头像
	public String mLocationLat;//头像
	public String mLocationLng;//头像
	
	public String HumanID;
	public String Birthday;
	public String IDCardNo;
	public String Address;
	public String HumanName;
	public int ObjectID;
	public String HumanType;
	public String AlldayTel;
	public boolean HumanSex =false;
	public String AvatarImage;
	public boolean HasSOSMDT;
	
	public LaorenInfo(){
		HumanSex =false;
	};
	
	public void setHasSOSMDT(boolean str){
		HasSOSMDT = str;
	}
	public boolean getHasSOSMDT(){
		return HasSOSMDT;
	}		
	public void setAvatarImage(String str){
		AvatarImage = str;
	}
	public String getAvatarImage(){
		return AvatarImage;
	}		
	public void setHumanSex(boolean str){
		HumanSex = str;
	}
	public boolean getHumanSex(){
		return HumanSex;
	}	
	public void setAlldayTel(String str){
		AlldayTel = str;
	}
	public String getAlldayTel(){
		return AlldayTel;
	}	
	public void setHumanType(String str){
		HumanType = str;
	}
	public String getHumanType(){
		return HumanType;
	}		
	public void setObjectID(int str){
		ObjectID = str;
	}
	public int getObjectID(){
		return ObjectID;
	}		
	public void setHumanName(String str){
		HumanName = str;
	}
	public String getHumanName(){
		return HumanName;
	}		
	public void setAddress(String str){
		Address = str;
	}
	public String getAddress(){
		return Address;
	}		
	public void setIDCardNo(String str){
		IDCardNo = str;
	}
	public String getIDCardNo(){
		return IDCardNo;
	}		
	public void setBirthday(String str){
		Birthday = str;
	}
	public String getBirthday(){
		return Birthday;
	}	
	public void setHumanID(String str){
		HumanID = str;
	}
	public String getHumanID(){
		return HumanID;
	}
	
	public String getSexString(){
		if(HumanSex)
			return "男";
		else
			return "女";
	}
	
	public  String getHomeTitle(){
		return HumanName 
				+ "         (" 
				+ getSexString()
				+")"
				+Birthday;
	}
}
