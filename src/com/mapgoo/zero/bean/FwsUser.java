package com.mapgoo.zero.bean;

import android.text.TextUtils;

public class FwsUser  extends User{
	
	public String picture;
	public String purview;
	public int holdId;
	public String holdName;
	public int serviceId;
	public String token;
	public int serviceType;
	public String serviceName;
	public String mPassword;	
	public String mUername;
	
	public FwsUser(){}
	public void setserviceName(String str){
		serviceName = str;
	}			
	public void setserviceType(int str){
		serviceType = str;
	}		
	public void setserviceId(int str){
		serviceId = str;
	}			
	public void setholdName(String str){
		holdName = str;
	}		
	public void setholdId(int str){
		holdId = str;
	}		
	public void setpurview(String str){
		purview = str;
	}	
public void settoken(String str){
	token = str;
}

}
