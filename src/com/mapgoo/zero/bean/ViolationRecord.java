package com.mapgoo.zero.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 概述: 违章记录
 * 
 * @author yao
 * @version 1.0
 * @created 2014年12月2日
 */
public class ViolationRecord implements Serializable {

	private static final long serialVersionUID = -2436674180713816375L;

	private String desc;

	private Date date;

	private String addr;

	private float bill; // 罚单面值

	private int grade; // 扣除分数

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public float getBill() {
		return bill;
	}

	public void setBill(float bill) {
		this.bill = bill;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
