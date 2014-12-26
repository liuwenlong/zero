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
import com.huaan.icare.family.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.OrderFormInfo;
import com.mapgoo.zero.bean.ZhiyuanzheInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class WodedingdanActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<OrderFormInfo> mOrderFormList = new ArrayList<OrderFormInfo>();
	private OrderFormAdapter mOrderFormAdapter;
	
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
		super.setupActionBar("我的订单", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
//		if(mOrderFormList.isEmpty()){
//			OrderFormInfo info = new OrderFormInfo();
//			info.mForName="张三";
//			info.mFromName="飘香餐馆";
//			info.mOrderName="鱼";
//			info.mOrderStatus="已接受";
//			info.mUnitPrice="200元";
//			info.mCount="3";
//			info.mOrderTime="2014-10-13 18:30";
//			info.mAppointment="2014-10-19 15:30";
//			
//			mOrderFormList.add(info);
//			mOrderFormList.add(info);
//			mOrderFormList.add(info);
//			mOrderFormList.add(info);
//			mOrderFormList.add(info);
//		}
		mOrderFormAdapter = new OrderFormAdapter(mContext, mOrderFormList);
		mListView.setAdapter(mOrderFormAdapter);
		mListView.setOnItemClickListener(this);
		getOrderformList();
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
	
	public class OrderFormAdapter extends BaseAdapter{
		ArrayList<OrderFormInfo> mDataList;	
		Context mContext;
		public OrderFormAdapter(Context context, ArrayList<OrderFormInfo> list){
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
				convertView = View.inflate(mContext, R.layout.list_item_orderform, null);
			inflateView(convertView,mDataList.get(position));
			return convertView;
		}
		
		void inflateView(View view,OrderFormInfo info){
			((TextView)view.findViewById(R.id.order_list_item_title)).setText(info.getOrderTitle());
			((TextView)view.findViewById(R.id.order_list_item_status)).setText(info.OrderStatus);
			((TextView)view.findViewById(R.id.order_list_item_time)).setText(info.getOrderTime());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100 && resultCode == RESULT_OK){
			getOrderformList();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, OrderformDetailActivity.class);
		forwardIntent.putExtra("OrderFormInfo", mOrderFormList.get(arg2));
		
		startActivityForResult(forwardIntent, 100);	
	}

	private void getOrderformList(){
		ApiClient.getOrderFormList(mXsyUser.peopleNo,1, Integer.MAX_VALUE,
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
											 mOrderFormList = (ArrayList<OrderFormInfo>) JSON.parseArray(array.get(1).toString(), OrderFormInfo.class);
											 mOrderFormAdapter.mDataList = mOrderFormList;
											 mOrderFormAdapter.notifyDataSetChanged();
										}
									}else{
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}}},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));		
	}
	
}
