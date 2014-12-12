package com.mapgoo.zero.utils;

import android.content.pm.PackageManager.NameNotFoundException;

import com.mapgoo.zero.MGApp;

/**
 * 概述: 版本管理工具， TODO 有待优化
 * 
 * @orignalAuthor yao
 * @createTime 2014年10月30日 下午5:17:41
 * 
 * @improvedAuther yao
 * @modifyTime 2014年10月30日
 */
public class VersionUtils {

	
	/**
	 * 概述: 获取版本名 , 举个栗子：v1.0.0
	 *
	 * @auther yao
	 * @return
	 */
	public static String getVersionName() {
		String pkName = MGApp.pThis.getPackageName(); // 获取包名
		String versionName = "";

		try {
			versionName = MGApp.pThis.getPackageManager().getPackageInfo(pkName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return "v" + versionName;
	}

}
