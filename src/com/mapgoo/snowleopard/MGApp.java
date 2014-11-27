package com.mapgoo.snowleopard;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mapgoo.snowleopard.api.MyVolley;
import com.mapgoo.snowleopard.utils.DatabaseHelper;

public class MGApp extends Application {

	public static MGApp pThis;

	@Override
	public void onCreate() {
		super.onCreate();

		init();

	}

	private void init() {
		pThis = this;

		MyVolley.init(this); // 单例初始化

		// 初始化百度地图引擎、定位引擎
		initMap();

		// 初始化极光推送
		initJpush();

		// 初始化 UIL(Universal Image Loader)
	}

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized MGApp getInstance() {
		return pThis;
	}

	private void initMap() {
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		SDKInitializer.initialize(getApplicationContext());
	}

	private void initJpush() {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	/**
	 * You'll need this in your class to cache the helper in the class.
	 */
	protected static DatabaseHelper mDatabaseHelper = null;

	/**
	 * You'll need this in your class to get the helper from the manager once
	 * per class.
	 */
	public static DatabaseHelper getHelper() {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = OpenHelperManager.getHelper(pThis, DatabaseHelper.class);
		}

		return mDatabaseHelper;
	}

	// FIXME 再整个App生命周期中， 维持数据的链接，是不是不太好？
	@Override
	public void onTerminate() {
		super.onTerminate();

		/*
		 * You'll need this in your class to release the helper when done.
		 */
		if (mDatabaseHelper != null) {
			OpenHelperManager.releaseHelper();
			mDatabaseHelper = null;
		}
	}

}
