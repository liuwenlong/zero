package com.mapgoo.zero.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.volley.Response.Listener;
import com.huaan.icare.xsy.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.OrderFormInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class PingjiaActivity extends BaseActivity implements OnCheckedChangeListener {
	OrderFormInfo mOrderFormInfo;
	CheckBox mBox1,mBox2,mBox0;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_pingjia);
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
		super.setupActionBar(getText(R.string.ping_jia_title).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mBox0 = (CheckBox)findViewById(R.id.pingjia_feichang_checkbox_0);
		mBox1 = (CheckBox)findViewById(R.id.manyi_feichang_checkbox_1);
		mBox2 = (CheckBox)findViewById(R.id.yiban_feichang_checkbox_2);
		mBox2.setOnCheckedChangeListener(this);
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		mBox0.setChecked(false);
		mBox1.setChecked(false);
		mBox2.setChecked(false);
		buttonView.setChecked(true);
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
		case R.id.shangpin_yuyue:
			OrderComplete();
			break;
		default:
			break;
		}
	}
	
	private int getChecked(){
		if(mBox0.isChecked())
			return 0;
		if(mBox1.isChecked())
			return 1;
		if(mBox2.isChecked())
			return 2;
		return 0;
	}
	
	private void OrderComplete(){
		ApiClient.OrderComplete(getChecked(),mOrderFormInfo.RecID,
				new onReqStartListener(){
					public void onReqStart() {
						getmProgressDialog().show();
					}}, 
					new Listener<JSONObject> (){
						public void onResponse(JSONObject response) {
							getmProgressDialog().dismiss();
							Log.d("onResponse",response.toString());
							if (response.has("error")) {
								try {
									if (response.getInt("error") == 0) {
										mToast.toastMsg("订单完成");
										setResult(RESULT_OK);
										finish();
									}else{
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
						}},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));		
	}


}
