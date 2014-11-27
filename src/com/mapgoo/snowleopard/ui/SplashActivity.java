package com.mapgoo.snowleopard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.mapgoo.snowleopard.R;

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
		startSettings();
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
		super.setupActionBar("", 1, -1, -1);

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
		startActivity(new Intent(mContext, LockPatternActivity.class));
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

	private void startSettings(){
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashActivity.this,SettingsActivity.class);
				SplashActivity.this.startActivity(intent);
			}
		},2000);
	}
}
