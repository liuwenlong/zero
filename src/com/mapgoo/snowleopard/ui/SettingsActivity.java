package com.mapgoo.snowleopard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mapgoo.snowleopard.R;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class SettingsActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_settings);
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
		super.setupActionBar(getText(R.string.settings_title).toString(), 1, R.drawable.ic_back_arrow_white, -1);
		findViewById(R.id.rl_settings_set_pwd).setOnClickListener(this);
		findViewById(R.id.rl_settings_set_bd_pwd).setOnClickListener(this);
		findViewById(R.id.rl_settings_msg_notify_set).setOnClickListener(this);
		findViewById(R.id.rl_settings_offline_map).setOnClickListener(this);
		findViewById(R.id.rl_settings_about).setOnClickListener(this);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.rl_settings_set_pwd:
			startActivity(new Intent(mContext, ModifyPassWordActivity.class));
			break;
		case R.id.rl_settings_msg_notify_set:
			startActivity(new Intent(mContext, MsgNotifierSettingsActivity.class));
			break;
		default:
			break;
		}
	}

}
