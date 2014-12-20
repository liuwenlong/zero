package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.OrderFormDetailInfo;
import com.mapgoo.zero.bean.OrderFormInfo;
import com.mapgoo.zero.bean.OrderFormDetailInfo.OrderDetailInfo;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;


/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class OrderformDetailActivity extends BaseActivity {

	OrderFormInfo mOrderFormInfo;
	OrderFormDetailInfo mOrderFormDetailInfo;
	ListView mListView;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_orderform_detail);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mOrderFormInfo = (OrderFormInfo)getIntent().getExtras().getSerializable("OrderFormInfo");
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
		( (TextView)findViewById(R.id.order_form_detail_from_name) ).setText(mOrderFormInfo.mFromName);
		( (TextView)findViewById(R.id.order_form_detail_for_name) ).setText(mOrderFormInfo.mForName);

		( (TextView)findViewById(R.id.order_form_detail_unit_price) ).setText(mOrderFormInfo.mUnitPrice);
		//( (TextView)findViewById(R.id.order_form_detail_count) ).setText(mOrderFormInfo.mCount);
		( (TextView)findViewById(R.id.order_form_detail_appointment) ).setText(mOrderFormInfo.mAppointment);
		( (TextView)findViewById(R.id.order_form_detail_note) ).setText(mOrderFormInfo.mNote);
		
		
		getOrderformList();
	}
LinearLayout mProductLinear;
	private void refreshDisplay(OrderFormDetailInfo info){
		( (TextView)findViewById(R.id.order_form_detail_from_name) ).setText(info.BusinessName);
		( (TextView)findViewById(R.id.order_form_detail_for_name) ).setText(info.HumanName);
		( (TextView)findViewById(R.id.order_form_detail_appointment) ).setText(info.OrderTime);
		( (TextView)findViewById(R.id.order_form_detail_note) ).setText(info.Comment);
		( (TextView)findViewById(R.id.order_form_detail_order_status) ).setText(info.OrderStatus);
		
		mProductLinear = (LinearLayout)findViewById(R.id.order_form_detail_name_num) ;
		mProductLinear.removeAllViews();
		if(info.OrderDetails!=null &&info.OrderDetails.size()>0){
			for(OrderDetailInfo deinfo:info.OrderDetails){
				View view = View.inflate(mContext, R.layout.list_item_orderform_details, null);
				( (TextView)view.findViewById(R.id.order_form_detail_count) ).setText(deinfo.ProductNumber);
				( (TextView)view.findViewById(R.id.order_form_detail_order_name) ).setText(deinfo.ProductName);
				mProductLinear.addView(view);
			}
		}
		
		if(info.OrderStatusID == 1){
			( (TextView)findViewById(R.id.order_form_detail_btn) ).setText(R.string.order_form_detail_Cancel);
		}else if(info.OrderStatusID == 2){
			( (TextView)findViewById(R.id.order_form_detail_btn) ).setText(R.string.order_form_detail_Confirm);
		}else{
			( (TextView)findViewById(R.id.order_form_detail_btn) ).setVisibility(View.INVISIBLE);
		}
		
		if(info.BusinessType == 1){
			((TextView)findViewById(R.id.order_form_detail_zhiyuanzhe_fuwu)).setText(info.OrderContent);
			
			findViewById(R.id.order_form_detail_zhiyuanzhe_fuwu_item).setVisibility(View.VISIBLE);
			findViewById(R.id.order_form_detail_unit_price_item) .setVisibility(View.GONE);
		}else{
			((TextView)findViewById(R.id.order_form_detail_unit_price) ).setText(info.ServiceFee);
		}
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
		case R.id.order_form_detail_btn:{
			if(mOrderFormDetailInfo!=null && mOrderFormDetailInfo.OrderStatusID == 2){//确认完成
				Intent intent = new Intent(mContext, PingjiaActivity.class).putExtra("OrderFormInfo", mOrderFormInfo);
				startActivity(intent);
			}else if(mOrderFormDetailInfo!=null && mOrderFormDetailInfo.OrderStatusID == 1){//取消订单
				
			}
			break;
		}
		default:
			break;
		}
	}

	private void getOrderformList(){
		ApiClient.getOrderFormDetailList(mOrderFormInfo.RecID,1, Integer.MAX_VALUE,
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
										mOrderFormDetailInfo = JSON.parseObject(response.getJSONObject("result").toString(), OrderFormDetailInfo.class);
										//mOrderFormDetailInfo.OrderDetails= (ArrayList<OrderDetailInfo>) JSON.parseArray(mOrderFormDetailInfo.OrderDetails.toString(), OrderDetailInfo.class);
											 //mOrderFormAdapter.mDataList = mOrderFormList;
											 //mOrderFormAdapter.notifyDataSetChanged();
										//}
										 
										refreshDisplay(mOrderFormDetailInfo);
										
										//Log.d("onResponse",mOrderFormDetailInfo.OrderDetails);
									}else{
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}}},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));		
	}
	
	class Adapater extends CommonAdapter<OrderDetailInfo>{

		public Adapater(Context context, List mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder holder, OrderDetailInfo item) {
			// TODO Auto-generated method stub
//			((TextView)holder.getConvertView().findViewById(R.id.order_list_item_name)).setText(item.ProductName);
//			((TextView)holder.getConvertView().findViewById(R.id.order_list_item_count)).setText(item.ProductNumber);
		}
		
	}
}
