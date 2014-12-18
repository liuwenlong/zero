package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.api.VersionUpdate;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.User;
import com.mapgoo.zero.ui.widget.AutoScrollViewPager;
import com.mapgoo.zero.ui.widget.CircleImageView;
import com.mapgoo.zero.ui.widget.CirclePageIndicator;
import com.mapgoo.zero.ui.widget.MyBannerAdapter;
import com.mapgoo.zero.utils.DoubleClickExitHelper;

public class MainActivity extends BaseActivity implements OnClosedListener  {
	
	private SlidingMenu mSlidingMenu;
	private View mMenuView;
	private CircleImageView civ_avatar;
	private TextView tv_wearer_nickname;

	private ArrayList<LaorenInfo> mLaorenList = new ArrayList<LaorenInfo>();
	LaorenInfo mLaorenInfo;
	
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
			startActivity(new Intent(mContext, LaorenActivity.class));
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
		VersionUpdate synctask = new VersionUpdate(mContext);
		synctask.execute("0201001");
	}

	@Override
	protected void handleData() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.man_name)).setText(mLaorenInfo.getHomeTitle());
		((TextView)findViewById(R.id.home_leixing_content)).setText(mLaorenInfo.mLeixing);
		((TextView)findViewById(R.id.home_shenfenzhen_content)).setText(mLaorenInfo.mShenfen);
		((TextView)findViewById(R.id.home_zhuzhi)).setText(mLaorenInfo.mAdress);
		((TextView)findViewById(R.id.home_dianhua)).setText(mLaorenInfo.mPhone);
	}
	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		
	}

}
