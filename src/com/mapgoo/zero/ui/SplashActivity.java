package com.mapgoo.zero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import cn.jpush.android.api.JPushInterface;

import com.huaan.icare.pub.R;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class SplashActivity extends BaseActivity {

	@Override
	public void setContentView() {
		final View view = getLayoutInflater().inflate(R.layout.activity_splash, null);

		setContentView(view);

		AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});

	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {

	}

	@Override
	public void handleData() {

	}
	
	/**
	 * 概述：跳转到...
	 * 
	 * @author yao
	 * 
	 * @created 2014年7月14日
	 */
	private void redirectTo() {

//		startActivity(new Intent(mContext, LockPatternActivity.class)
//		.putExtra(LockPatternActivity.START_FROM, LockPatternActivity.START_FROM_SPLASH));

		startActivity(new Intent(mContext, LoginActivity.class));
		
		
		
		finish();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;

		default:
			break;
		}
	}
	
	// JPUSH统计
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(mContext);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(mContext);
	}
}
