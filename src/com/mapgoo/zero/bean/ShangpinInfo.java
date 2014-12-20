package com.mapgoo.zero.bean;

import java.io.Serializable;

public class ShangpinInfo  implements Serializable {

//	public String mFromName;//订单提供者名字
//	public String mShangpinName;//订单商品名字
//	public String mUnitPrice;//单价
//	public String mFuwuTime;//服务时间
//	public String mPayType;//付款方式
//	public String mShanMen;//上门与否
//	public String mAdress;//地址	
//	public String mPhone;//电话
//	public String mNote;//电话
//	public String mID;//ID
	public int mNumber;
	public boolean isCheck;//选定
	
	public ShangpinInfo(){
		isCheck = false;
	}
	
	public String Period;
	public String ProjectName;
	public String Remark;
	public String ImagePath;
	public String Price;
	public int ProjectID;

	public void setProjectID(int str){
		ProjectID = str;
	}
	public int getProjectID(){
		return ProjectID;
	}
	
	public void setPrice(String str){
		Price = str;
	}
	public String getPrice(){
		return Price;
	}
	
	public void setImagePath(String str){
		ImagePath = str;
	}
	public String getImagePath(){
		return ImagePath;
	}
	
	public void setRemark(String str){
		Remark = str;
	}
	public String getRemark(){
		return Remark;
	}
	
	public void setProjectName(String str){
		ProjectName = str;
	}
	public String getProjectName(){
		return ProjectName;
	}	
	
	public void setPeriod(String str){
		Period = str;
	}
	public String getPeriod(){
		return Period;
	}
	
}
