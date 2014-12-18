package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

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

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.ShangpinInfo;
import com.mapgoo.zero.bean.ZhiyuanzheInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;

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
	TextView mInputDate;
	TextView mInputTime;	
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_order_form_create);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mShangpinList = (ArrayList<ShangpinInfo>) getIntent().getSerializableExtra("mMsg");
			mZhiyuanzheInfo = (ZhiyuanzheInfo) getIntent().getSerializableExtra("Zhiyuanzhe");
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
		
		mInputDate = (TextView)findViewById(R.id.order_create_data_show);
		mInputTime = (TextView)findViewById(R.id.order_create_time_show);
		
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
			(view.findViewById(R.id.zhiyuan_zhe_arrow)).setVisibility(View.INVISIBLE);
		}
		
	}	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
//		Intent forwardIntent = new Intent();
//		forwardIntent.setClass(mContext, MessageReadActivity.class);
//
//		Bundle mBundle = new Bundle();
//		mBundle.putSerializable("mMsg", mShangpinList.get(arg2));
//		forwardIntent.putExtras(mBundle);
		
		//startActivity(forwardIntent);		
	}
}
