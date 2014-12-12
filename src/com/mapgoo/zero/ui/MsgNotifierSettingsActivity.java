package com.mapgoo.zero.ui;

import com.mapgoo.zero.R;

import android.os.Bundle;
import android.view.View;


/**
 * 概述: 消息通知设置
 * 
 * @Author yao
 */
public class MsgNotifierSettingsActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_set_msg_notifier);
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

		super.setupActionBar(getText(R.string.settings_msg_notify_set).toString(), 1,R.drawable.ic_back_arrow_white, -1, -1, -1);

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
