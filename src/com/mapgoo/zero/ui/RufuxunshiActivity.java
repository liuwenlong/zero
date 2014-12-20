package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.OrderFormInfo;
import com.mapgoo.zero.bean.PatrolBasicInfo;
import com.mapgoo.zero.ui.widget.RuhuPagerAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class RufuxunshiActivity extends BaseActivity implements OnCheckedChangeListener {

	private ViewPager mViewPager;
	private View mViewPager0;
	private View mViewPager1;
	private View mViewPager2;
	private PatrolBasicInfo mPatrolBasicInfo= new PatrolBasicInfo();
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_ruhuxunshi);
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
		ArrayList<View> pageViews = new ArrayList<View>();
		
		super.setupActionBar(getText(R.string.home_rufu).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mViewPager = (ViewPager)findViewById(R.id.ruhu_vPager);
		
		mViewPager0 = View.inflate(this, R.layout.layout_ruhu_pager_0, null);
		mViewPager1  = View.inflate(this, R.layout.layout_ruhu_pager_1, null);
		mViewPager2  = View.inflate(this, R.layout.layout_ruhu_pager_2, null);
		

		
		pageViews.add(mViewPager0);
		pageViews.add(mViewPager1);
		pageViews.add(mViewPager2);
		
		RuhuPagerAdapter mPagerAdapter= new RuhuPagerAdapter(pageViews);
		mViewPager.setAdapter(mPagerAdapter);
		getOrderformList();
	}
	
	int[] pager1_item_view_id=new int[]{R.id.pager_1_item_0,R.id.pager_1_item_1,R.id.pager_1_item_2};
	int[] pager2_item_view_id=new int[]{R.id.pager_2_item_0,R.id.pager_2_item_1};
	int[] pager_item_view_id=new int[]{R.id.ruhu_xinxi_item_0,R.id.ruhu_xinxi_item_1,R.id.ruhu_xinxi_item_2,R.id.ruhu_xinxi_item_3,R.id.ruhu_xinxi_item_4,R.id.ruhu_xinxi_item_5};
	int[] string_pager1_array_id=new int[]{R.array.ruhu_jiankan_xinxi,R.array.ruhu_weisheng_xinxi,R.array.ruhu_juzhu_xinxi};
	int[] string_pager2_array_id=new int[]{R.array.ruhu_shuidian_xinxi,R.array.ruhu_jingsheng_xinxi};
	
	int mBoxCount=0;
	String boxKey = "key";
	private void refreshDisplay(){
		refreshViewPager0();
		inflate_view(pager1_item_view_id,mViewPager1);
		inflate_view(pager2_item_view_id,mViewPager2);	
	}
	private void refreshViewPager0(){

		((TextView)findViewById(R.id.ruhu_dianhua_lianxiren)).setText(mPatrolBasicInfo.AlldayTel);
		((TextView)findViewById(R.id.ruhu_dianhua_jianhuren)).setText(mPatrolBasicInfo.MonitorTel);
		((TextView)findViewById(R.id.ruhu_dianhua_qinshu)).setText(mPatrolBasicInfo.RelativeTel);
		((TextView)findViewById(R.id.ruhu_xinming)).setText(MainActivity.mLaorenInfo.HumanName);
		((TextView)findViewById(R.id.ruhu_xingbie)).setText(MainActivity.mLaorenInfo.getSexString());
		((TextView)findViewById(R.id.ruhu_nianling)).setText(MainActivity.mLaorenInfo.Birthday);
		((TextView)findViewById(R.id.ruhu_shenfenzheng)).setText(MainActivity.mLaorenInfo.IDCardNo);
		((TextView)findViewById(R.id.ruhu_dizhi)).setText(MainActivity.mLaorenInfo.Address);
		
		((EditText)findViewById(R.id.ruhu_dianhua_gaoya)).setText(mPatrolBasicInfo.Hypertension);
		((EditText)findViewById(R.id.ruhu_dianhua_diya)).setText(mPatrolBasicInfo.Hypotension);
		((EditText)mViewPager2.findViewById(R.id.ruhu_jiankang_xinxi)).setText(mPatrolBasicInfo.FamilySafety);
		if(MainActivity.mLaorenInfo.AvatarImage != null){
			MyVolley.getImageLoader().get(MainActivity.mLaorenInfo.AvatarImage, 
					ImageLoader.getImageListener((ImageView) findViewById(R.id.ruhu_laoren_touxiang), 
							R.drawable.ic_avatar_holder, R.drawable.ic_avatar_holder));
		}else{
			((ImageView) findViewById(R.id.ruhu_laoren_touxiang)).setImageResource(R.drawable.ic_avatar_holder);
		}
	}
	
	private void inflate_view(int viewId[],View parent){
		for(int i=0;i<viewId.length;i++){
			View view = parent.findViewById(viewId[i]);
			inflate_xinxi_item(string_pager1_array_id[i],view);
		}
	}
	
	private void inflate_xinxi_item(int resId,View parent){
		Resources res = getResources();
		String str[] =  res.getStringArray(resId);
		
		for(int i=0;i<pager_item_view_id.length;i++){
			if(i==0){
				TextView text = (TextView)parent.findViewById(pager_item_view_id[i]);
				text.setText(str[i]);
			}else{
				CheckBox box = (CheckBox)parent.findViewById(pager_item_view_id[i]);
				box.setText(str[i]);
				box.setTag(mBoxCount);
				box.setChecked(!mPatrolBasicInfo.getBoolean(mBoxCount));
				box.setOnCheckedChangeListener(this);
				mBoxCount++;
			}
		}
	}
	
	
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int num = (int)buttonView.getTag();
		mPatrolBasicInfo.setBoolean(!isChecked, num);
	}
	
	@Override
	public void handleData() {

	}

	public final static int requestCode_photo = 1001;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.ruhu_jiating_tianjia_zhaopian_btn:
			startActivityForResult(new Intent(mContext, PhotoSelectActivity.class), requestCode_photo);
		default:
			break;
		}
	}
	private void getOrderformList(){
		ApiClient.getPatrolBasic(MainActivity.mLaorenInfo.HumanID,mXsyUser.peopleNo,
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
											mPatrolBasicInfo = JSON.parseObject(response.getJSONObject("result").toString(), PatrolBasicInfo.class);
											refreshDisplay();
											Log.d("onResponse","HumanID="+mPatrolBasicInfo.HumanID);
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
