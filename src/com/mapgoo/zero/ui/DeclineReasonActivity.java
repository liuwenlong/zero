package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.FwsOrderinfo;
import com.mapgoo.zero.bean.MessageInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class DeclineReasonActivity extends BaseActivity {

	FwsOrderinfo mFwsOrderinfo;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_decline_reason);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mFwsOrderinfo = (FwsOrderinfo)savedInstanceState.get("FwsOrderinfo");
		} else {
			mFwsOrderinfo = (FwsOrderinfo)getIntent().getExtras().getSerializable("FwsOrderinfo");
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("FwsOrderinfo", mFwsOrderinfo);
		super.onSaveInstanceState(outState);
	}
	EditText reason;
	@Override
	public void initViews() {
		super.setupActionBar("消息", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		reason  = (EditText)findViewById(R.id.msg_read_msg);
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
		case R.id.order_form_desline:
			OrderDecline();
		default:
			break;
		}
	}
	private void OrderDecline(){
		String declineReason = reason.getText().toString();
		
		if(TextUtils.isEmpty(declineReason)){
			mToast.toastMsg("请输入拒受理原因");
			return;
		}
			
		ApiClient.OrderDecline(mFwsOrderinfo.RecID,declineReason,
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
										mToast.toastMsg("提交成功");
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
