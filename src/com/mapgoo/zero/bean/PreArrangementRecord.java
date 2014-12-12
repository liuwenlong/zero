package com.mapgoo.zero.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 概述: 预约记录 实体
 * 
 * @author yao
 * @version 1.0
 * @created 2014年12月3日
 */
public class PreArrangementRecord implements Serializable {

	private static final long serialVersionUID = 5354373981646296239L;

	private String preArrangeType;

	private String preArrangeCarBrand;

	private String preArrangeCarLisenceNum;

	private String preArrangeStore; // 预约的商家/预约试驾的商家

	private Date preArrangeTime;

	private boolean serviceAccept; // 服务状态/受理状态 true=已受理 false=未受理

	private String customer; // 预约人

	private String remarks; // 备注

	public String getPreArrangeType() {
		return preArrangeType;
	}

	public void setPreArrangeType(String preArrangeType) {
		this.preArrangeType = preArrangeType;
	}

	public String getPreArrangeCarBrand() {
		return preArrangeCarBrand;
	}

	public void setPreArrangeCarBrand(String preArrangeCarBrand) {
		this.preArrangeCarBrand = preArrangeCarBrand;
	}

	public String getPreArrangeCarLisenceNum() {
		return preArrangeCarLisenceNum;
	}

	public void setPreArrangeCarLisenceNum(String preArrangeCarLisenceNum) {
		this.preArrangeCarLisenceNum = preArrangeCarLisenceNum;
	}

	public Date getPreArrangeTime() {
		return preArrangeTime;
	}

	public void setPreArrangeTime(Date preArrangeTime) {
		this.preArrangeTime = preArrangeTime;
	}

	public String getPreArrangeStore() {
		return preArrangeStore;
	}

	public void setPreArrangeStore(String preArrangeStore) {
		this.preArrangeStore = preArrangeStore;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isServiceAccept() {
		return serviceAccept;
	}

	public void setServiceAccept(boolean serviceAccept) {
		this.serviceAccept = serviceAccept;
	}

}
