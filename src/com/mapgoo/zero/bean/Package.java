package com.mapgoo.zero.bean;

import java.io.Serializable;

/**
 * 概述: 套餐实体
 * 
 * @author yao
 * @version 1.0
 * @created 2014年12月1日
 */
public class Package implements Serializable {

	private static final long serialVersionUID = -303884385738550473L;

	private String packageName;
	private float packagePrice;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public float getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(float packagePrice) {
		this.packagePrice = packagePrice;
	}

}
