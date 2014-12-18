package com.mapgoo.zero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.OrderFormInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class OrderformDetailActivity extends BaseActivity {

	OrderFormInfo mOrderFormInfo;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_orderform_detail);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mOrderFormInfo = (OrderFormInfo)getIntent().getExtras().getSerializable("OrderFormInfo");
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
		( (TextView)findViewById(R.id.order_form_detail_from_name) ).setText(mOrderFormInfo.mFromName);
		( (TextView)findViewById(R.id.order_form_detail_for_name) ).setText(mOrderFormInfo.mForName);
		( (TextView)findViewById(R.id.order_form_detail_order_name) ).setText(mOrderFormInfo.mOrderName);
		( (TextView)findViewById(R.id.order_form_detail_unit_price) ).setText(mOrderFormInfo.mUnitPrice);
		( (TextView)findViewById(R.id.order_form_detail_count) ).setText(mOrderFormInfo.mCount);
		( (TextView)findViewById(R.id.order_form_detail_appointment) ).setText(mOrderFormInfo.mAppointment);
		( (TextView)findViewById(R.id.order_form_detail_note) ).setText(mOrderFormInfo.mNote);
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
			
		case R.id.order_form_detail_btn:{
				Intent forwardIntent = new Intent();
				forwardIntent.setClass(mContext, PingjiaActivity.class);

				Bundle mBundle = new Bundle();
				mBundle.putSerializable("OrderFormInfo", mOrderFormInfo);
				forwardIntent.putExtras(mBundle);
				
				startActivity(forwardIntent);	
			break;}
		default:
			break;
		}
	}

}
