package com.mapgoo.zero.ui;

import java.sql.SQLException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapgoo.zero.MGApp;
import com.huaan.icare.pub.R;
import com.mapgoo.zero.bean.MGObject;
import com.mapgoo.zero.bean.User;
import com.mapgoo.zero.ui.widget.MyToast;
import com.mapgoo.zero.utils.LoadPref;

/**
 * 概述: 组织一个强大的基类，好让子类更方便的使用父类提供的方法，而不用重复写太多代码<br>
 * ps: 咱得用面向对象思想干点实事啊<br>
 * <br>
 * 1、细分其生命周期，形成固定化规范方法流程<br>
 * 2、引入自定义Activity栈管理<br>
 * 3、默认实现OnClickListener接口，子类必须实现OnClickListener中的方法<br>
 * <br>
 * 后续有什么通用的方法可以抽取到这里...<br>
 * 
 * @orignalAuthor yao
 * @createTime 2014年10月29日 下午3:57:41
 * 
 * @improvedAuther yao
 * @modifyTime 2014年10月29日
 */
public abstract class RegisterActivity extends Activity implements OnClickListener {

	protected LayoutInflater mInflater;
	protected MyToast mToast;
	protected Context mContext;

	/**
	 * 概述: 只需写初始化数据和初始化View控件之后的处理即可
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = getLayoutInflater();
		mContext = this;
		
		setContentView();
		
		_init(savedInstanceState);
		
		initViews();

		handleData();
	}
	

	private void _init(Bundle savedInstanceState) {

		mToast = MyToast.getInstance(mContext);

		initData(savedInstanceState);
	}
	
	/**
	 * 概述: 设置ContextView
	 * 
	 * @auther yao
	 */
	protected abstract void setContentView();

	/**
	 * 概述: 初始化当前页面所需要的数据，例如前一个页面传过来的数据，
	 * 
	 * @auther yao
	 * @param savedInstanceState
	 *            内存存储数据的变量 从onCreate传过来
	 */
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * 概述: 初始化当前页面的View控件
	 * 
	 * @auther yao
	 */
	protected abstract void initViews();

	/**
	 * 概述: 加载数据
	 * 
	 * @auther yao
	 */
	protected abstract void handleData();

	protected TextView tv_ab_title;
	protected ImageView iv_ab_left_btn;
	protected ImageView iv_ab_right_btn;

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

		ActionBar actionBar = getActionBar();

		if (actionBar == null)
			return;

		actionBar.setDisplayShowHomeEnabled(false); // 不显示Home
		actionBar.setDisplayShowTitleEnabled(false); // 不显示Title
		actionBar.setDisplayUseLogoEnabled(false); // 不显示logo
		actionBar.setDisplayShowCustomEnabled(true);

		if (mInflater == null)
			mInflater = getLayoutInflater();

		View actionBarView = mInflater.inflate(R.layout.layout_actionbar_generic, null);
		tv_ab_title = (TextView) actionBarView.findViewById(R.id.tv_ab_title);
		iv_ab_left_btn = (ImageView) actionBarView.findViewById(R.id.iv_ab_left_btn);
		iv_ab_right_btn = (ImageView) actionBarView.findViewById(R.id.iv_ab_right_btn);

		if (tv_ab_title != null)
			tv_ab_title.setText(title);

		switch (whichStyle) {
		case 1:
			
			if(leftBtnResId != -1)
				iv_ab_left_btn.setImageResource(leftBtnResId);
			iv_ab_left_btn.setVisibility(View.VISIBLE);
			
			iv_ab_right_btn.setVisibility(View.INVISIBLE);
			tv_ab_title.setClickable(false);

			break;

		case 2:

			if (rightBtnResId != -1)
				iv_ab_right_btn.setImageResource(rightBtnResId);
			iv_ab_right_btn.setVisibility(View.VISIBLE);
			
			if(leftBtnResId != -1)
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
	protected MGObject getCurObject() { // TODO 注意切换设备时会不会影响

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
	protected void onDestroy() {
		super.onDestroy();
		
	}

}
