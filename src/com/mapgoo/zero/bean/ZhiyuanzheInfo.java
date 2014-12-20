package com.mapgoo.zero.bean;

import java.io.Serializable;

public class ZhiyuanzheInfo  implements Serializable {

	public String mZhiyuanzheName;//订单提供者名字
	public String mFuwuName;//订单商品名字
	public String mFuwuTime;//服务时间
	public String mAdress;//地址	
	public String mPhone;//电话
	public String mID;//ID
	public String mXingbie;//ID
	
	public String Picture;
	public int HoldID;
	public String PeopleName;
	public String ServiceContent;
	public String ServiceTime;
	public Boolean PeopleSex;
	public String MobilePhone;
	public int PeopleNo;

	public void setPeopleNo(int  str){
		PeopleNo = str;
	}
	public int getPeopleNo(){
		return PeopleNo;
	}
	
	public void setMobilePhone(String str){
		MobilePhone = str;
	}
	public String getMobilePhone(){
		return MobilePhone;
	}
	
	public void setPeopleSex(Boolean str){
		PeopleSex = str;
	}
	public Boolean getPeopleSex(){
		return PeopleSex;
	}
	
	public void setServiceTime(String str){
		ServiceTime = str;
	}
	public String getServiceTime(){
		return ServiceTime;
	}
	
	public void setServiceContent(String str){
		ServiceContent = str;
	}
	public String getServiceContent(){
		return ServiceContent;
	}
	
	public void setPeopleName(String str){
		PeopleName = str;
	}
	public String getPeopleName(){
		return PeopleName;
	}
	
	public void setHoldID(int str){
		HoldID = str;
	}
	public int getHoldID(){
		return HoldID;
	}
	
	public void setPicture(String str){
		Picture = str;
	}
	public String getPicture(){
		return Picture;
	}
	
	
}
