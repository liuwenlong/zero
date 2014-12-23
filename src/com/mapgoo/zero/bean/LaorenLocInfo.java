package com.mapgoo.zero.bean;

import java.io.Serializable;

import android.text.TextUtils;

public class LaorenLocInfo implements Serializable {
	
	public int HumanID;
	public String GPSSignal;
	public boolean HasSIM;
	public boolean HasMDT;
	public String Voltage;
	public String HumanName;
	public int ObjectID;
	public String PosMode;
	public String Lat;
	public String GSMSignal;
	public String Lon;
	public String GPSTime;
	public String Adress;
	public String SIM;	
	
	public void setSIM(String str){
		SIM = str;
	}		
	public void setGPSTime(String str){
		GPSTime = str;
	}	
	public void setLon(String str){
		Lon = str;
	}		
	public void setGSMSignal(String str){
		GSMSignal = str;
	}	
	public void setGPSSignal(String str){
		GPSSignal = str;
	}	
	public void setHasSIM(boolean str){
		HasSIM = str;
	}	
	public void setHasMDT(boolean str){
		HasMDT = str;
	}	
	public void setVoltage(String str){
		Voltage = str;
	}	
	public void setHumanName(String str){
		HumanName = str;
	}	
	public void setPosMode(String str){
		PosMode = str;
	}	
	public void setLat(String str){
		Lat = str;
	}		
	public void setObjectID(int str){
		ObjectID = str;
	}	
	public void setHumanID(int str){
		HumanID = str;
	}

	public boolean isLoactionAviable(){
		if(Lat!=null && Lon!=null&&!Lat.isEmpty()&&!Lon.isEmpty()){
			
			return true;
		}
		return false;
	}
	
	public String getLoactionStatus(){
		String str = null;
		str = String.format("%s [定位:%s]", GPSTime,PosMode);
		return str;		
	}
}
