package com.mapgoo.snowleopard.utils;

import java.util.regex.Pattern;

public class TelNumUtils {
	/**
	 * 概述: 手机号码合法性验证
	 * 
	 * @auther yao
	 * @param phoneNum
	 * @return
	 */
	public static boolean isPhoneNumLegal(String phoneNum) {
		if (StringUtils.isEmpty(phoneNum))
			return false;

		if (phoneNum.length() > 11 || phoneNum.length() < 11 || !phoneNum.startsWith("1"))
			return false;

		// TIPS 暂不支持12、17开头的号码
		// TIPS 暂不支持11位以上的号码，如物联网卡
		String phoneNumRegex = "^((13\\d)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern phoneNumPattern = Pattern.compile(phoneNumRegex);

		return phoneNumPattern.matcher(phoneNum).matches();
	}
}
