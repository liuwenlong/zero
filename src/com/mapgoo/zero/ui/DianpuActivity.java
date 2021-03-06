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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.huaan.icare.xsy.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
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
			mFuwuType = savedInstanceState.getInt("fuwutype");
		} else {
			mFuwuType = getIntent().getIntExtra("fuwutype", -1);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("fuwutype", mFuwuType);
		super.onSaveInstanceState(outState);
	}

	private String getMyTitle(){
		String str = null;
		switch (mFuwuType) {
			case YuyuefuwuActivity.YUYUE_FUWU_JIAZHENG:str = getString(R.string.yuyue_jiazheng);break;
			case YuyuefuwuActivity.YUYUE_FUWU_YILIAO:str = getString(R.string.yuyue_yiliao);break;
			case YuyuefuwuActivity.YUYUE_FUWU_XIYU:str = getString(R.string.yuyue_xiyu);break;
			
			case YuyuefuwuActivity.YUYUE_FUWU_LIHUA:str = getString(R.string.yuyue_lihua);break;
			case YuyuefuwuActivity.YUYUE_FUWU_CANYING:str = getString(R.string.yuyue_canying);break;
			case YuyuefuwuActivity.YUYUE_FUWU_CHAOSHI:str = getString(R.string.yuyue_chaoshi);break;
			
			case YuyuefuwuActivity.YUYUE_FUWU_SHUCAI:str = getString(R.string.yuyue_shucai);break;
			case YuyuefuwuActivity.YUYUE_FUWU_JIANSHENG: str = getString(R.string.yuyue_jiansheng);break;
			case YuyuefuwuActivity.YUYUE_FUWU_ZHIYUANZHE:str = getString(R.string.yuyue_zhiyuan);break;
		default:
			break;
		}
		return str;
	}
	
	@Override
	public void initViews() {
		super.setupActionBar(getMyTitle(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mListView = (ListView)findViewById(R.id.laoren_list);
		
		if(mFuwuType == YuyuefuwuActivity.YUYUE_FUWU_ZHIYUANZHE){
			zhiyuanzheInit();
			getZhiyuanzhe();
		}else{
			dianpuInit();
			getFuwuInfoList();
		}
	}
	
	void dianpuInit(){
		mDianpuAdapter = new DianpuAdapter(mContext, mDianpuList);
		mListView.setAdapter(mDianpuAdapter);
		mListView.setOnItemClickListener(this);

		mListView.setEmptyView(findViewById(R.id.empty_text));
		findViewById(R.id.empty_text).setOnClickListener(this);
	}

	void zhiyuanzheInit(){
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
		case R.id.empty_text:
			if(mFuwuType == YuyuefuwuActivity.YUYUE_FUWU_ZHIYUANZHE){
				getZhiyuanzhe();
			}else{
				getFuwuInfoList();
			}
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
			((TextView)view.findViewById(R.id.dianpu_item_shang_men)).setText(info.getShangmen());
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
			((TextView)view.findViewById(R.id.zhiyuan_zhe_name)).setText(info.PeopleName);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_phone)).setText(info.MobilePhone);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_do)).setText(info.ServiceContent);
			((TextView)view.findViewById(R.id.zhiyuan_zhe_work_time)).setText(info.ServiceTime);

			if(info.Picture != null){
				Log.d("onResponse","info.AvatarImage="+ info.Picture);
				MyVolley.getImageLoader().get(info.Picture, 
						ImageLoader.getImageListener((ImageView) view.findViewById(R.id.zhiyuan_zhe_avater), 
								R.drawable.list_item_zhiyuan_zhe_icon, R.drawable.list_item_zhiyuan_zhe_icon));
			}else{
				((ImageView) view.findViewById(R.id.zhiyuan_zhe_avater)).setImageResource(R.drawable.list_item_zhiyuan_zhe_icon);
			}
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
	
	private void getZhiyuanzhe(){
		ApiClient.getZhiyuanzheList(1, Integer.MAX_VALUE,
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
											 mZhiyuanzheList = (ArrayList<ZhiyuanzheInfo>) JSON.parseArray(array.get(1).toString(), ZhiyuanzheInfo.class);
											 //refresLastLaoren();
											 mZhiyuanzheAdapter.mDataList = mZhiyuanzheList;
											 mZhiyuanzheAdapter.notifyDataSetChanged();
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
