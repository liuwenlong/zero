package com.mapgoo.zero.bean;

import java.io.Serializable;

public class OrderFormInfo implements Serializable {

	public String mForName;//订单给的老人名字
	public String mFromName;//订单提供者名字
	public String mOrderName;//订单商品名字
	public String mUnitPrice;//单价
	public String mCount;//数量
	public String mAppointment;//预约时间
	public String mOrderTime;//订单时间
	public String mOrderStatus;//备注
	public String mNote;//备注
	public String mID;//ID	
	
	public String getOrderTitle(){
		return  HumanName+" - "+BusinessName;
	}
	public String getOrderTime(){
		return  "提交:"+CreateTime;
	}	
	
	public String BusinessName;
	public String OrderStatus;
	public int RecID;
	public int BusinessType;
	public String CreateTime;
	public String HumanName;

	public void setHumanName(String str){
		HumanName = str;
	}
//	public String getHumanName(String str){
//		return HumanName;
//	}		
	
	public void setCreateTime(String str){
		CreateTime = str;
	}
//	public String getCreateTime(String str){
//		return CreateTime;
//	}	
	
	public void setBusinessType(int str){
		BusinessType = str;
	}
//	public int getBusinessType(String str){
//		return BusinessType;
//	}	
	
	public void setRecID(int str){
		RecID = str;
	}
//	public int getRecID(String str){
//		return RecID;
//	}
	
	public void setOrderStatus(String str){
		OrderStatus = str;
	}
//	public String getOrderStatus(String str){
//		return OrderStatus;
//	}
	
	public void setBusinessName(String str){
		BusinessName = str;
	}
//	public String getBusinessName(String str){
//		return BusinessName;
//	}
}
