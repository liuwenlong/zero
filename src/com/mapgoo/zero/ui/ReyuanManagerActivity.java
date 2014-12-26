package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.RequestUtils;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.RenyuanInfo;
import com.mapgoo.zero.ui.LaorenActivity.LaorenAdapter;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class ReyuanManagerActivity extends BaseActivity implements OnItemClickListener,  OnItemLongClickListener {

	private ListView mListView;
	private ArrayList<RenyuanInfo> mRenyuanList = new ArrayList<RenyuanInfo>();
	
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
	RenyuanAdapter mRenyuanAdapter;
	@Override
	public void initViews() {
		super.setupActionBar("人员管理", 2, R.drawable.ic_back_arrow_white,
				R.drawable.fws_shangpin_manager_add,R.drawable.home_actionbar_bgd, -1);	
		mListView = (ListView)findViewById(R.id.laoren_list);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mRenyuanAdapter = new RenyuanAdapter(mContext, mRenyuanList, R.layout.list_item_renyuan_manager);
		mListView.setAdapter(mRenyuanAdapter);
		getRenyuanList();
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
		case R.id.iv_ab_right_btn:
			startActivityForResult(new Intent(mContext, EditRenyuanActivity.class),100);
			break;
		default:
			break;
		}
	}
public class RenyuanAdapter extends CommonAdapter<RenyuanInfo>{

	public RenyuanAdapter(Context context, List<RenyuanInfo> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		// TODO Auto-generated constructor stub
	}
	public void setDataList( List<RenyuanInfo> mDatas){
		this.mDatas = mDatas;
		notifyDataSetChanged();
	}
	@Override
	public void convert(ViewHolder holder, RenyuanInfo item) {
		// TODO Auto-generated method stub
		((TextView)(holder.getConvertView().findViewById(R.id.fws_renyuan_name))).setText(item.PeopleName);
		((TextView)(holder.getConvertView().findViewById(R.id.fws_renyuan_sex))).setText(item.getPeopleSexString());
		((TextView)(holder.getConvertView().findViewById(R.id.fws_renyuan_birthday))).setText(item.Birthday);
		((TextView)(holder.getConvertView().findViewById(R.id.fws_renyuan_idcard))).setText(item.IDCard);
		if(item.Picture != null){
			Log.d("onResponse","info.AvatarImage="+ item.Picture);
			MyVolley.getImageLoader().get(item.Picture, 
					ImageLoader.getImageListener((ImageView)(holder.getConvertView().findViewById(R.id.fws_renyuan_manager_def_pic)), 
							R.drawable.list_item_zhiyuan_zhe_icon, R.drawable.list_item_zhiyuan_zhe_icon));
		}
	}
	
}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 100 && resultCode == RESULT_OK){
			getRenyuanList();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, EditRenyuanActivity.class).putExtra("Renyuan", mRenyuanList.get(arg2));
		startActivityForResult(forwardIntent, 100);		
	}
	
	private void getRenyuanList(){
		ApiClient.getPeopleBasic(mFwsUser.serviceId, 1, Integer.MAX_VALUE,
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
											mRenyuanList = (ArrayList<RenyuanInfo>) JSON.parseArray(array.get(1).toString(), RenyuanInfo.class);
											mRenyuanAdapter.setDataList(mRenyuanList);
											// refresLastLaoren();
											// Log.d("onResponse",array.get(1).toString());
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
	
	private void PeopleDelete(RenyuanInfo info){
		ApiClient.PeopleDelete(info,
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
										mToast.toastMsg("删除成功");
										getRenyuanList();
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

	private void deleteItem( int arg2){
		final RenyuanInfo info =  mRenyuanList.get(arg2);
		new AlertDialog.Builder(mContext).setTitle("提示")
				.setMessage("是否删除选中项?")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						PeopleDelete(info);
					}
				}).show();	
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		deleteItem(arg2);
		return true;
	}

}
