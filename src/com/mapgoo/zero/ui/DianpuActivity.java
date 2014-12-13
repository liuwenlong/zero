package com.mapgoo.zero.ui;

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
import com.mapgoo.zero.bean.DianpuInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.ui.XiaoxiActivity.MessageAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class DianpuActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<DianpuInfo> mDianpuList = new ArrayList<DianpuInfo>();
	private DianpuAdapter mMessageAdapter;
	
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
		super.setupActionBar(getText(R.string.home_laoren).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
		if(mDianpuList.isEmpty()){
			DianpuInfo info = new DianpuInfo();
			info.mFromName="飘香餐馆";
			info.mFuwuTime = "8:30-19:30";
			info.mUnitPrice="200元";
			info.mPayType="刷卡";
			info.mPhone="78455454";
			info.mAdress="北京西城区椿树街道102号";
			info.mShanMen="是";
			mDianpuList.add(info);
			mDianpuList.add(info);
			mDianpuList.add(info);
			mDianpuList.add(info);
			mDianpuList.add(info);
		}
		mMessageAdapter = new DianpuAdapter(mContext, mDianpuList);
		mListView.setAdapter(mMessageAdapter);
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
	
	public class DianpuAdapter extends BaseAdapter{
		ArrayList<DianpuInfo> mDataList;	
		Context mContext;
		public DianpuAdapter(Context context, ArrayList< DianpuInfo> list){
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
				convertView = View.inflate(mContext, R.layout.list_item_dianpu, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,DianpuInfo info){
			
			((TextView)view.findViewById(R.id.dianpu_item_from_name)).setText(info.mFromName);
			((TextView)view.findViewById(R.id.dianpu_item_unit_price)).setText(info.mUnitPrice);
			((TextView)view.findViewById(R.id.dianpu_item_avail_time)).setText(info.mFuwuTime);
			((TextView)view.findViewById(R.id.dianpu_item_pay_type)).setText(info.mPayType);
			((TextView)view.findViewById(R.id.dianpu_item_shang_men)).setText(info.mShanMen);
			((TextView)view.findViewById(R.id.dianpu_item_from_phone)).setText(info.mPhone);
			((TextView)view.findViewById(R.id.dianpu_item_from_adress)).setText(info.mAdress);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, ShangpinActivity.class);

		Bundle mBundle = new Bundle();
		mBundle.putSerializable("DianpuInfo", mDianpuList.get(arg2));
		forwardIntent.putExtras(mBundle);
		
		startActivity(forwardIntent);
	}
}
