package com.mapgoo.zero.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huaan.icare.family.R;
import com.mapgoo.zero.utils.StringUtils;

/**
 * 概述: 4s服务-预约
 * 
 * @Author yao
 */
public class PreArrangementActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_prearrangement);
	}

	private String mTitle;

	@Override
	public void initData(Bundle savedInstanceState) {
		int position;

		if (savedInstanceState != null) {
			position = savedInstanceState.getInt("position", 0);
		} else {
			position = getIntent().getIntExtra("position", 0);
		}

		switch (position) {
		case 0:
			mTitle = getText(R.string.title_prearrange_repair).toString();
			break;

		case 1:
			mTitle = getText(R.string.title_prearrange_car_wash).toString();
			break;
		case 2:
			mTitle = getText(R.string.title_prearrange_maintenance).toString();
			break;
		case 3:
			mTitle = getText(R.string.title_prearrange_drive).toString();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	private TextView tv_call_num;

	@Override
	public void initViews() {
		super.setupActionBar(mTitle, 2, R.drawable.ic_back_arrow_white, R.drawable.ic_actionbar_record, R.drawable.ic_4s_actionbar_bg, -1);

		tv_call_num = (TextView) findViewById(R.id.tv_call_num);

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

		case R.id.rl_prearrange_call:
			if (!StringUtils.isEmpty(tv_call_num.getText())) {
				
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + tv_call_num.getText()));

				startActivity(intent);
			}

			break;
			
		case R.id.iv_ab_right_btn: // 预约记录

			startActivity(new Intent(mContext, PreArrangementsRecordsActivity.class));
			
			break;

		default:
			break;
		}
	}

}
