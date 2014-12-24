package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.VolleyError;
import com.huaan.icare.fws.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.bean.FwsOrderinfo;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;


public class MainActivity extends BaseActivity implements OnClosedListener, OnItemClickListener  {
	
	private final int RequestCode_Laoren = 1001;
	private final int ORDER_TYPE_D = 0;
	private final int ORDER_TYPE_Y = 1;
	private final int ORDER_TYPE_J = 2;
	
	private SlidingMenu mSlidingMenu;
	private View mMenuView;
	private ListView mListView;

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

	private void initSlideMenu() {
		mMenuView = mInflater.inflate(R.layout.layout_sliding_menu, null);
		
		TextView xsy_user_name = (TextView) mMenuView.findViewById(R.id.xsy_user_name);
		xsy_user_name.setText(mXsyUser.DisplayName);
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
	
	private void setPagerTo(int type){
		setSelect(type);
		getOrderInfoList(type);	
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
				setPagerTo(0);
				break;
			case R.id.home_yishou_btn:
				setPagerTo(1);
				break;
			case R.id.home_jieshu_btn:
				setPagerTo(2);
				break;
			case R.id.setting_modify_password:
				startActivity(new Intent(mContext, ModifyPassWordActivity.class));
				break;
			case R.id.setting_center_message:
				startActivity(new Intent(mContext, XiaoxiActivity.class));
				break;
			case R.id.setting_about:
				startActivity(new Intent(mContext, SettingsAboutActivity.class));
				break;
			case R.id.set_logout:
				startActivity(new Intent(mContext, LoginActivity.class));
				finish();
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
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}
	OrderAdapater adapter[] = new OrderAdapater[3];
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.setupActionBar(getText(R.string.fws_mian_title).toString(), 1, R.drawable.ic_menu, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		mListView = (ListView)findViewById(R.id.fws_order_list);
		mListView.setDividerHeight(0);
		mListView.setOnItemClickListener(this);
		setPagerTo(0);
	}
	private void refreshData(ArrayList<FwsOrderinfo> array1){
		if(adapter[OrderType] == null){
			adapter[OrderType]  = new OrderAdapater(mContext, array1, R.layout.fws_list_item_order);
		}else{
			adapter[OrderType] .setData(array1);
		}
		mListView.setAdapter(adapter[OrderType] );
	}	
	@Override
	protected void handleData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
	}
	

	private int OrderType;
	private void getOrderInfoList(int type){
		OrderType = type;
		ApiClient.getZhiyuanzheOrderList(type+1,mXsyUser.peopleNo, 1, Integer.MAX_VALUE,
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
											ArrayList<FwsOrderinfo> array1 = (ArrayList<FwsOrderinfo>)JSON.parseArray(array.get(1).toString(), FwsOrderinfo.class);
											refreshData(array1);
										}
									}else{
										ArrayList<FwsOrderinfo> array1 = new ArrayList<FwsOrderinfo>();
										refreshData(array1);
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
						}},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));
	}
	
	
	
	public class OrderAdapater extends CommonAdapter<FwsOrderinfo>{

		public OrderAdapater(Context context, List<FwsOrderinfo> mDatas,int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}
		
		public void setData(List<FwsOrderinfo> mDatas){
			this.mDatas.clear();
			this.mDatas = mDatas;
		}
		
		public void convert(ViewHolder holder, FwsOrderinfo item) {
			((TextView)(holder.getConvertView().findViewById(R.id.fws_list_item_order_dingdanhao))).setText(item.OrderCode);
			((TextView)(holder.getConvertView().findViewById(R.id.fws_order_yuyue_shijian))).setText(item.OrderTime);
			((TextView)(holder.getConvertView().findViewById(R.id.fws_list_item_order_note))).setText(item.Remark);
			((TextView)(holder.getConvertView().findViewById(R.id.fws_list_item_order_laoren_dizhi))).setText(item.HumanAddress);
			((TextView)(holder.getConvertView().findViewById(R.id.fws_list_item_order_status))).setText(item.OrderStatus);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 100 && resultCode == RESULT_OK){
			setPagerTo(OrderType);
		}
	}
	FwsOrderinfo mFwsOrderinfo;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mFwsOrderinfo = adapter[OrderType] .getItem(arg2);
		startActivityForResult( new Intent(mContext, OrderformDetailActivity.class).putExtra("FwsOrderinfo", mFwsOrderinfo),100);
	}
}
