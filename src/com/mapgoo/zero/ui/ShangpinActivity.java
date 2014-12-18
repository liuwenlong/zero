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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.DianpuInfo;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.ShangpinInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class ShangpinActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<ShangpinInfo> mShangpinList = new ArrayList<ShangpinInfo>();
	private ShangpinAdapter mShanpinAdapter;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_shangpin);
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
		super.setupActionBar(getText(R.string.home_laoren).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
		if(mShangpinList.isEmpty()){
			ShangpinInfo info;
			
			info = new ShangpinInfo();
			info.mFromName = "测试消息，请忽略！";
			info.mShangpinName = "麻婆豆腐";
			info.mUnitPrice="81元/份";
			info.mFuwuTime="15:30-20:30";
			info.mNote="无";
			
			mShangpinList.add(info);
			
			info = new ShangpinInfo();
			info.mFromName = "测试消息，请忽略！";
			info.mShangpinName = "麻婆豆腐";
			info.mUnitPrice="21元/份";
			info.mFuwuTime="15:30-20:30";
			info.mNote="无";
			
			mShangpinList.add(info);
		}
		mShanpinAdapter = new ShangpinAdapter(mContext, mShangpinList);
		mListView.setAdapter(mShanpinAdapter);
		mListView.setOnItemClickListener(this);
		getShangpinInfoList();
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
			Intent forwardIntent = new Intent();
			forwardIntent.setClass(mContext, OrderCreateActivity.class);
			forwardIntent.putExtra("mMsg", getSelectShangpin());
			startActivity(forwardIntent);	
			break;
		default:
			break;
		}
	}
	
	private ArrayList<ShangpinInfo> getSelectShangpin(){
		ArrayList<ShangpinInfo> arraylist = new ArrayList<ShangpinInfo>();
		for(ShangpinInfo info:mShangpinList){
			if(info.isCheck){
				arraylist.add(info);
			}
		}
		return arraylist;
	}
	
	public class ShangpinAdapter extends BaseAdapter{
		ArrayList<ShangpinInfo> mDataList;	
		Context mContext;
		public ShangpinAdapter(Context context, ArrayList<ShangpinInfo> list){
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
				convertView = View.inflate(mContext, R.layout.list_item_shangpin, null);
			inflateView(convertView,mDataList.get(position),position);
			return convertView;
		}
		
		void inflateView(View view,ShangpinInfo info,int position){
			CheckBox  box = (CheckBox)view.findViewById(R.id.shangpin_checkbox);
			
			((TextView)view.findViewById(R.id.shangpin_name)).setText(info.mShangpinName);
			((TextView)view.findViewById(R.id.shangpin_unit_price)).setText(info.mUnitPrice);
			((TextView)view.findViewById(R.id.shangpin_fuwu_time)).setText(info.mFuwuTime);
			((TextView)view.findViewById(R.id.shangping_note)).setText(info.mNote);
			
			box.setTag(position);
			box.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					Object tag = buttonView.getTag();
					if(tag instanceof Integer){
						int index  = (Integer)tag;
						mDataList.get(index).isCheck = isChecked;
						Log.d("ischecked", "index = "+index);
					}
				}});
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
	}
	
	private void getShangpinInfoList(){
		ApiClient.getShangpinList("8"
				, 1, Integer.MAX_VALUE,
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
											 //mDianpuList = (ArrayList<DianpuInfo>) JSON.parseArray(array.get(1).toString(), DianpuInfo.class);
											 //refresLastLaoren();
											 //mDianpuAdapter.mDataList = mDianpuList;
											 //mDianpuAdapter.notifyDataSetChanged();
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
