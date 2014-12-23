package com.mapgoo.zero.ui;


import com.huaan.icare.xsy.R;
import com.mapgoo.zero.utils.VersionUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * 概述: 消息通知设置
 * 
 * @Author yao
 */
public class SettingsAboutActivity extends BaseActivity {

	TextView app_cur_version;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_settings_about);
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
		super.setupActionBar("关于", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		app_cur_version = (TextView)findViewById(R.id.tv_app_cur_ver);
		app_cur_version.setText(VersionUtils.getVersionName());
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

	@Override
	protected void handleData() {
		// TODO Auto-generated method stub
	}

}
