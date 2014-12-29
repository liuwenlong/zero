package com.mapgoo.zero.bean;

import java.io.Serializable;

public class FwsShangpinInfo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	public String Period;
	public String ProjectName;
	public String Remark;
	public int ServiceID;
	public String ImagePath;	
	public String Price;
	public int ProjectID;
	public void setProjectID(int arg){
		ProjectID = arg;
	}		
	public void setPrice(String arg){
		Price = arg;
	}			
	public void setImagePath(String arg){
		ImagePath = arg;
	}				
	public void setServiceID(int arg){
		ServiceID = arg;
	}			
	public void setRemark(String arg){
		Remark = arg;
	}		
	public void setProjectName(String arg){
		ProjectName = arg;
	}	
	public void setPeriod(String arg){
		Period = arg;
	}
	
	public String getStartTime(){
		if(Period!=null && !Period.isEmpty()){
			String time[] = Period.split("-");
			if(time.length>=2){
				String start = time[0];
					return start;
			}
		}
		return " ";
	}
	public String getEndTime(){
		if(Period!=null && !Period.isEmpty()){
			String time[] = Period.split("-");
			if(time.length>=2){
				String start = time[1];
					return start;
			}
		}
		return " ";
	}
}
