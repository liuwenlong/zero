package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.huaan.icare.family.R;
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
	private ArrayList<LaorenInfo> mSearLaorenList = new ArrayList<LaorenInfo>();
	private LaorenAdapter mLaorenAdapter;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_laoren);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mLaorenList = (ArrayList<LaorenInfo>)savedInstanceState.getSerializable("laoren");
		} else {
			mLaorenList = (ArrayList<LaorenInfo>)getIntent().getExtras().getSerializable("laoren");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("laoren", mLaorenList);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.laoren_list).toString(), 2, R.drawable.ic_back_arrow_white, R.drawable.laoren_search,
				R.drawable.home_actionbar_bgd, -1);

		mListView = (ListView)findViewById(R.id.laoren_list);
		mLaorenAdapter = new LaorenAdapter(mContext, mLaorenList);
		mListView.setAdapter(mLaorenAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setEmptyView(findViewById(R.id.empty_text));
		getLoaorenInfo();
	}

	@Override
	public void handleData() {

	}

	private void SearchLaorenbyName(String queryText){
		for(LaorenInfo info:mLaorenList){
			if(info.HumanName!=null)
				if(info.HumanName.indexOf(queryText)>=0)
					mSearLaorenList.add(info);
		}
		mLaorenAdapter.mDataList = mSearLaorenList;
		mLaorenAdapter.notifyDataSetChanged();
	}
	
	private void toggleSearch(){
		if(tv_search_edit.getVisibility() == View.GONE){
			super.setupActionBar(getText(R.string.home_laoren).toString(), 5, R.drawable.ic_back_arrow_white, R.drawable.laoren_search_dis,
					R.drawable.home_actionbar_bgd, -1); 
			tv_search_edit.clearFocus();
			
			tv_search_edit.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					// TODO Auto-generated method stub
					Log.d("onKey", "keyCode="+keyCode);
					if(event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER){
						SearchLaorenbyName(tv_search_edit.getText().toString());
		                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		                    if (imm != null) {  
		                        imm.hideSoftInputFromWindow(  
		                        		tv_search_edit.getWindowToken(), 0);  
		                    }  
		                    tv_search_edit.clearFocus();  
					}
					return true;
				}
			});
			
			/*			
			tv_search_edit.setOnQueryTextListener(new OnQueryTextListener() {  
	            @Override  
	            public boolean onQueryTextChange(String queryText) {  
	            	SearchLaorenbyName(queryText);
	                return true;  
	            } 
	  
	            @Override  
	            public boolean onQueryTextSubmit(String queryText) {  
	                if (tv_search_edit != null) {  
	                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	                    if (imm != null) {  
	                        imm.hideSoftInputFromWindow(  
	                        		tv_search_edit.getWindowToken(), 0);  
	                    }  
	                    tv_search_edit.clearFocus();  
	                }  
	                return true;  
	            }  
	        });  */
		}else{
			super.setupActionBar(getText(R.string.laoren_list).toString(), 2, R.drawable.ic_back_arrow_white, R.drawable.laoren_search,
					R.drawable.home_actionbar_bgd, -1);
			 mLaorenAdapter.mDataList = mLaorenList;
			 mLaorenAdapter.notifyDataSetChanged();			
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.iv_ab_right_btn:
			toggleSearch();
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
			if(info.HasSOSMDT)
				view.findViewById(R.id.laoren_list_item_shebei).setVisibility(View.INVISIBLE);
			else
				view.findViewById(R.id.laoren_list_item_shebei).setVisibility(View.VISIBLE);
			
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
		ApiClient.getLoarenInfoList(mXsyUser.peopleNo, 1, Integer.MAX_VALUE,
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
