package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huaan.icare.family.R;

public class PatternIndicatorView extends LinearLayout {

	public PatternIndicatorView(Context context) {
		super(context);

		init(context);
	}

	public PatternIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	public PatternIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init(context);
	}

	private LinearLayout mRootView;
	private ImageView iv_pattern_num1;
	private ImageView iv_pattern_num2;
	private ImageView iv_pattern_num3;
	private ImageView iv_pattern_num4;
	private ImageView iv_pattern_num5;
	private ImageView iv_pattern_num6;
	private ImageView iv_pattern_num7;
	private ImageView iv_pattern_num8;
	private ImageView iv_pattern_num9;

	// 初始化
	private void init(Context context) {
		mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_lock_pattern_indicator, null);
		iv_pattern_num1 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num1);
		iv_pattern_num2 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num2);
		iv_pattern_num3 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num3);
		iv_pattern_num4 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num4);
		iv_pattern_num5 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num5);
		iv_pattern_num6 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num6);
		iv_pattern_num7 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num7);
		iv_pattern_num8 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num8);
		iv_pattern_num9 = (ImageView) mRootView.findViewById(R.id.iv_pattern_num9);

		addView(mRootView);

		clear();
	}

	/**
	 * 概述: 清楚所选
	 * 
	 * @auther yao
	 */
	public void clear() {
		// TODO 先用笨办法
		iv_pattern_num1.setSelected(false);
		iv_pattern_num2.setSelected(false);
		iv_pattern_num3.setSelected(false);
		iv_pattern_num4.setSelected(false);
		iv_pattern_num5.setSelected(false);
		iv_pattern_num6.setSelected(false);
		iv_pattern_num7.setSelected(false);
		iv_pattern_num8.setSelected(false);
		iv_pattern_num9.setSelected(false);
	}

	/**
	 * 概述: 设置pattern指示器的显示值， 如：1235789 <br> 
	 * TODO 先用笨办法
	 * 
	 * @auther yao
	 * @param patternStr
	 */
	public void setPattern(String patternStr) {

		for (int i = 0; i < patternStr.length(); i++) {
			int patternNumItemVal = Integer.valueOf(String.valueOf(patternStr.charAt(i)));

			switch (patternNumItemVal) {
			case 1:
				iv_pattern_num1.setSelected(true);
				break;
			case 2:
				iv_pattern_num2.setSelected(true);
				break;
			case 3:
				iv_pattern_num3.setSelected(true);
				break;
			case 4:
				iv_pattern_num4.setSelected(true);
				break;
			case 5:
				iv_pattern_num5.setSelected(true);
				break;
			case 6:
				iv_pattern_num6.setSelected(true);
				break;
			case 7:
				iv_pattern_num7.setSelected(true);
				break;
			case 8:
				iv_pattern_num8.setSelected(true);
				break;
			case 9:
				iv_pattern_num9.setSelected(true);
				break;

			default:
				break;
			}

		}

	}

}
