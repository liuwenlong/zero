package com.mapgoo.zero.bean;

import java.io.Serializable;

public class RenyuanInfo  implements Serializable {
	
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
	public void setIPeopleSex(boolean arg){
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
	
}
