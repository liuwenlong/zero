package com.mapgoo.zero.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

import android.util.Log;

/**
 * 概述: 数字工具集
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月17日
 */
public class NumUtils {

	/**
	 * 概述: 字符转double
	 * 
	 * @auther yao
	 * @param numStr
	 *            字符数字
	 * @param digits
	 *            保留小数位数
	 * @return 传入为空则，返回为digits小数位的0， 默认四舍五入，即使格式化失败也返回digits小数位的0
	 */
	public static double stringToDouble(String numStr, int digits) {

		NumberFormat df = NumberFormat.getNumberInstance();
		df.setGroupingUsed(false);	// 不分组，即不会有逗号
		df.setMaximumFractionDigits(digits); // 保留小数
		df.setRoundingMode(RoundingMode.HALF_UP); // 四舍五入

		if (StringUtils.isEmpty(numStr)) // 为空返回 0.xxx
			return Double.valueOf(df.format(0));

		double result;
		try {
			result = Double.valueOf(numStr);
		} catch (NumberFormatException e) { // 格式化失败，比如遇到：1.2.3的情况
			e.printStackTrace();

			result = 0.0;
		}

		result = Double.valueOf(df.format(result));

		Log.d("result", numStr + " --> " + result);

		return result;
	}
}
