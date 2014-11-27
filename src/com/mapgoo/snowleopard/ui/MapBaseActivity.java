package com.mapgoo.snowleopard.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.mapgoo.snowleopard.R;

public abstract class MapBaseActivity extends BaseActivity {
	private static final String LTAG = MapBaseActivity.class.getSimpleName();

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	protected class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				mToast.toastMsg(getText(R.string.bdmap_key_error_tips));
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				mToast.toastMsg(getText(R.string.bdmap_network_error_tips));
			}
		}
	}

	protected SDKReceiver mReceiver;
	protected MapView mMapView;
	protected BaiduMap mBaiduMap;

	protected LocationClient mLocClient = null;

	/**
	 * 概述: 定位初始化
	 * 
	 * @auther yao
	 */
	protected void initLocClient(BDLocationListener myLocationListener) {

		mLocClient = new LocationClient(mContext); // 声明LocationClient类
		mLocClient.registerLocationListener(myLocationListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型，百度地图坐标
		option.setScanSpan(10000); // 定位*时间间隔 10秒
		// 高精度定位, 同时使用GPS，Wifi和基站定位
//		option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocClient.setLocOption(option);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 关闭定位图层
		if (mBaiduMap != null)
			mBaiduMap.setMyLocationEnabled(false);

		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		if (mMapView != null) {
			mMapView.onDestroy();
			mMapView = null;
		}

		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();

		if (mReceiver != null)
			unregisterReceiver(mReceiver);

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		if(mLocClient != null)
			mLocClient.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		if (mMapView != null)
			mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		if (mMapView != null)
			mMapView.onPause();
	}

}
