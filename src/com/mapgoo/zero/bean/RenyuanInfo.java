package com.mapgoo.zero.bean;

import java.io.Serializable;

public class RenyuanInfo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int PeopleNo;
	public String PeopleName;
	public boolean PeopleSex;
	public String IDCard;
	public String Birthday;
	public String Picture;
	public int ServiceID;
	
	public void setIPeopleNo(int arg){
		PeopleNo = arg;
	}		
	public void setIPeopleName(String arg){
		PeopleName = arg;
	}		
	public void setPeopleSex(boolean arg){
		PeopleSex = arg;
	}
	public void setIDCard(String arg){
		IDCard = arg;
	}	
	public void setBirthday(String arg){
		Birthday = arg;
	}
	public void setPicture(String arg){
		Picture = arg;
	}
	public void setServiceID(int arg){
		ServiceID = arg;
	}
	public void setPeopleSexString(String str){
		if(str!=null && !str.isEmpty()){
			if(str.contains("男"))
				PeopleSex = true;
			else
				PeopleSex = false;
		}
	}
	public String getPeopleSexString(){
		if(PeopleSex)
			return "男";
		else
			return "女";
	}
}
