package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.ShangpinInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class OrderCreateActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<ShangpinInfo> mShangpinList = new ArrayList<ShangpinInfo>();
	private ShangpinAdapter mShanpinAdapter;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_order_form_create);
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
			ShangpinInfo info = new ShangpinInfo();
			info.mFromName = "测试消息，请忽略！";
			info.mShangpinName = "麻婆豆腐";
			info.mUnitPrice="80元/份";
			info.mFuwuTime="15:30-20:30";
			info.mNote="无";
			mShangpinList.add(info);
			mShangpinList.add(info);
			mShangpinList.add(info);
			mShangpinList.add(info);
			mShangpinList.add(info);
		}
		mShanpinAdapter = new ShangpinAdapter(mContext, mShangpinList);
		mListView.setAdapter(mShanpinAdapter);
		mListView.setOnItemClickListener(this);
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
				convertView = View.inflate(mContext, R.layout.list_item_order_create, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,ShangpinInfo info){
			
			((TextView)view.findViewById(R.id.order_form_order_name)).setText(info.mShangpinName);
			((TextView)view.findViewById(R.id.order_form_unit_price)).setText(info.mUnitPrice);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, MessageReadActivity.class);

		Bundle mBundle = new Bundle();
		mBundle.putSerializable("mMsg", mShangpinList.get(arg2));
		forwardIntent.putExtras(mBundle);
		
		//startActivity(forwardIntent);		
	}
}
