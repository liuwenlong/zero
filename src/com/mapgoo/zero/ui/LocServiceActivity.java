package com.mapgoo.zero.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mapgoo.zero.R;

/**
 * 概述: 位置服务
 * 
 * @Author yao
 */
public class LocServiceActivity extends MapBaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_loc_service);
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
		super.setupActionBar(getText(R.string.home_weizhi).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);

		mMapView = (MapView) findViewById(R.id.bmapView);
		// 初始化百度地图设置
		initBaiduMapSettings();

		// 初始化定位设置
		initLocClient(new MyLocationListener());

	}
	
	private boolean isTrafficEnable = false;
	private boolean isFirstReqLoc = true; // 是否第一次开启定位
	private boolean isReqLoc = false; // 是否主动请求定位

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null)
				return;

			Log.d("latitude", location.getLatitude() + "");
			Log.d("longitude", location.getLongitude() + "");

			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(location.getDirection())
					.latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			if (isReqLoc) { // 第一次定位/请求定位 进入转到位置点
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
				mBaiduMap.animateMapStatus(u);

				isReqLoc = false; // 重置
			}
		}
	}
	

	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:

			finish();

			break;

		default:
			break;
		}
	}

}
