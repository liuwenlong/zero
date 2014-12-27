package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.FwsOrderinfo;
import com.mapgoo.zero.bean.FwsOrderinfo.OrderDetailInfo;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;


/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class OrderformDetailActivity extends BaseActivity {

	FwsOrderinfo mFwsOrderinfo;
	ListView mListView;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_orderform_detail);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mFwsOrderinfo = (FwsOrderinfo)getIntent().getExtras().getSerializable("FwsOrderinfo");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.order_form_detail).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		refreshDisplay();

	}
LinearLayout mProductLinear;
	private void refreshDisplay(){
		( (TextView)findViewById(R.id.order_form_detail_from_name) ).setText("订单号 "+mFwsOrderinfo.OrderCode);
		( (TextView)findViewById(R.id.order_form_detail_for_name) ).setText(mFwsOrderinfo.HumanName);
		( (TextView)findViewById(R.id.order_form_detail_unit_price) ).setText(mFwsOrderinfo.HumanAddress);
		( (TextView)findViewById(R.id.order_form_detail_appointment) ).setText(mFwsOrderinfo.OrderTime);
		( (TextView)findViewById(R.id.order_form_detail_note) ).setText(mFwsOrderinfo.Remark);		
		( (TextView)findViewById(R.id.ruhu_dianhua_lianxiren) ).setText(mFwsOrderinfo.MobilePhone);		
		( (TextView)findViewById(R.id.order_form_detail_order_status) ).setText(mFwsOrderinfo.OrderStatus);		
		
		if(mFwsOrderinfo.OrderStatusID == 1){
			findViewById(R.id.order_form_commit).setVisibility(View.VISIBLE);
		}else{
			findViewById(R.id.order_form_commit).setVisibility(View.INVISIBLE);
		}
		
		findViewById(R.id.order_form_deceline_reason_item) .setVisibility(View.GONE);
		findViewById(R.id.order_form_detail_pingjia_item) .setVisibility(View.GONE);
		if(mFwsOrderinfo.OrderStatusID == 4){
			findViewById(R.id.order_form_deceline_reason_item) .setVisibility(View.VISIBLE);
			( (TextView)findViewById(R.id.order_form_deceline_reason) ).setText(mFwsOrderinfo.DeclineReason);
		}
		if(mFwsOrderinfo.OrderStatusID == 3){
			findViewById(R.id.order_form_detail_pingjia_item) .setVisibility(View.VISIBLE);
			( (TextView)findViewById(R.id.order_form_detail_pingjia) ).setText(mFwsOrderinfo.Comment);
		}
		
		mProductLinear = (LinearLayout)findViewById(R.id.order_form_detail_name_num) ;
		mProductLinear.removeAllViews();
		if(mFwsOrderinfo.OrderDetails!=null &&mFwsOrderinfo.OrderDetails.size()>0){
			for(OrderDetailInfo deinfo:mFwsOrderinfo.OrderDetails){
				View view = View.inflate(mContext, R.layout.list_item_orderform_details, null);
				( (TextView)view.findViewById(R.id.order_form_detail_count) ).setText(deinfo.ProductNumber);
				( (TextView)view.findViewById(R.id.order_form_detail_order_name) ).setText(deinfo.ProductName);
				mProductLinear.addView(view);
			}
		}
	}

	@Override
	public void handleData() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 100 && resultCode == RESULT_OK){
			mFwsOrderinfo.OrderStatus = "拒受理";
			mFwsOrderinfo.OrderStatusID = 4;
			mFwsOrderinfo.DeclineReason = "";
			refreshDisplay();
			setResult(RESULT_OK);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.order_form_jieshou_btn:
			OrderAccept();
			break;	
		case R.id.order_form_jujue_btn:
			startActivityForResult(new Intent(mContext, DeclineReasonActivity.class).putExtra("FwsOrderinfo",mFwsOrderinfo), 100);
			break;	
		case R.id.order_dianhua_lianxiren:
			callPhone(mFwsOrderinfo.MobilePhone);
			break;
		default:
			break;
		}
	}
	private void callPhone(String str){
		if(str!=null && !str.isEmpty()){
			Uri uri = Uri.parse("tel:"+str);   
			Intent it = new Intent(Intent.ACTION_DIAL, uri);     
			startActivity(it); 
		}
	}
	
	private void OrderAccept(){
		ApiClient.OrderAccept(mFwsOrderinfo.RecID,
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
										mToast.toastMsg("受理成功");
										mFwsOrderinfo.OrderStatus = "已受理";
										mFwsOrderinfo.OrderStatusID = 2;
										refreshDisplay();
										setResult(RESULT_OK);
									//	getOrderformList();
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
