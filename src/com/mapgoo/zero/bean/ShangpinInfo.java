package com.mapgoo.zero.bean;

import java.io.Serializable;

public class ShangpinInfo  implements Serializable {

	public String mFromName;//订单提供者名字
	public String mShangpinName;//订单商品名字
	public String mUnitPrice;//单价
	public String mFuwuTime;//服务时间
	public String mPayType;//付款方式
	public String mShanMen;//上门与否
	public String mAdress;//地址	
	public String mPhone;//电话
	public String mNote;//电话
	public String mID;//ID
	public boolean isCheck;//选定
	
	public ShangpinInfo(){
		isCheck = false;
	}
}
