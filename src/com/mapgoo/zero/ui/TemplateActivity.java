package com.mapgoo.zero.ui;

import android.os.Bundle;
import android.view.View;

import com.huaan.icare.xsy.R;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class TemplateActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.layout_fragment_container);
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
		super.setupActionBar(getText(R.string.title_4s_default).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_convenient_service_actionbar_bg, -1);

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
