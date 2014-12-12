package com.mapgoo.zero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mapgoo.zero.R;

/**
 * 概述: 车况查询
 * 
 * @Author yao
 */
public class CarConditionQueryActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_carcondition_query);
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
		super.setupActionBar(getText(R.string.title_carcondtion_query).toString(), 2, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_btn_help, R.drawable.ic_carcondtion_actionbar_bg, -1);

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
			
		case R.id.iv_loc_service:
			startActivity(new Intent(mContext, LocServiceActivity.class));
			
			break;

		default:
			break;
		}
	}

}
