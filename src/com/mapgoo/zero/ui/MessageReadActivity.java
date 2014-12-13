package com.mapgoo.zero.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.MessageInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class MessageReadActivity extends BaseActivity {

	MessageInfo mMsg;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_message_read);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mMsg = (MessageInfo)getIntent().getExtras().getSerializable("mMsg");
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.order_form_detail).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		((TextView)findViewById(R.id.msg_read_msg)).setText(mMsg.mMessage);
		((TextView)findViewById(R.id.msg_read_time)).setText(mMsg.mRecevTime);
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

		default:
			break;
		}
	}

}
