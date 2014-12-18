package com.mapgoo.zero.ui;

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
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.DianpuInfo;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.ZhiyuanzheInfo;
import com.mapgoo.zero.ui.XiaoxiActivity.MessageAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class DianpuActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<DianpuInfo> mDianpuList = new ArrayList<DianpuInfo>();
	private ArrayList<ZhiyuanzheInfo> mZhiyuanzheList = new ArrayList<ZhiyuanzheInfo>();
	private DianpuAdapter mDianpuAdapter;
	private ZhiyuanzheAdapter mZhiyuanzheAdapter;
	private int mFuwuType;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_laoren);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mFuwuType = getIntent().getIntExtra("fuwutype", -1);
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
		
		if(mFuwuType == YuyuefuwuActivity.YUYUE_FUWU_ZHIYUANZHE){
			zhiyuanzheInit();
		}else{
			dianpuInit();
			getFuwuInfoList();
		}
	}
	
	void dianpuInit(){
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
		mDianpuAdapter = new DianpuAdapter(mContext, mDianpuList);
		mListView.setAdapter(mDianpuAdapter);
		mListView.setOnItemClickListener(this);		
	}

	void zhiyuanzheInit(){
		if(mZhiyuanzheList.isEmpty()){
			ZhiyuanzheInfo info = new ZhiyuanzheInfo();
			info.mZhiyuanzheName="李四";
			info.mFuwuName="做饭";
			info.mXingbie="男";
			info.mFuwuTime="11:00-12:30";
			info.mPhone="87541236";
			mZhiyuanzheList.add(info);
			mZhiyuanzheList.add(info);
			mZhiyuanzheList.add(info);
			mZhiyuanzheList.add(info);
		}
		mZhiyuanzheAdapter = new ZhiyuanzheAdapter(mContext, mZhiyuanzheList);
		mListView.setAdapter(mZhiyuanzheAdapter);
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
			
			((TextView)view.findViewById(R.id.dianpu_item_from_name)).setText(info.CompanyName);
			((TextView)view.findViewById(R.id.dianpu_item_unit_price)).setText(info.Charges);
			((TextView)view.findViewById(R.id.dianpu_item_avail_time)).setText(info.ServiceTime);
			((TextView)view.findViewById(R.id.dianpu_item_pay_type)).setText(info.PayMent);
			((TextView)view.findViewById(R.id.dianpu_item_shang_men)).setText(info.ServiceType);
			((TextView)view.findViewById(R.id.dianpu_item_from_phone)).setText(info.Phone);
			((TextView)view.findViewById(R.id.dianpu_item_from_adress)).setText(info.Address);
		}
		
	}

	public class ZhiyuanzheAdapter extends BaseAdapter{
		ArrayList<ZhiyuanzheInfo> mDataList;	
		Context mContext;
		public ZhiyuanzheAdapter(Context context, ArrayList< ZhiyuanzheInfo> list){
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
				convertView = View.inflate(mContext, R.layout.list_item_zhiyuan_zhe, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,ZhiyuanzheInfo info){
			((TextView)view.findViewById(R.id.zhiyuan_zhe_name)).setText(info.mZhiyuanzheName);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_phone)).setText(info.mPhone);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_do)).setText(info.mFuwuName);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_time)).setText(info.mFuwuTime);
		}
		
	}	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(mFuwuType == YuyuefuwuActivity.YUYUE_FUWU_ZHIYUANZHE){
			Intent forwardIntent = new Intent();
			forwardIntent.setClass(mContext, OrderCreateActivity.class);

			Bundle mBundle = new Bundle();
			mBundle.putSerializable("Zhiyuanzhe", mZhiyuanzheList.get(arg2));
			forwardIntent.putExtras(mBundle);
			
			startActivity(forwardIntent);
		}else{
			Intent forwardIntent = new Intent();
			forwardIntent.setClass(mContext, ShangpinActivity.class);

			Bundle mBundle = new Bundle();
			mBundle.putSerializable("DianpuInfo", mDianpuList.get(arg2));
			forwardIntent.putExtras(mBundle);
			
			startActivity(forwardIntent);
		}
	}
	
	private void getFuwuInfoList(){
		ApiClient.getFuwuList(YuyuefuwuActivity.UrlType[mFuwuType]
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
											 mDianpuList = (ArrayList<DianpuInfo>) JSON.parseArray(array.get(1).toString(), DianpuInfo.class);
											 //refresLastLaoren();
											 mDianpuAdapter.mDataList = mDianpuList;
											 mDianpuAdapter.notifyDataSetChanged();
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
