package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.huaan.icare.xsy.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.RequestUtils;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class XiaoxiActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<MessageInfo> mMessageList = new ArrayList<MessageInfo>();
	private MessageAdapter mMessageAdapter;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_laoren);
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
		super.setupActionBar("通知中心", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
		if(mMessageList.isEmpty()){
			MessageInfo info = new MessageInfo();
			info.mMessage = "测试消息，请忽略！";
			info.mRecevTime = "2014-10-12 19:30";
			info.mIsRead="0";
			info.IsRead=true;
			mMessageList.add(info);
		}
		mMessageAdapter = new MessageAdapter(mContext, mMessageList);
		mListView.setAdapter(mMessageAdapter);
		mListView.setOnItemClickListener(this);
		getMessageList();
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
	
	public class MessageAdapter extends BaseAdapter{
		ArrayList<MessageInfo> mDataList;	
		Context mContext;
		public MessageAdapter(Context context, ArrayList<MessageInfo> list){
			mDataList = list;
			mContext = context;
		}
		@Override
		public int getCount() {
			return mDataList.size();
		}
		@Override
		public Object getItem(int position) {
			return mDataList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
				convertView = View.inflate(mContext, R.layout.list_item_message, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,MessageInfo info){
			
			view.findViewById(R.id.msg_list_item_icon).setVisibility(info.getIocnVisiable());
			((TextView)view.findViewById(R.id.msg_list_item_msg)).setText(info.Title);
			((TextView)view.findViewById(R.id.msg_list_item_time)).setText(info.CreateTime);

		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 100){
			getMessageList();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, MessageReadActivity.class);

		Bundle mBundle = new Bundle();
		mBundle.putSerializable("mMsg", mMessageList.get(arg2));
		forwardIntent.putExtras(mBundle);
		
		startActivityForResult(forwardIntent, 100);		
	}
	
	private void getMessageList(){
		ApiClient.getMessageList(mXsyUser.getUserId(), 1, Integer.MAX_VALUE,
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
										JSONArray array = response.getJSONArray("result");
										if(array!=null&&array.length()>1){
											 //mLaorenList = JSON.parseObject(response.getJSONObject("result").toString(), (ArrayList<LaorenInfo>).cl);
											 mMessageList = (ArrayList<MessageInfo>) JSON.parseArray(array.get(1).toString(), MessageInfo.class);
											 mMessageAdapter.mDataList = mMessageList;
											 mMessageAdapter.notifyDataSetChanged();
											// refresLastLaoren();
											// Log.d("onResponse",array.get(1).toString());
										}
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
