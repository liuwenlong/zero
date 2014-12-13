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
		return  mForName+" - "+mFromName;
	}
	public String getOrderTime(){
		return  "提交:"+mOrderTime;
	}	
}
