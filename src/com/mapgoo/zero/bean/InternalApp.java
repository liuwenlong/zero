package com.mapgoo.zero.bean;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

/**
 * 概述: 内部应用实体
 * 
 * @author yao
 * @version 1.0
 * @created 2014年12月1日
 */
public class InternalApp implements Serializable {

	private static final long serialVersionUID = 3529393796458684214L;

	private String appName;
	private Drawable appIcon;

	// 后续继续增加其他属性

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

}
