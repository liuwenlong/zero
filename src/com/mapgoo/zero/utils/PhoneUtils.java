package com.mapgoo.zero.utils;

import java.util.regex.Pattern;

import android.widget.EditText;

import com.mapgoo.zero.MGApp;
import com.mapgoo.zero.R;
import com.mapgoo.zero.ui.widget.MyToast;

public class PhoneUtils {

	public static boolean validatePhoneNum(String phoneNum, EditText et_view) {

		if (!isPhoneNumValied(phoneNum, et_view)) {
			MyToast.getInstance(MGApp.pThis).toastMsg(R.string.hint_phone_invalid);
			SoftInputUtils.requestFocus(MGApp.pThis, et_view);

			return false;
		} else
			return true;
	}

	/**
	 * 概述: 手机号码验证
	 * 
	 * @auther yao
	 */
	private static boolean isPhoneNumValied(String phoneNum, EditText et_view) {
		if (StringUtils.isEmpty(phoneNum)) {
			MyToast.getInstance(MGApp.pThis).toastMsg(R.string.account_check_info_tel_error_null);
			SoftInputUtils.requestFocus(MGApp.pThis, et_view);

			return false;
		}

		if (phoneNum.length() > 11 || phoneNum.length() < 11 || !phoneNum.startsWith("1")) {
			MyToast.getInstance(MGApp.pThis).toastMsg(R.string.account_check_info_tel_error);
			et_view.setText("");
			SoftInputUtils.requestFocus(MGApp.pThis, et_view);

			return false;
		}

		return isPhoneNumLegal(phoneNum);
	}

	/**
	 * 概述: 手机号码合法性验证
	 * 
	 * @auther yao
	 * @param phoneNum
	 * @return
	 */
	private static boolean isPhoneNumLegal(String phoneNum) {
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
