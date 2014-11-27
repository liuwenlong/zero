package com.mapgoo.snowleopard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

import com.mapgoo.snowleopard.MGApp;
import com.mapgoo.snowleopard.R;

public class DateUtils {

	/**
	 * 概述: 日期转换， 从一种格式的日期字符串转换成另一种格式的日期字符串
	 * 
	 * @auther yao
	 * @param DateStr
	 * @param srcFormat
	 * @param targetFormat
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateFormatConverter(String dateStr, String srcFormat, String targetFormat) {
		String result = "";

		if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(srcFormat) || StringUtils.isEmpty(targetFormat))
			return "";

		Date srcDate = null;
		try {
			srcDate = new SimpleDateFormat(srcFormat).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (srcDate != null)
			result = new SimpleDateFormat(targetFormat).format(srcDate);

		return result;
	}

}
