package com.mapgoo.zero.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.LaorenInfo;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class LoactionActivity extends BaseActivity {
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	MapView mMapView;
	BaiduMap mBaiduMap;
	private PopupWindow pop;	
	private View popview;
	
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位
	LatLng mLaorenLatLng;
	LatLng mMylocLatLng;
	Marker mLaorenMarker;
	InfoWindow mLaorenInfoWindow;
	LaorenInfo mLaorenInfo;
	
	BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_loc_service);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {
			mLaorenInfo = (LaorenInfo)getIntent().getExtras().getSerializable("mLaorenInfo");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_4s_default).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_convenient_service_actionbar_bg, -1);
		
		initMapLoaction();
		Resources mResources = this.getResources();
		DisplayMetrics displayMetrics = mResources.getDisplayMetrics();
		int displayWidth = displayMetrics.widthPixels;
		
		popview = mInflater.inflate(R.layout.map_controllview, null);
		pop = new PopupWindow(popview, displayWidth, LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(R.style.mapPopview_anim);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);	
		// 点击OBD列表对话框外的监听
		pop.getContentView().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (pop.isShowing()) 
					pop.dismiss();
				return true;
			}});		

	}
	
	private void initMapLoaction(){
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
						mCurrentMode, true, null));				
	}

	@Override
	public void handleData() {

	}
	
	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}	
private void zoomIn(){
	float zoom = mBaiduMap.getMapStatus().zoom;
	perfomZoom(zoom-1);
}
private void zoomOut(){
	float zoom = mBaiduMap.getMapStatus().zoom;
	perfomZoom(zoom+1);
}
boolean mTrafficEnabled = false;
private void taggleTraffic(){
	ImageView  image;
	mTrafficEnabled = !mTrafficEnabled;
	mBaiduMap.setTrafficEnabled(mTrafficEnabled);
	image = (ImageView)findViewById(R.id.iv_traffic_switch);
	if(mTrafficEnabled){
		image.setImageResource(R.drawable.ic_main_traffic_on);
	}else{
		image.setImageResource(R.drawable.ic_main_traffic_off);
	}
}
	private void perfomZoom(float zoom) {
		try {
			float zoomLevel = zoom;
			MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
			mBaiduMap.animateMapStatus(u);
		} catch (NumberFormatException e) {
			Toast.makeText(this, "请输入正确的缩放级别", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			Log.d("location", "onReceiveLocation");
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			mMylocLatLng = new LatLng(location.getLatitude(),
					location.getLongitude());
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				mLaorenLatLng =  new LatLng(location.getLatitude()+0.03f,location.getLongitude());
				showLaorenMarker();
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
public void moveToPosition(LatLng latlng){
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latlng);
		mBaiduMap.animateMapStatus(u);		
}
private void  tiggerPop(View v){
	
	if(pop.isShowing()){
		pop.dismiss();
	}else{
		pop.showAsDropDown(v, 0, 0);
	}
}

private void showLaorenMarker(){
	OverlayOptions ooA = new MarkerOptions().position(mLaorenLatLng).icon(bdA)
			.zIndex(9).draggable(true);
	mLaorenMarker = (Marker) (mBaiduMap.addOverlay(ooA));	
	showLoarenInfoWindows();
}

private void showLoarenInfoWindows(){
	View v = View.inflate(mContext, R.layout.popview, null);
	
	((TextView)v.findViewById(R.id.loaren_name)).setText(mLaorenInfo.mName);
	((TextView)v.findViewById(R.id.laoren_location_time)).setText(mLaorenInfo.mLocationTime);
	((TextView)v.findViewById(R.id.laoren_location_adress)).setText(mLaorenInfo.mAdress);
	
	mLaorenInfoWindow = new InfoWindow(v, mLaorenLatLng, -47);
	mBaiduMap.showInfoWindow(mLaorenInfoWindow);	
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.iv_map_zoomin:
			zoomOut();
			break;
		case R.id.iv_map_zoomout:
			zoomIn();
			break;	
		case R.id.iv_traffic_switch:
			taggleTraffic();
			break;				
		case R.id.iv_map_viewmode:
			tiggerPop(v);
			break;	
		case R.id.ll_3dtu:
			pop.dismiss();
			break;
		case R.id.ll_weixing:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			pop.dismiss();
			break;
		case R.id.ll_pingmian:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			pop.dismiss();
			break;
		case R.id.iv_locate_target:
			moveToPosition(mLaorenLatLng);
			break;
		case R.id.iv_locate_me:
			moveToPosition(mMylocLatLng);
			break;
		case R.id.iv_more_func:
			myStartActivity(RightDialogActivity.class);
			break;
		default:
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
}
