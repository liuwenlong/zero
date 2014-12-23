package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.j256.ormlite.stmt.query.In;
import com.huaan.icare.volunteer.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.DianpuInfo;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.ServiceOrderSubmitInfo;
import com.mapgoo.zero.bean.ShangpinInfo;
import com.mapgoo.zero.bean.VolunteerOrderSubmitInfo;
import com.mapgoo.zero.bean.ZhiyuanzheInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;
import com.mapgoo.zero.ui.widget.NumberCtrlView;
import com.mapgoo.zero.ui.widget.NumberCtrlView.onNumberChangeListener;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class OrderCreateActivity extends BaseActivity implements OnItemClickListener {

	private ListView mListView;
	private ArrayList<ShangpinInfo> mShangpinList ;
	private ArrayList<ZhiyuanzheInfo> mZhiyuanzheList = new ArrayList<ZhiyuanzheInfo>();
	private ShangpinAdapter mShanpinAdapter;
	private ZhiyuanzheAdapter mZhiyuanzheAdapter;
	private ZhiyuanzheInfo mZhiyuanzheInfo;
	private DianpuInfo mDianpuInfo;
	TextView mInputDate;
	TextView mInputTime;	
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_order_form_create);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mShangpinList = (ArrayList<ShangpinInfo>)savedInstanceState.getSerializable("mMsg");
			mDianpuInfo = (DianpuInfo)savedInstanceState.getSerializable("DianpuInfo");
			mZhiyuanzheInfo = (ZhiyuanzheInfo)savedInstanceState.getSerializable("Zhiyuanzhe");
		} else {
			mShangpinList = (ArrayList<ShangpinInfo>) getIntent().getSerializableExtra("mMsg");
			mDianpuInfo = (DianpuInfo)getIntent().getSerializableExtra("DianpuInfo");
			mZhiyuanzheInfo = (ZhiyuanzheInfo) getIntent().getSerializableExtra("Zhiyuanzhe");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("DianpuInfo", mDianpuInfo);
		outState.putSerializable("mMsg", mShangpinList);
		
		outState.putSerializable("Zhiyuanzhe", mZhiyuanzheInfo);
		
		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar("订单内容", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
		mInputDate = (TextView)findViewById(R.id.order_create_data_show);
		mInputTime = (TextView)findViewById(R.id.order_create_time_show);
		if(MainActivity.mLaorenInfo!=null)
		((TextView)findViewById(R.id.order_create_for_laoren)).setText(MainActivity.mLaorenInfo.getHumanName());
		
		
		ZhiyuanzheInit();
		shanpinInit();
	}
	void ZhiyuanzheInit(){
		if(mZhiyuanzheInfo!=null){
			mZhiyuanzheList.clear();
			mZhiyuanzheList.add(mZhiyuanzheInfo);
			mZhiyuanzheAdapter = new ZhiyuanzheAdapter(mContext, mZhiyuanzheList);
			mListView.setAdapter(mZhiyuanzheAdapter);
			mListView.setOnItemClickListener(this);
		}
	}
	void shanpinInit(){
		if(mShangpinList!=null){
			mShanpinAdapter = new ShangpinAdapter(mContext, mShangpinList);
			mListView.setAdapter(mShanpinAdapter);
			mListView.setOnItemClickListener(this);
		}	
	}

	private String getOrderTime(){
		String time ;
		time = mInputTime.getText().toString();
		time +=  " "+mInputDate.getText().toString();
		return time;
	}
	
	int getLaorenObjectId(){
		int id = 0;
		if(MainActivity.mLaorenInfo!=null)
			id = MainActivity.mLaorenInfo.ObjectID;
		return id;
	}
	
	
	String getServiceltem(){
		String str= "" ;
		ShangpinInfo info;
		for(int i=0;i<mShangpinList.size();i++){
			info = mShangpinList.get(i);
			if(i!=0)
				str =str+",";
			str =str+info.ProjectName+"_"+info.mNumber;
		}
		
		return str;
	}
	
	private void VolunteerOrderSubmit(){
		if(mZhiyuanzheInfo!=null){
			VolunteerOrderSubmitInfo info = new VolunteerOrderSubmitInfo();
			info.BusinessID = mZhiyuanzheInfo.getPeopleNo();
			info.HoldID =  mZhiyuanzheInfo.getHoldID();
			info.ObjectID = getLaorenObjectId();
			info.OrderTime = getOrderTime();
			info.PeopleNo = Integer.parseInt(mXsyUser.getpeopleNo(null));
			info.Remark = "null";
			info.OrderContent=mZhiyuanzheInfo.getServiceContent();
			info.UserID = mXsyUser.getUserId();
			ApiClient.postVolunteerOrderSubmit(info, new onReqStartListener(){
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
									OrderSubmitSucesse();
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
	
	private String getServiceFee(){
		float Num = 0.0f;	
		for(ShangpinInfo info:mShangpinList){
			Num += Float.parseFloat(info.Price)*info.mNumber;
		}
		
		return Num+"";
	}
	private void serviceOrderSubmit(){
		if(mShangpinList!=null){
			ServiceOrderSubmitInfo info = new ServiceOrderSubmitInfo();
			info.BusinessID = Integer.parseInt( mDianpuInfo.ServiceID);
			info.HoldID = Integer.parseInt( mDianpuInfo.HoldID);
			info.ObjectID = getLaorenObjectId();
			info.OrderTime = getOrderTime();
			info.PeopleNo = Integer.parseInt(mXsyUser.getpeopleNo(null));
			info.Remark = "null";
			info.ServiceFee=getServiceFee();
			info.Serviceltem=getServiceltem();
			info.UserID = mXsyUser.getUserId();
			
			
			ApiClient.postServiceOrderSubmit(info, new onReqStartListener(){
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
									OrderSubmitSucesse();
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
	
	private void OrderSubmitSucesse(){
		finish();
	}
	
	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		Log.d("onClick", "order create onClick");
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.order_create_data_click:	{
			Calendar c = Calendar.getInstance();
			Dialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
				public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
					mInputDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
				}
			}, c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
			dialog.show();
			break;	
			}
		case R.id.order_create_time_click:{
			Calendar c = Calendar.getInstance();
			Dialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					String miao = "00";
					String hour = "";
					if (hourOfDay < 10) {
						hour = "0" + hourOfDay;
					} else {
						hour = "" + hourOfDay;
					}
					String min = "";
					if (minute < 10) {
						min = "0" + minute;
					} else {
						min = "" + minute;
					}
					mInputTime.setText(hour + ":" + min);
				}
			}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
			dialog.show();
			break;
		}
		case R.id.shangpin_yuyue:
			serviceOrderSubmit();
			VolunteerOrderSubmit();
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
			inflateView(convertView,mDataList.get(position),position);
			return convertView;
		}
		
		void inflateView(View view,ShangpinInfo info,int position){
			NumberCtrlView v;
			((TextView)view.findViewById(R.id.order_form_order_name)).setText(info.ProjectName);
			((TextView)view.findViewById(R.id.order_form_unit_price)).setText(info.Price);
			v = ((NumberCtrlView)
					view.findViewById(R.id.order_form_number));
			v.setTag(position);
			v.setOnNumberChangeListener(new onNumberChangeListener() {
				public void onNumberChange(View v, int num) {
					int position = (int)v.getTag();
					mDataList.get(position).mNumber = num;
				}});
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
			((TextView)view.findViewById(R.id.zhiyuan_zhe_name)).setText(info.getPeopleName());
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_phone)).setText(info.getMobilePhone());
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_do)).setText(info.getServiceContent());
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_time)).setText(info.getServiceTime());
			(view.findViewById(R.id.zhiyuan_zhe_arrow)).setVisibility(View.INVISIBLE);
		}
	}	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}
}
