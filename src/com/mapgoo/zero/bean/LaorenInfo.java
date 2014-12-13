package com.mapgoo.zero.bean;

import java.io.Serializable;

public class LaorenInfo implements Serializable {
	public String mName;//姓名
	public String mOld;//年龄
	public String mLeixing;//类型
	public String mDingwei;//定位
	public String mXingbie;//性别
	public String mShenfen;//身份证
	public String mAdress;//地址
	public String mPhone;//电话
	public String mImage;//头像
	
	public  String getHomeTitle(){
		return mName+"         ("+mXingbie+")"+mOld;
	}
}
