package com.mapgoo.zero.bean;

import java.io.Serializable;

public class CarObject implements Serializable {

	private static final long serialVersionUID = 305459391757312807L;
	
	private String carBrand;
	private String carLicenseNo;
	private String carLogoURL;
	private int carLogoResId;

	private int validRemainDays;

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarLicenseNo() {
		return carLicenseNo;
	}

	public void setCarLicenseNo(String carLicenseNo) {
		this.carLicenseNo = carLicenseNo;
	}

	public String getCarLogoURL() {
		return carLogoURL;
	}

	public void setCarLogoURL(String carLogoURL) {
		this.carLogoURL = carLogoURL;
	}

	public int getCarLogoResId() {
		return carLogoResId;
	}

	public void setCarLogoResId(int carLogoResId) {
		this.carLogoResId = carLogoResId;
	}

	public int getValidRemainDays() {
		return validRemainDays;
	}

	public void setValidRemainDays(int validRemainDays) {
		this.validRemainDays = validRemainDays;
	}

}
