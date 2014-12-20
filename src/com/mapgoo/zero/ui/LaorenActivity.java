package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class LaorenActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<LaorenInfo> mLaorenList = new ArrayList<LaorenInfo>();
	private LaorenAdapter mLaorenAdapter;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_laoren);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mLaorenList = (ArrayList<LaorenInfo>)getIntent().getExtras().getSerializable("laoren");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.home_laoren).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		mLaorenAdapter = new LaorenAdapter(mContext, mLaorenList);
		mListView.setAdapter(mLaorenAdapter);
		mListView.setOnItemClickListener(this);
		getLoaorenInfo();
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


	
	public class LaorenAdapter extends BaseAdapter{
		ArrayList<LaorenInfo> mDataList;	
		Context mContext;
		public LaorenAdapter(Context context, ArrayList<LaorenInfo> list){
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
				convertView = View.inflate(mContext, R.layout.list_item_laoren, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,LaorenInfo info){
			((TextView)view.findViewById(R.id.laoren_list_item_xinming)).setText(info.HumanName);
			((TextView)view.findViewById(R.id.laoren_list_item_xingbie)).setText(info.getSexString());
			((TextView)view.findViewById(R.id.laoren_list_item_leixing)).setText(info.HumanType);
			((TextView)view.findViewById(R.id.laoren_list_item_dizhi)).setText(info.Address);
			((TextView)view.findViewById(R.id.laoren_list_item_dianhua)).setText(info.AlldayTel);
			((TextView)view.findViewById(R.id.laoren_list_item_nianling)).setText(info.Birthday);
			((TextView)view.findViewById(R.id.laoren_list_item_shenfenzheng)).setText(info.IDCardNo);
			if(info.AvatarImage != null){
				Log.d("onResponse","info.AvatarImage="+ info.AvatarImage);
				MyVolley.getImageLoader().get(info.AvatarImage, 
						ImageLoader.getImageListener((ImageView) view.findViewById(R.id.ruhu_laoren_touxiang), 
								R.drawable.ic_avatar_holder, R.drawable.ic_avatar_holder));
			}else{
				Log.d("onResponse","info.AvatarImage is null.");
				((ImageView) view.findViewById(R.id.ruhu_laoren_touxiang)).setImageResource(R.drawable.ic_avatar_holder);
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("select", mLaorenList.get(arg2));
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void getLoaorenInfo(){
		ApiClient.getLoarenInfoList(mXsyUser.peopleNo, 1, 10,
				new onReqStartListener(){
					public void onReqStart() {
						getmProgressDialog().show();
					}}, 
					new Listener<JSONObject> (){
						public void onResponse(JSONObject response) {
							getmProgressDialog().dismiss();
							
							if (response.has("error")) {
								try {
									if (response.getInt("error") == 0) {
										JSONArray array = response.getJSONArray("result");
										if(array!=null&&array.length()>1){
											 //mLaorenList = JSON.parseObject(response.getJSONObject("result").toString(), (ArrayList<LaorenInfo>).cl);
											 mLaorenList = (ArrayList<LaorenInfo>) JSON.parseArray(array.get(1).toString(), LaorenInfo.class);
											 //refresLastLaoren();
											 mLaorenAdapter.mDataList = mLaorenList;
											 mLaorenAdapter.notifyDataSetChanged();
											 Log.d("onResponse",array.get(1).toString());
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
