package com.mapgoo.zero.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response.Listener;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.RenyuanInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class EditRenyuanActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_add_or_modify_runyuan);
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
		super.setupActionBar(getString(R.string.fws_renyuan_add), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
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
		case R.id.add_shangpin_tijiao:
			saveRenyuan();
			break;
		default:
			break;
		}
	}
	
	private RenyuanInfo getRenyuanInfo(){
		RenyuanInfo info=new RenyuanInfo();
		info.PeopleNo=0;
		info.PeopleName="u珊珊";
		info.Birthday="19880213";
		info.PeopleSex=false;
		info.IDCard="423978091";
		info.ServiceID = mFwsUser.serviceId;
		info.Picture="null";
		return info;
	}
	
	public void saveRenyuan(){
		
		ApiClient.savePeopleBasic(getRenyuanInfo(),
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
