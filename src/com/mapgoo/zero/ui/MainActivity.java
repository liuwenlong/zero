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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.VolleyError;
import com.huaan.icare.volunteer.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.bean.FwsOrderinfo;


public class MainActivity extends BaseActivity implements OnClosedListener  {
	
	private final int RequestCode_Laoren = 1001;
	private final int ORDER_TYPE_D = 0;
	private final int ORDER_TYPE_Y = 1;
	private final int ORDER_TYPE_J = 2;
	
	private SlidingMenu mSlidingMenu;
	private View mMenuView;
	private ListView mListView;
	
private ArrayList<ArrayList<FwsOrderinfo>> mFwsOrderList ;

int btn_image[]= new int[]{R.id.home_daishou_image,R.id.home_yishou_image,R.id.home_jieshu_image};
int btn_image_ds[] = new int[]{R.drawable.home_daishouli_ds,R.drawable.home_yishou_ds,R.drawable.home_jieshu_ds};
int btn_image_en[] = new int[]{R.drawable.home_daishouli_en,R.drawable.home_yishou_en,R.drawable.home_jieshu_en};

int btn_text[]= new int[]{R.id.home_daishou_text,R.id.home_yishou_text,R.id.home_jieshu_text};
int btn_line[]= new int[]{R.id.home_daishou_line,R.id.home_yishou_line,R.id.home_jieshu_line};

private void setSelect(int num){
	int i=0;
	for(i=0;i<btn_image.length;i++){
		((ImageView)findViewById(btn_image[i])).setImageResource(btn_image_ds[i]);
		((TextView)findViewById(btn_text[i])).setTextColor(getResources().getColor(R.color.normal_color_text));
		findViewById(btn_line[i]).setVisibility(View.INVISIBLE);
	}
	i = num;
	((ImageView)findViewById(btn_image[i])).setImageResource(btn_image_en[i]);
	((TextView)findViewById(btn_text[i])).setTextColor(getResources().getColor(R.color.color_main_blue));
	findViewById(btn_line[i]).setVisibility(View.VISIBLE);	
	
}

private void getFwsOrderinfo(int type){
	if(mFwsOrderList == null){
		ArrayList<FwsOrderinfo> array0, array1,array2;
		
		mFwsOrderList = new ArrayList<ArrayList<FwsOrderinfo>>();
		
		array0 = new ArrayList<FwsOrderinfo>();
		array1 = new ArrayList<FwsOrderinfo>();
		array2 = new ArrayList<FwsOrderinfo>();
		mFwsOrderList.add(array0);
		mFwsOrderList.add(array1);
		mFwsOrderList.add(array2);
	}
	ArrayList<FwsOrderinfo> array = mFwsOrderList.get(type);
	FwsOrderinfo info = new FwsOrderinfo();
	info.orderAdress = "北京东城2号";
	info.orderContent="啦啦";
	info.orderStatus = "待受理";
    info.orderTime="2014-12-17 12:30";
	info.orderNumber="500001623157324521";
	array.add(info);
	array.add(info);
	array.add(info);
}

	private void initSlideMenu() {
		mMenuView = mInflater.inflate(R.layout.layout_sliding_menu, null);
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

			}
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.iv_ab_left_btn:
				if (mSlidingMenu != null) {
					if (mSlidingMenu.isMenuShowing())
						mSlidingMenu.showContent();
					else
						mSlidingMenu.showMenu();
				}
				break;
			case R.id.home_daishou_btn:
				setSelect(0);
				break;
			case R.id.home_yishou_btn:
				setSelect(1);
				break;
			case R.id.home_jieshu_btn:
				setSelect(2);
				break;				
		}
	}
void myStartActivity(Class<?> c){
	Intent forwardIntent = new Intent();
	forwardIntent.setClass(mContext, c);
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

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.setupActionBar(getText(R.string.fws_mian_title).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		mListView = (ListView)findViewById(R.id.fws_order_list);
		
		handleData();
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
