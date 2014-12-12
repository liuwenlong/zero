package com.mapgoo.zero.ui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.LaorenInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class LaorenActivity extends BaseActivity {

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
		
		if(mLaorenList.isEmpty()){
			LaorenInfo info = new LaorenInfo();
			info.mAdress="北京市西城区陶然亭";
			info.mXingbie="男";	
			info.mLeixing="正常";	
			info.mPhone="13712345678";	
			info.mShenfen="430123456789";	
			info.mName="张三";
			info.mOld="86";
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
		}
		mLaorenAdapter = new LaorenAdapter(mContext, mLaorenList);
		mListView.setAdapter(mLaorenAdapter);
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
			((TextView)view.findViewById(R.id.laoren_list_item_xinming)).setText(info.mName);
			((TextView)view.findViewById(R.id.laoren_list_item_xingbie)).setText(info.mXingbie);
			((TextView)view.findViewById(R.id.laoren_list_item_leixing)).setText(info.mLeixing);
			((TextView)view.findViewById(R.id.laoren_list_item_dizhi)).setText(info.mAdress);
			((TextView)view.findViewById(R.id.laoren_list_item_dianhua)).setText(info.mPhone);
			((TextView)view.findViewById(R.id.laoren_list_item_nianling)).setText(info.mOld);
			((TextView)view.findViewById(R.id.laoren_list_item_shenfenzheng)).setText(info.mShenfen);
		}
		
	}
}
