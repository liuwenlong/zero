package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.mapgoo.zero.MGApp;
import com.huaan.icare.family.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.OrderFormInfo;
import com.mapgoo.zero.bean.PatrolBasicInfo;
import com.mapgoo.zero.ui.widget.NativeImageLoader;
import com.mapgoo.zero.ui.widget.RuhuPagerAdapter;
import com.mapgoo.zero.ui.widget.NativeImageLoader.NativeImageCallBack;
import com.mapgoo.zero.utils.DimenUtils;
import com.mapgoo.zero.utils.ImageUtils;

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
	private boolean mPatrolStatus;
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
		mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			public void onPageScrollStateChanged(int arg0) {
				//Log.d("pager", "onPageScrollStateChanged arg0="+arg0);
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				//Log.d("pager", "onPageScrolled arg0="+arg0+",arg1="+arg1+",arg2="+arg2);
			}
			public void onPageSelected(int arg0) {
				Log.d("pager", "onPageSelected arg0="+arg0);
				setPagerNum(arg0);
			}});
		setPagerNum(0);
		
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
		mBoxCount=0;
		refreshViewPager0();
		inflate_view(pager1_item_view_id,mViewPager1);
		inflate_view(pager2_item_view_id,mViewPager2);	
	}
	private void refreshViewPager0(){
		if(MainActivity.mLaorenInfo == null)
			return;
		((TextView)mViewPager0.findViewById(R.id.ruhu_dianhua_lianxiren)).setText(mPatrolBasicInfo.AlldayTel);
		((TextView)mViewPager0.findViewById(R.id.ruhu_dianhua_jianhuren)).setText(mPatrolBasicInfo.MonitorTel);
		((TextView)mViewPager0.findViewById(R.id.ruhu_dianhua_qinshu)).setText(mPatrolBasicInfo.RelativeTel);
		((TextView)mViewPager0.findViewById(R.id.ruhu_xinming)).setText(MainActivity.mLaorenInfo.HumanName);
		((TextView)mViewPager0.findViewById(R.id.ruhu_xingbie)).setText(MainActivity.mLaorenInfo.getSexString());
		((TextView)mViewPager0.findViewById(R.id.ruhu_nianling)).setText(MainActivity.mLaorenInfo.Birthday);
		((TextView)mViewPager0.findViewById(R.id.ruhu_shenfenzheng)).setText(MainActivity.mLaorenInfo.IDCardNo);
		((TextView)mViewPager0.findViewById(R.id.ruhu_dizhi)).setText(MainActivity.mLaorenInfo.Address);
		
		if(MainActivity.mLaorenInfo.HasSOSMDT){
			mViewPager0.findViewById(R.id.ruhu_devices_not_location).setVisibility(View.INVISIBLE);
		}else{
			mViewPager0.findViewById(R.id.ruhu_devices_not_location).setVisibility(View.VISIBLE);
		}
		
		((EditText)mViewPager0.findViewById(R.id.ruhu_dianhua_gaoya)).setText(mPatrolBasicInfo.Hypertension);
		((EditText)mViewPager0.findViewById(R.id.ruhu_dianhua_diya)).setText(mPatrolBasicInfo.Hypotension);
		((EditText)mViewPager2.findViewById(R.id.ruhu_jiating_qinshuru_chuli_jieguo)).setText(mPatrolBasicInfo.FamilySafety);
		
		if(MainActivity.mLaorenInfo.AvatarImage != null){
			MyVolley.getImageLoader().get(MainActivity.mLaorenInfo.AvatarImage, 
					ImageLoader.getImageListener((ImageView) findViewById(R.id.ruhu_laoren_touxiang), 
							R.drawable.ic_avatar_holder, R.drawable.ic_avatar_holder));
		}else{
			((ImageView) findViewById(R.id.ruhu_laoren_touxiang)).setImageResource(R.drawable.ic_avatar_holder);
		}
		
		refreshPatrolStatus(mPatrolBasicInfo.PatrolStatus);
		
		addBitmap(ImageUtils.getBitmapFromBase64String(mContext,mPatrolBasicInfo.SafetyImg1),mPatrolBasicInfo.SafetyImg1);
		addBitmap(ImageUtils.getBitmapFromBase64String(mContext,mPatrolBasicInfo.SafetyImg2),mPatrolBasicInfo.SafetyImg1);
		addBitmap(ImageUtils.getBitmapFromBase64String(mContext,mPatrolBasicInfo.SafetyImg3),mPatrolBasicInfo.SafetyImg1);
		addBitmap(ImageUtils.getBitmapFromBase64String(mContext,mPatrolBasicInfo.SafetyImg4),mPatrolBasicInfo.SafetyImg1);
	}
	
	void refreshPatrolStatus(boolean b){
			mPatrolStatus = b;
			((TextView)findViewById(R.id.ruhu_xunshi_sign_in)).setClickable(!b);
			((TextView)findViewById(R.id.ruhu_xunshi_sign_out)).setClickable(b);
			
			if(b){
				((TextView)findViewById(R.id.ruhu_xunshi_sign_out)).setTextColor(getResources().getColor(R.color.color_main_blue));
				((TextView)findViewById(R.id.ruhu_xunshi_sign_in)).setTextColor(getResources().getColor(R.color.normal_color_text));			
			}else{
				((TextView)findViewById(R.id.ruhu_xunshi_sign_out)).setTextColor(getResources().getColor(R.color.normal_color_text));
				((TextView)findViewById(R.id.ruhu_xunshi_sign_in)).setTextColor(getResources().getColor(R.color.color_main_blue));							
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

	ArrayList<String> mImageList = new ArrayList<String>();
	 
	private void addPhoto(String path){
		
		Point point = new Point();
		point.set(DimenUtils.dip2px(mContext, 60), DimenUtils.dip2px(mContext, 60));
		Bitmap bp = NativeImageLoader.getInstance().loadNativeImage(path, point, new NativeImageCallBack() {
			public void onImageLoader(Bitmap bitmap, String path) {
				addBitmap(bitmap,null);
			}
		});
		addBitmap(bp,null);
	}
	private void addBitmap(Bitmap bitmap,String base64){
		if(bitmap != null){
			LinearLayout linear = (LinearLayout)mViewPager2.findViewById(R.id.ruhu_jiating_tianjia_zhaopian_list);
			
			View view = View.inflate(mContext, R.layout.grid_child_item, null);
			ImageView image = (ImageView)view.findViewById(R.id.child_image);
			
			image.setImageBitmap(bitmap);
			
			if(base64 == null)
				mImageList.add(0,ImageUtils.img2Base64(MGApp.pThis, bitmap));
			else
				mImageList.add(0, base64);
			
			LinearLayout.LayoutParams param = new  LinearLayout.LayoutParams(DimenUtils.dip2px(mContext, 60),DimenUtils.dip2px(mContext, 60) );

			param.setMargins(0, 0, DimenUtils.dip2px(mContext, 5), 0);
			
			linear.addView(view, 0, param);
			
			if(linear.getChildCount()>=5)
				mViewPager2.findViewById(R.id.ruhu_jiating_tianjia_zhaopian_btn).setVisibility(View.INVISIBLE);
			
			Log.d("ChildCount", "linear.getChildCount()="+linear.getChildCount());
		}
	}
	
	private String getImageBase64(int num){
		String str = null;
		if(mImageList.size()>num){
			str = mImageList.get(num);
		}
		return str;
	}	
	
	public final static int requestCode_photo = 1001;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == requestCode_photo && resultCode == RESULT_OK){
			String photo = data.getStringExtra("photo");
			addPhoto(photo);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.ruhu_jiating_tianjia_zhaopian_btn:
			startActivityForResult(new Intent(mContext, PhotoSelectActivity.class), requestCode_photo);
			break;
		case R.id.ruhu_xunshi_sign_in:
			setPatrolSign(0);
			break;
		case R.id.ruhu_xunshi_sign_out:
			setPatrolSign(1);
			break;
		case R.id.ruhu_xinxi_pager_0:
			setPagerNum(0);
			break;
		case R.id.ruhu_xinxi_pager_1:
			setPagerNum(1);
			break;
		case R.id.ruhu_xinxi_pager_2:
			setPagerNum(2);
			break;
		case R.id.ruhu_xinxi_tijiao_0:
		case R.id.ruhu_xinxi_tijiao_1:
		case R.id.ruhu_xinxi_tijiao_2:
			postPatrolBasic();
			break;
		default:
			break;
		}
	}
	
	private void setPagerNum(int num){
		mViewPager.setCurrentItem(num);
		
		((TextView)findViewById(R.id.ruhu_xinxi_pager_0)).setTextColor(getResources().getColor(R.color.normal_color_text));
		((TextView)findViewById(R.id.ruhu_xinxi_pager_1)).setTextColor(getResources().getColor(R.color.normal_color_text));
		((TextView)findViewById(R.id.ruhu_xinxi_pager_2)).setTextColor(getResources().getColor(R.color.normal_color_text));
		
		int text[] =new int[]{R.id.ruhu_xinxi_pager_0,R.id.ruhu_xinxi_pager_1,R.id.ruhu_xinxi_pager_2};
		((TextView)findViewById(text[num])).setTextColor(getResources().getColor(R.color.color_main_blue));
		
		findViewById(R.id.ruhu_xinxi_pager_line_0).setVisibility(View.INVISIBLE);
		findViewById(R.id.ruhu_xinxi_pager_line_1).setVisibility(View.INVISIBLE);
		findViewById(R.id.ruhu_xinxi_pager_line_2).setVisibility(View.INVISIBLE);
		
		int line[] =new int[]{R.id.ruhu_xinxi_pager_line_0,R.id.ruhu_xinxi_pager_line_1,R.id.ruhu_xinxi_pager_line_2};
		findViewById(line[num]).setVisibility(View.VISIBLE);
		
	}
	
	private void getOrderformList(){
		if(MainActivity.mLaorenInfo!=null)
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

	private void setPatrolSign(int sign){
		if(MainActivity.mLaorenInfo!=null)
		ApiClient.setPatrolSign(sign,
				Integer.parseInt(MainActivity.mLaorenInfo.HumanID),
				Integer.parseInt(mXsyUser.peopleNo),
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
										refreshPatrolStatus(!mPatrolStatus);
									}else{
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}}},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));				
	}
	

	
	private void initPatrolBasicInfo(){
		mPatrolBasicInfo.Hypertension = ((EditText)mViewPager0.findViewById(R.id.ruhu_dianhua_gaoya)).getText().toString();
		mPatrolBasicInfo.Hypotension = ((EditText)mViewPager0.findViewById(R.id.ruhu_dianhua_diya)).getText().toString();
		mPatrolBasicInfo.FamilySafety = ((EditText)mViewPager2.findViewById(R.id.ruhu_jiating_qinshuru_chuli_jieguo)).getText().toString();
		
		mPatrolBasicInfo.SafetyImg1 = getImageBase64(0);
		mPatrolBasicInfo.SafetyImg2 = getImageBase64(1);
		mPatrolBasicInfo.SafetyImg3 = getImageBase64(2);
		mPatrolBasicInfo.SafetyImg4 = getImageBase64(3);
	}
	
	private void postPatrolBasic(){
		initPatrolBasicInfo();
		ApiClient.postPatrolBasic(mPatrolBasicInfo,
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
										//refreshPatrolStatus(!mPatrolStatus);
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
