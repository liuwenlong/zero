package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
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
		super.setupActionBar("消息", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		((TextView)findViewById(R.id.msg_read_title)).setText(mMsg.Title);
		((TextView)findViewById(R.id.msg_read_msg)).setText(mMsg.Content);
		((TextView)findViewById(R.id.msg_read_time)).setText(mMsg.CreateTime);
		if(!mMsg.IsRead)
			SetNoticeRead();
	}

	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			setResult(RESULT_OK);
			finish();
			break;
		default:
			break;
		}
	}
	private void SetNoticeRead(){
		ApiClient.SetNoticeRead(mMsg.NoticeID,mXsyUser.getUserId(),
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
										//JSONArray array = response.getJSONArray("result");
										//if(array!=null&&array.length()>1){
											 //mLaorenList = JSON.parseObject(response.getJSONObject("result").toString(), (ArrayList<LaorenInfo>).cl);
//											 mMessageList = (ArrayList<MessageInfo>) JSON.parseArray(array.get(1).toString(), MessageInfo.class);
//											 mMessageAdapter.mDataList = mMessageList;
//											 mMessageAdapter.notifyDataSetChanged();
											// refresLastLaoren();
											// Log.d("onResponse",array.get(1).toString());
										//}
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
