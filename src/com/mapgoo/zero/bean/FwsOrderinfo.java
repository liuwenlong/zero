package com.mapgoo.zero.bean;

import java.io.Serializable;

public class FwsOrderinfo implements Serializable {

public String OrderTime;
public String DeclineReason;
public String PatrolPeopleName;
public String OrderStatus;
public String HumanName;
public String OrderCode;
public String Comment;
public int OrderStatusID;
public String MobilePhone;
public String Remark;
public int RecID;
public String HumanAddress;

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
