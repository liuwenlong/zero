package com.mapgoo.zero.bean;

import java.io.Serializable;

import android.view.View;

public class MessageInfo implements Serializable {

	public String mMessage;
	public String mIsRead;
	public String mRecevTime;

	public boolean IsRead;
	public int NoticeID;
	public String Remark;
	public String Content;
	public String Title;
	public String CreateTime;
	
	public int getIocnVisiable(){
		if(IsRead)
			return View.INVISIBLE;
		else
			return View.VISIBLE;
	}
	
	public void setIsRead(boolean str){
		IsRead = str;
	}
	public void setNoticeID(int str){
		NoticeID = str;
	}
	public void setRemark(String str){
		Remark = str;
	}
	public void setContent(String str){
		Content = str;
	}
	public void setTitle(String str){
		Title = str;
	}
	public void setCreateTime(String str){
		CreateTime = str;
	}
}
