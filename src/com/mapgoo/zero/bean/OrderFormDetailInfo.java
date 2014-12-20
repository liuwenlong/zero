package com.mapgoo.zero.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;

public class OrderFormDetailInfo implements Serializable {
	
	public class  OrderDetailInfo{
		public String ProductNumber;
		public String ProductName;
	}
	
	public String OrderTime;
	public String ServiceFee;
	public String DeclineReason;
	public String OrderStatus;
	public int BusinessType;
//	public JSONArray  OrderDetails;
	public ArrayList<OrderDetailInfo> OrderDetails;
	public String HumanName;
	public int OrderStatusID;
	public String Comment;
	public String BusinessName;
	public String Remark;
	public int RecID;
	public String OrderContent;

public void setOrderContent(String str){
	OrderContent = str;
}

public void setRecID(int str){
	RecID = str;
}

public void setRemark(String str){
	Remark = str;
}

public void setBusinessName(String str){
	BusinessName = str;
}

public void setComment(String str){
	Comment = str;
}

public void setOrderStatusID(int str){
	OrderStatusID = str;
}

public void setHumanName(String str){
	HumanName = str;
}

public void setOrderDetails(ArrayList<OrderDetailInfo> str){
	OrderDetails = str;
}

public void setBusinessType(int str){
	BusinessType = str;
}

public void setOrderStatus(String str){
	OrderStatus = str;
}

public void setDeclineReason(String str){
	DeclineReason = str;
}

public void setServiceFee(String str){
	ServiceFee = str;
}

public void setOrderTime(String str){
	OrderTime = str;
}


}
