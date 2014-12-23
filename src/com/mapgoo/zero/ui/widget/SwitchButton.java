package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huaan.icare.xsy.R;


/**
 * 概述：自定义SwitchButton
 * 
 * @author yao
 * @version 1.0
 * @created 2014年10月11日
 */
public class SwitchButton extends RelativeLayout {

	private View mViewBg;
	private ImageView mViewSwitchPoint;
	private int mSwitchPointSize;
	private int mSwitchBtnWidth;

	private boolean mSwitch = false;
	private OnSwitchListener mOnSwitchListener;
	private View mFrameView;

	/**
	 * 概述：开关监听器
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 */
	public interface OnSwitchListener {
		/**
		 * 概述：开关监听器回调函数
		 * 
		 * @author yao
		 * @version 1.0
		 * @created 2014年10月11日
		 * 
		 * @param v
		 * @param switchStatus
		 * @return
		 */
		public boolean onSwitch(SwitchButton v, boolean switchStatus);
	}

	public SwitchButton(Context context) {
		this(context, null);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mFrameView = LayoutInflater.from(context).inflate(R.layout.layout_switch_button, null);

		mViewBg = mFrameView.findViewById(R.id.rl_switch_bg);
		mViewSwitchPoint = (ImageView) mFrameView.findViewById(R.id.iv_switch_image);

		addView(mFrameView);
		ViewHelper.measureView(this);
		ViewHelper.measureView(mViewBg);
		ViewHelper.measureView(mViewSwitchPoint);

		mSwitchBtnWidth = mViewBg.getMeasuredWidth();
		mSwitchPointSize = mViewSwitchPoint.getMeasuredWidth();

		mFrameView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnSwitchListener != null && mOnSwitchListener.onSwitch(SwitchButton.this, !mSwitch)) {
					return;
				}
				setSwitch(!mSwitch);
			}
		});

		initSwitch(false);
	}

	/**
	 * 概述：初始化开关
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @param isSwitchOn
	 */
	public void initSwitch(boolean isSwitchOn) {
		setSwitch(isSwitchOn, 0);
	}

	/**
	 * 概述：设置开关
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @param isSwitchOn
	 */
	public void setSwitch(boolean isSwitchOn) {
		setSwitch(isSwitchOn, 0);
	}

	/**
	 * 概述：切换开关
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 */
	public void toggleSwitch() {
		if (mOnSwitchListener != null) {
			mOnSwitchListener.onSwitch(SwitchButton.this, !mSwitch);
		}

		setSwitch(!mSwitch, 150);
	}

	/**
	 * 概述：设置开关-带动画时间 Switch the Position of the SwitchButton's indicate point.
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @param isSwitchOn
	 *            indicate the switch position.
	 * @param duration
	 *            the switch animation duration
	 */
	public void setSwitch(boolean isSwitchOn, long duration) {
		this.mSwitch = isSwitchOn;
		Animation anim = getSwitchAnimation(isSwitchOn, duration);
		mViewSwitchPoint.startAnimation(anim);
	}

	/**
	 * 概述：设置开关监听器
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @param l
	 */
	public void setOnSwitchListener(OnSwitchListener l) {
		mOnSwitchListener = l;
	}

	/**
	 * 概述：
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @return
	 */
	public boolean isSwitchOn() {
		return mSwitch;
	}

	/**
	 * 概述：获取开关动画
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年10月11日
	 * 
	 * @param isSwitchOn
	 * @param durationMillis
	 * @return
	 */
	public Animation getSwitchAnimation(boolean isSwitchOn, long durationMillis) {

		TranslateAnimation anim = new TranslateAnimation(isSwitchOn ? 0 : mSwitchBtnWidth - mSwitchPointSize, isSwitchOn ? mSwitchBtnWidth
				- mSwitchPointSize : 0, 0, 0);
		anim.setDuration(durationMillis);
		anim.setFillAfter(true);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mViewBg.setBackgroundResource(mSwitch ? R.drawable.ic_switch_open_bg : R.drawable.ic_switch_close_bg);
			}
		});

		return anim;
	}

}
