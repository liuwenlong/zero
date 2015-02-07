package com.mapgoo.zero.bean;

import java.io.Serializable;

public class DianpuInfo  implements Serializable {

	public String mFromName;//订单提供者名字
	public String mOrderName;//订单商品名字
	public String mUnitPrice;//单价
	public String mFuwuTime;//服务时间
	public String mPayType;//付款方式
	public String mShanMen;//上门与否
	public String mAdress;//地址	
	public String mPhone;//电话
	public String mID;//ID
	
	public String PayMent;//ID
	public String Phone;//ID
	public String HoldID;//ID
	public String ServiceID;//ID
	public String Address;//ID
	public String ServiceTime;//ID
	public String ServiceType;//ID
	public boolean IfOnSite;//ID
	public String CompanyName;//ID
	public String Charges;//ID
	public void setPhone(String str){
		Phone = str;
	}
	public String getPhone(){
		return Phone;
	}	
	
	public void setHoldID(String str){
		HoldID = str;
	}
	public String getHoldID(){
		return Address;
	}	
	
	public void setServiceID(String str){
		ServiceID = str;
	}
	public String getServiceID(){
		return Address;
	}		
	
	public void setAddress(String str){
		Address = str;
	}
	public String getAddress(){
		return Address;
	}		
	
	public void setServiceTime(String str){
		ServiceTime = str;
	}
	public String getServiceTime(){
		return ServiceTime;
	}	
	
	public void setServiceType(String str){
		ServiceType = str;
	}
	public String getServiceType(){
		return ServiceType;
	}
	public void setIfOnSite(boolean str){
		IfOnSite = str;
	}
	public boolean getIfOnSite(){
		return IfOnSite;
	}
	
	public void setPayMent(String str){
		PayMent = str;
	}
	public String getPayMent(){
		return PayMent;
	}
	
	public void setCharges(String str){
		Charges = str;
	}
	public String getCharges(){
		return Charges;
	}	
	
	public void setCompanyName(String str){
		CompanyName = str;
	}
	public String getCompanyName(){
		return CompanyName;
	}
	
	public String getShangmen(){
			if(IfOnSite)
				return "是";
			else 
				return "否";
	}
	
	public String[] getPayMentList(){
		String[] str = null;
		if(PayMent != null && PayMent.length() > 0){
			if(PayMent.contains(",")){
				str = PayMent.split(",");
			}else{
				str = new String[]{PayMent};
			}
		}else{
			str = new String[]{"现金"};
		}
		return str;
	}
}
