package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.VersionUpdate;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.User;
import com.mapgoo.zero.bean.XsyUser;
import com.mapgoo.zero.ui.widget.AutoScrollViewPager;
import com.mapgoo.zero.ui.widget.CircleImageView;
import com.mapgoo.zero.ui.widget.CirclePageIndicator;
import com.mapgoo.zero.ui.widget.MGProgressDialog;
import com.mapgoo.zero.ui.widget.MyBannerAdapter;
import com.mapgoo.zero.ui.widget.QuickShPref;
import com.mapgoo.zero.utils.DoubleClickExitHelper;

public class MainActivity extends BaseActivity implements OnClosedListener  {
	
	private final int RequestCode_Laoren = 1001;
	private SlidingMenu mSlidingMenu;
	private View mMenuView;
	private CircleImageView civ_avatar;
	private TextView tv_wearer_nickname;

	private ArrayList<LaorenInfo> mLaorenList = new ArrayList<LaorenInfo>();
	public static LaorenInfo mLaorenInfo;
	
	private void initSlideMenu() {
		mMenuView = mInflater.inflate(R.layout.layout_sliding_menu, null);
		tv_wearer_nickname = (TextView) mMenuView.findViewById(R.id.tv_wearer_nickname);
		civ_avatar = (CircleImageView) mMenuView.findViewById(R.id.civ_avatar);

		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setSlidingEnabled(true);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.slide_shadow_left);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.666f);
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		mSlidingMenu.setMenu(mMenuView);

		mSlidingMenu.setOnClosedListener(this); // 当SlideMenu关闭的事件监听
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RequestCode_Laoren:
			if(resultCode == RESULT_OK){
				mLaorenInfo = (LaorenInfo)data.getExtras().getSerializable("select");
				refreshDisplay(mLaorenInfo);
			}
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			if (mSlidingMenu != null) {
				if (mSlidingMenu.isMenuShowing())
					mSlidingMenu.showContent();
				else
					mSlidingMenu.showMenu();
			}
			break;
		case R.id.home_yuyue:
			startActivity(new Intent(mContext, YuyuefuwuActivity.class));
			break;
		case R.id.home_weizhi:
			myStartActivity(LoactionActivity.class);
			break;
		case R.id.home_rufu:
			startActivity(new Intent(mContext, RufuxunshiActivity.class));
			break;
		case R.id.home_dingdan:
			startActivity(new Intent(mContext, WodedingdanActivity.class));
			break;
		case R.id.tv_ab_title:
			startActivityForResult((new Intent(mContext, LaorenActivity.class).putExtra("laoren", mLaorenList)),RequestCode_Laoren);
			break;
		case R.id.iv_ab_right_btn:
			startActivity(new Intent(mContext, XiaoxiActivity.class));
			break;
		}
	}
void myStartActivity(Class<?> c){
	Intent forwardIntent = new Intent();
	forwardIntent.setClass(mContext, c);
	
	Bundle mBundle = new Bundle();
	mBundle.putSerializable("mLaorenInfo", mLaorenInfo);
	forwardIntent.putExtras(mBundle);
	
	startActivity(forwardIntent);			
}
	
	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		initSlideMenu();
	}

	private void inflateLaoren(){
		
	}
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(mLaorenList.isEmpty()){
			LaorenInfo info = new LaorenInfo();
			info.mAdress="北京市西城区陶然亭";
			info.mXingbie="男";	
			info.mLeixing="正常";	
			info.mPhone="13712345678";	
			info.mShenfen="430123456789";	
			info.mName="张三";
			info.mOld="86岁";
			info.mLocationTime="2014/12/17  15:23";
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
			mLaorenList.add(info);
		}
		mLaorenInfo = mLaorenList.get(0);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.setupActionBar(getText(R.string.home_action_title).toString(), 4, R.drawable.ic_menu, R.drawable.home_action_bar_xinxi,
				R.drawable.home_actionbar_bgd, -1);
		handleData();
		
//		new VersionUpdate(mContext).execute("0201001");
		getLoaorenInfo();
	}
	LaorenInfo getLaorenFromId(int objectId){
		for(LaorenInfo info:mLaorenList){
			if(info.ObjectID == objectId)
			return info;
		}
		if(mLaorenList.size()>0){
			return mLaorenList.get(0);
		}
		return null;
	}
	
	void refresLastLaoren(){
		int objectId= QuickShPref.getInt(QuickShPref.Last_Laoren_objectId);
		mLaorenInfo = getLaorenFromId(objectId);
		if(mLaorenInfo!=null)
			refreshDisplay(mLaorenInfo);
	}
	
	void refreshDisplay(LaorenInfo mLaorenInfo){

		((TextView)findViewById(R.id.man_name)).setText(mLaorenInfo.getHomeTitle());
		((TextView)findViewById(R.id.home_leixing_content)).setText(mLaorenInfo.HumanType);
		((TextView)findViewById(R.id.home_shenfenzhen_content)).setText(mLaorenInfo.IDCardNo);
		((TextView)findViewById(R.id.home_zhuzhi)).setText(mLaorenInfo.Address);
		((TextView)findViewById(R.id.home_dianhua)).setText(mLaorenInfo.AlldayTel);	
		
		QuickShPref.putValueObject(QuickShPref.Last_Laoren_objectId, mLaorenInfo.ObjectID);
		
		if(mLaorenInfo.AvatarImage != null){
			MyVolley.getImageLoader().get(mLaorenInfo.AvatarImage, 
					ImageLoader.getImageListener((ImageView) findViewById(R.id.avatar), 
							R.drawable.ic_avatar_holder, R.drawable.ic_avatar_holder));
		}else{
			((ImageView) findViewById(R.id.avatar)).setImageResource(R.drawable.ic_avatar_holder);
		}
	}
	
	@Override
	protected void handleData() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
	}

	private void getLoaorenInfo(){
		ApiClient.getLoarenInfoList(mXsyUser.peopleNo, 1, 10,
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
											 refresLastLaoren();
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
