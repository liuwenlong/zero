package com.mapgoo.snowleopard.ui;

import java.sql.SQLException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapgoo.snowleopard.MGApp;
import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.bean.MGObject;
import com.mapgoo.snowleopard.bean.User;
import com.mapgoo.snowleopard.utils.LoadPref;
import com.mapgoo.snowleopard.widget.MyToast;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_init();
	}

	protected Context mContext;
	protected MyToast mToast;

	private void _init() {
		mContext = getActivity();

		mToast = MyToast.getInstance(getActivity());
	}

	protected TextView tv_ab_title;
	protected ImageView iv_ab_left_btn;
	protected ImageView iv_ab_right_btn;

	private LayoutInflater mInflater;

	/**
	 * 概述: 概述: 设置ActionBar，如果有就设置，没有就返回
	 * 
	 * @auther yao
	 * @param title
	 *            标题
	 * @param whichStyle
	 *            哪种类型的：1、左按钮-中标题； 2、左按钮-中标题-右按钮(按钮图标设置)； 3、左右按钮图标全设置
	 * @param leftBtnResId
	 *            左按钮图标， -1为不设置
	 * @param rightBtnResId
	 *            右按钮图标， -1为不设置
	 */
	protected void setupActionBar(String title, int whichStyle, int leftBtnResId, int rightBtnResId) {

		ActionBar actionBar = ((Activity) mContext).getActionBar();

		if (actionBar == null)
			return;

		actionBar.setDisplayShowHomeEnabled(false); // 不显示Home
		actionBar.setDisplayShowTitleEnabled(false); // 不显示Title
		actionBar.setDisplayUseLogoEnabled(false); // 不显示logo
		actionBar.setDisplayShowCustomEnabled(true);

		if (mInflater == null)
			mInflater = ((Activity) mContext).getLayoutInflater();

		View actionBarView = mInflater.inflate(R.layout.layout_actionbar_generic, null);
		tv_ab_title = (TextView) actionBarView.findViewById(R.id.tv_ab_title);
		iv_ab_left_btn = (ImageView) actionBarView.findViewById(R.id.iv_ab_left_btn);
		iv_ab_right_btn = (ImageView) actionBarView.findViewById(R.id.iv_ab_right_btn);

		if (tv_ab_title != null)
			tv_ab_title.setText(title);

		switch (whichStyle) {
		case 1:

			if (leftBtnResId != -1)
				iv_ab_left_btn.setImageResource(leftBtnResId);
			iv_ab_left_btn.setVisibility(View.VISIBLE);

			iv_ab_right_btn.setVisibility(View.INVISIBLE);
			tv_ab_title.setClickable(false);

			break;

		case 2:

			if (rightBtnResId != -1)
				iv_ab_right_btn.setImageResource(rightBtnResId);
			iv_ab_right_btn.setVisibility(View.VISIBLE);

			if (leftBtnResId != -1)
				iv_ab_left_btn.setImageResource(leftBtnResId);
			iv_ab_left_btn.setVisibility(View.VISIBLE);

			tv_ab_title.setClickable(false);

			break;

		case 3:

			if (leftBtnResId != -1) {
				iv_ab_left_btn.setImageResource(leftBtnResId);
				iv_ab_left_btn.setVisibility(View.VISIBLE);
			}

			if (rightBtnResId != -1) {
				iv_ab_right_btn.setImageResource(rightBtnResId);
				iv_ab_right_btn.setVisibility(View.VISIBLE);
			}

			tv_ab_title.setClickable(false);

			break;
		case 4: // 带arrow down的actionbar

			if (leftBtnResId != -1) {
				iv_ab_left_btn.setImageResource(leftBtnResId);
				iv_ab_left_btn.setVisibility(View.VISIBLE);
			}

			if (rightBtnResId != -1) {
				iv_ab_right_btn.setImageResource(rightBtnResId);
				iv_ab_right_btn.setVisibility(View.VISIBLE);
			}

			tv_ab_title.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.titlebar_arrow_down),
					null);
			tv_ab_title.setClickable(true);

			break;

		default:
			break;
		}

		actionBar.setCustomView(actionBarView, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	private User mCurUser;

	/**
	 * 概述: 获取当前已登录的用户信息
	 * 
	 * @auther yao
	 * @return
	 */
	protected User getCurUser() { // TODO 注意切换用户时会不会影响

//		if (mCurUser == null) {
			String curUserId = LoadPref.getInstance(mContext).getLastLoginUserId();

			try {
				mCurUser = User.getDao(MGApp.getHelper()).queryForId(curUserId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//		}

		return mCurUser;
	}

	private MGObject mCurObject; // 当前选中的设备

	/**
	 * 概述: 获得当前选中的设备信息
	 * 
	 * @auther yao
	 * @return
	 */
	protected MGObject getCurObject() { // TODO 注意切换用户时会不会影响

		if (mCurObject == null) {
			String curObjectId = LoadPref.getInstance(mContext).getCurObjectId();

			try {
				mCurObject = (MGObject) MGObject.getDao(MGApp.getHelper()).queryForId(curObjectId);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return mCurObject;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
