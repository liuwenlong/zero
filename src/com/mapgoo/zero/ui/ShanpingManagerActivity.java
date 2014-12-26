package com.mapgoo.zero.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.mapgoo.zero.bean.FwsShangpinInfo;
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
public class ShanpingManagerActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener {

	private ListView mListView;
	private ArrayList<FwsShangpinInfo> mFwsShangpinList = new ArrayList<FwsShangpinInfo>();
	
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
	FwsShangpinAdapter mRenyuanAdapter;
	@Override
	public void initViews() {
		super.setupActionBar("商品管理", 2, R.drawable.ic_back_arrow_white,
				R.drawable.fws_shangpin_manager_add,R.drawable.home_actionbar_bgd, -1);	
		mListView = (ListView)findViewById(R.id.laoren_list);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
		mRenyuanAdapter = new FwsShangpinAdapter(mContext, mFwsShangpinList, R.layout.list_item_fws_shangpin_manager);
		mListView.setAdapter(mRenyuanAdapter);
		getProjectList();
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
			startActivityForResult(new Intent(mContext, EditShangpinActivity.class),100);
			break;
		default:
			break;
		}
	}
public class FwsShangpinAdapter extends CommonAdapter<FwsShangpinInfo>{

	public FwsShangpinAdapter(Context context, List<FwsShangpinInfo> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		// TODO Auto-generated constructor stub
	}
	public void setDataList( List<FwsShangpinInfo> mDatas){
		this.mDatas = mDatas;
		notifyDataSetChanged();
	}
	@Override
	public void convert(ViewHolder holder, FwsShangpinInfo item) {
		// TODO Auto-generated method stub
		((TextView)(holder.getConvertView().findViewById(R.id.fws_shangpin_name))).setText(item.ProjectName);
		((TextView)(holder.getConvertView().findViewById(R.id.fws_shangpin_time))).setText(item.Period);
		((TextView)(holder.getConvertView().findViewById(R.id.fws_shangpin_price))).setText(item.Price);
		((TextView)(holder.getConvertView().findViewById(R.id.fws_shangpin_remark))).setText(item.Remark);
		if(item.ImagePath != null){
			Log.d("onResponse","info.AvatarImage="+ item.ImagePath);
			MyVolley.getImageLoader().get(item.ImagePath, 
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
			getProjectList();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, EditShangpinActivity.class).putExtra("Renyuan", mFwsShangpinList.get(arg2));
		startActivityForResult(forwardIntent, 100);		
	}
	
	private void getProjectList(){
		ApiClient.getProjectBasic(mFwsUser.serviceId, 1, Integer.MAX_VALUE,
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
										//JSONArray array = response.getJSONArray("result");
										//if(array!=null&&array.length()>1){
											 //mLaorenList = JSON.parseObject(response.getJSONObject("result").toString(), (ArrayList<LaorenInfo>).cl);
										Log.d("response",response.getString("result"));
										//Log.d("response",response.getJSONObject("result").toString());
											mFwsShangpinList = (ArrayList<FwsShangpinInfo>) JSON.parseArray(response.getString("result"), FwsShangpinInfo.class);
											mRenyuanAdapter.setDataList(mFwsShangpinList);
											// refresLastLaoren();
											// Log.d("onResponse",array.get(1).toString());
										//}
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
	
	private void ProjectDelete(FwsShangpinInfo info){
		ApiClient.ProjectDelete(info,
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
										mToast.toastMsg("删除商品成功");
										getProjectList();
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
		final FwsShangpinInfo info =  mFwsShangpinList.get(arg2);
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
						ProjectDelete(info);
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
