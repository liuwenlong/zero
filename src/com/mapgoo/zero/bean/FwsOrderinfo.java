package com.mapgoo.zero.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FwsOrderinfo implements Serializable {
	public class  OrderDetailInfo implements Serializable{
		public String ProductNumber;
		public String ProductName;
	}
public String OrderTime;
public String DeclineReason;
public String PatrolPeopleName;
public ArrayList<OrderDetailInfo> OrderDetails;
public String ServiceFee;
public String OrderStatus;
public String HumanName;
public String OrderCode;
public String Comment;
public int OrderStatusID;
public String MobilePhone;
public String Remark;
public int RecID;
public String HumanAddress;

public String getProductName(){
	String name = " ";
	if(OrderDetails!=null && OrderDetails.size()>0){
		for(int i=0;i<OrderDetails.size();i++){
			if(i!=0)
				name += ",";
			name += OrderDetails.get(i).ProductName;
		}
	}
	return name;
}

public void setServiceFee(String str){
	ServiceFee = str;
}
public void setOrderDetails(ArrayList<OrderDetailInfo> str){
	OrderDetails = str;
}
public void setHumanAddress(String str){
	HumanAddress = str;
}
public void setRecID(int str){
	RecID = str;
}
public void setRemark(String str){
	Remark = str;
}
public void setMobilePhone(String str){
	MobilePhone = str;
}
public void setOrderStatusID(int str){
	OrderStatusID = str;
}
public void setComment(String str){
	Comment = str;
}
public void setOrderCode(String str){
	OrderCode = str;
}
public void setHumanName(String str){
	HumanName = str;
}
public void setOrderStatus(String str){
	OrderStatus = str;
}
public void setPatrolPeopleName(String str){
	PatrolPeopleName = str;
}
public void setDeclineReason(String str){
	DeclineReason = str;
}

public void setOrderTime(String str){
	OrderTime = str;
}


}
