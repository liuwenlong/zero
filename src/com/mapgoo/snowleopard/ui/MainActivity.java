package com.mapgoo.snowleopard.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.api.ApiClient.onReqStartListener;
import com.mapgoo.snowleopard.bean.User;
import com.mapgoo.snowleopard.ui.widget.CircleImageView;
import com.mapgoo.snowleopard.utils.DoubleClickExitHelper;

public class MainActivity extends BaseActivity implements OnClosedListener, ErrorListener, Listener<JSONObject>, onReqStartListener {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_main);
	}

	private DoubleClickExitHelper mDoubleClickExitHelper;

	private User mCurUser;
	
	// 自定义的请求码 标示当前是哪一个请求
	private int mReqCode = -1;
	private final int REQ_USEROBJLIST = 909;
	
	@Override
	public void initData(Bundle savedInstanceState) {
		mDoubleClickExitHelper = new DoubleClickExitHelper(this);
		
		if (savedInstanceState != null) {

		} else {

//			String telNum = getIntent().getStringExtra("telNum");
//
//			try {
//				mCurUser = User.getDao(MGApp.getHelper()).queryForId(telNum);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void initViews() {

		super.setupActionBar(getText(R.string.app_name).toString(), 2, R.drawable.ic_menu, R.drawable.ic_add);

		initSlideMenu();

	}

	private SlidingMenu mSlidingMenu;
	private View mMenuView;
	private CircleImageView civ_avatar;
	
	private TextView tv_wearer_nickname;
	
	private LinearLayout rl_menu_trajectory;
	private LinearLayout rl_menu_family_members;
	private LinearLayout rl_menu_safe_area;
	private LinearLayout rl_menu_settings;
	private LinearLayout rl_menu_official_mall;

	private void initSlideMenu() {
		mMenuView = mInflater.inflate(R.layout.layout_sliding_menu, null);
		tv_wearer_nickname = (TextView) mMenuView.findViewById(R.id.tv_wearer_nickname);
		civ_avatar = (CircleImageView) mMenuView.findViewById(R.id.civ_avatar);
		
//		rl_menu_trajectory = (LinearLayout) mMenuView.findViewById(R.id.rl_menu_trajectory);
//		rl_menu_family_members = (LinearLayout) mMenuView.findViewById(R.id.rl_menu_family_members);
//		rl_menu_safe_area = (LinearLayout) mMenuView.findViewById(R.id.rl_menu_safe_area);
//		rl_menu_settings = (LinearLayout) mMenuView.findViewById(R.id.rl_menu_settings);
//		rl_menu_official_mall = (LinearLayout) mMenuView.findViewById(R.id.rl_menu_official_mall);
//		rl_menu_trajectory.setSelected(true);	// 默认实时轨迹选中
		
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
		// 设置侧边菜单栏打开时的动画效果，
		// mSlidingMenu.setBehindCanvasTransformer()

		mSlidingMenu.setOnClosedListener(this); // 当SlideMenu关闭的事件监听
	}

	@Override
	public void handleData() {
		// 设置Api回调监听
//		ApiClient.setListeners(this, this,  GlobalNetErrorHandler.getInstance(mContext, mCurUser, null));
//
//		// 加载账号下的设备列表
//		if (mCurUser != null) {
//			mReqCode = REQ_USEROBJLIST;
//			ApiClient.getUserObjList(mCurUser.getUserId(), mCurUser.getAuthToken());
//		}

	}
	
	
	@Override
	public void onClosed() {	// TODO 关闭侧边栏菜单的回调，若所选设备更改，则更新首页信息 
	}
	
	private Fragment mCurrentFragment = null;

	@Override
	public void onClick(View v) {
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
			
		case R.id.iv_ab_right_btn:

			if (mCurrentFragment != null) {

//				if (mCurrentFragment instanceof HomeFragment) {
//					// 消息界面进入
//				} else if (mCurrentFragment instanceof FamliyMembersFragment) {
//					// 家庭成员添加
//					intent.setClass(mContext, FamilyMembersInviteActivity.class);
//					mCurrentFragment.startActivityForResult(intent, PICK_CONTACT_RESULT); // fragment的startActivityForResult
//
//				} else if (mCurrentFragment instanceof SafeAreaFragment) {
//					// 安全区域添加
//				}

			}
			
			break;
			
		case R.id.civ_avatar: // 若没有，则点击后跳rl_menu_settings转到添加界面，如有则点击到信息查看界面

//			String objectId1 =  ((MGObject) civ_avatar_middle.getTag()).getObjectId();
//			LoadPref.getInstance(mContext).beginTransaction().setCurObjectId(objectId1).commit();
//			
//			intent.setClass(mContext, WearerInfoActivity.class);
//			intent.putExtra("objectId", objectId1);
//			intent.putExtra("curUser", mCurUser);
//			
//			startActivity(intent);
			break;
			
		case R.id.rl_menu_settings:
			
			startActivity(new Intent(mContext, SettingsActivity.class));
			
			break;
			
		default:
			break;
		}

	}

	@Override
	public void onReqStart() {
	}

	@Override
	public void onResponse(JSONObject response) {
		if (mReqCode == REQ_USEROBJLIST) { // 获取账号设备列表

			try {

				if (response.has("error") && response.getInt("error") == 0) {

				} else
					mToast.toastMsg(response.has("reason") ? response.getString("reason") : getText(R.string.bad_network));

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		mToast.toastMsg(getText(R.string.bad_network));
		
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mSlidingMenu != null && mSlidingMenu.isMenuShowing()) // 如果侧边栏已打开，则先关闭侧边栏
				mSlidingMenu.showContent();
			else
				// 否则双击退出应用
				return mDoubleClickExitHelper.onKeyDown(keyCode, event);
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			// 菜单按键-是否进入设置？
			if (mSlidingMenu != null) {
				if (mSlidingMenu.isMenuShowing())
					mSlidingMenu.showContent();
				else
					mSlidingMenu.showMenu();
			}
		}

		return flag;
	}

}
