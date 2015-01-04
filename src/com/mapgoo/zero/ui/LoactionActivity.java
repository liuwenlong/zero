package com.mapgoo.zero.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
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
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.huaan.icare.xsy.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.LaorenLocInfo;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.bean.OrderFormInfo;
import com.mapgoo.zero.service.LocalService;
import com.mapgoo.zero.service.LocalService.MyLoactionChange;

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
	LaorenLocInfo mLaorenLocInfo;
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
		doBindService();
	}
	
	private void ConverterLaorenLoaction(LaorenLocInfo info){
		CoordinateConverter converter=new CoordinateConverter();
		LatLng source = new LatLng(Double.parseDouble( info.Lat),Double.parseDouble( info.Lon));
		converter.from(CoordType.GPS);
		converter.coord(source);
		LatLng desLatLng = converter.convert();
		info.Lat = desLatLng.latitude+"";
		info.Lon = desLatLng.longitude+"";
	}
	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mLaorenInfo = (LaorenInfo)savedInstanceState.getSerializable("mLaorenInfo");
		} else {
			mLaorenInfo = (LaorenInfo)getIntent().getExtras().getSerializable("mLaorenInfo");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("mLaorenInfo", mLaorenInfo);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar("位置服务", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
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
		getTracks();
	}
	
	private void initMapLoaction(){
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.NORMAL, true, null));				
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
		doUnbindService();
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
			//Log.d("location", "onReceiveLocation");
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
				//LatLng ll = new LatLng(location.getLatitude(),
				//		location.getLongitude());
				//MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				//mBaiduMap.animateMapStatus(u);
				//mLaorenLatLng =  new LatLng(location.getLatitude()+0.03f,location.getLongitude());
				//showLaorenMarker();
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
	if(isLoactionLaoren()){
		mLaorenLatLng =  new LatLng(Double.parseDouble( mLaorenLocInfo.Lat),Double.parseDouble( mLaorenLocInfo.Lon));
		OverlayOptions ooA = new MarkerOptions().position(mLaorenLatLng).icon(bdA)
				.zIndex(9).draggable(true);
		mLaorenMarker = (Marker) (mBaiduMap.addOverlay(ooA));	
		showLoarenInfoWindows();
		new LoactionUpdate().execute(mLaorenLatLng);
	}
}

private void showLoarenInfoWindows(){
	if(isLoactionLaoren()){
		View v = View.inflate(this, R.layout.popview, null);
		
		((TextView)v.findViewById(R.id.loaren_name)).setText(mLaorenLocInfo.HumanName);
		((TextView)v.findViewById(R.id.laoren_location_time)).setText(mLaorenLocInfo.getLoactionStatus());
		if(mLaorenLocInfo.Adress == null)
			((TextView)v.findViewById(R.id.laoren_location_adress)).setText("正在获取地址...");
		else
			((TextView)v.findViewById(R.id.laoren_location_adress)).setText(mLaorenLocInfo.Adress);
	
		if(v == null)
			Log.d("showLoarenInfoWindows", "v is null.");
		
		v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		mLaorenInfoWindow = new InfoWindow(v, mLaorenLatLng, -47);
		if(mLaorenInfoWindow == null)
			Log.d("showLoarenInfoWindows", "mLaorenInfoWindow is null.");
		
		mBaiduMap.showInfoWindow(mLaorenInfoWindow);
	}
	
	
}

public class LoactionUpdate  extends AsyncTask<LatLng, Integer, String>{

	@Override
	protected String doInBackground(LatLng... params) {
		// TODO Auto-generated method stub
		LatLng latlng = params[0];
		String rsl = getAddrDescByCoordinate(latlng.latitude, latlng.longitude);
		return rsl;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mLaorenLocInfo.Adress = result;
		showLoarenInfoWindows();
	}

	public  String inputStreamTOString(InputStream in, String encoding) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);

		data = null;

		return new String(outStream.toByteArray(), encoding);
	}
	public  String getAddrDescByCoordinate(Double lat,Double lng) {
		String resultStr;

		String url = String.format("http://access2.u12580.com/getPos1/Geocoder.v2.aspx?pos=%f,%f", lng,lat);

		Log.e("url", url);

		InputStream returnedInputStream = getResponse(url);
		try {
			if (returnedInputStream != null) {
				resultStr = inputStreamTOString(returnedInputStream, "UTF-8");
			} else {
				resultStr = " ";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = " ";
		}
		Log.e("getAddrDescByCoordinate", resultStr);
		return resultStr;
	}	
	
	public  InputStream getResponse(String url) {
		try {
			HttpParams httpParameters;
			httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 60000);
			DefaultHttpClient mClient = new DefaultHttpClient(httpParameters);
			url = url.replace(" ", "%20");
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = mClient.execute(getMethod);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				getMethod.abort();
				return null;
			}

			HttpEntity entry = response.getEntity();
			InputStream mInputStream = entry.getContent();

			return mInputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

private boolean isLoactionLaoren(){
	if(mLaorenLocInfo!=null && mLaorenLocInfo.HasMDT &&mLaorenLocInfo.isLoactionAviable() )
		return true;
	return false;
}

private void moveToLoaren(){
	if(isLoactionLaoren()){
		LatLng latlng = new LatLng(Double.parseDouble(mLaorenLocInfo.Lat), Double.parseDouble(mLaorenLocInfo.Lon));
		moveToPosition(latlng);
	}	
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
			moveToLoaren();
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
	
	MyLoactionChange mMyLoactionChange = new MyLoactionChange(){
		@Override
		public void onMyLoactionChange(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			//Log.d("location", "onReceiveLocation");
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mMylocLatLng = new LatLng(location.getLatitude(),
					location.getLongitude());
			if(mBaiduMap != null)
				mBaiduMap.setMyLocationData(locData);
		}
	};
	
	private LocalService mBoundService;

	private ServiceConnection mConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        // This is called when the connection with the service has been
	        // established, giving us the service object we can use to
	        // interact with the service.  Because we have bound to a explicit
	        // service that we know is running in our own process, we can
	        // cast its IBinder to a concrete class and directly access it.
	        mBoundService = ((LocalService.LocalBinder)service).getService();
	        if(mBoundService != null)
	        	mBoundService.setMyLocationListenner(mMyLoactionChange);
	        // Tell the user about this for our demo.
//	        Toast.makeText(Binding.this, R.string.local_service_connected,
//	                Toast.LENGTH_SHORT).show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        // Because it is running in our same process, we should never
	        // see this happen.
	        mBoundService = null;
//	        Toast.makeText(Binding.this, R.string.local_service_disconnected,
//	                Toast.LENGTH_SHORT).show();
	    }
	};
boolean mIsBound;
	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).
	    bindService(new Intent(this, 
	            LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
	    mIsBound = true;
	}

	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        unbindService(mConnection);
	        mIsBound = false;
	    }
	}

	
	void myStartActivity(Class<?> c){
		Intent forwardIntent = new Intent();
		forwardIntent.setClass(mContext, c);
		
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("mLaorenLocInfo", mLaorenLocInfo);
		forwardIntent.putExtras(mBundle);
		
		startActivity(forwardIntent);			
	}
	
	private void getTracks(){
		ApiClient.getTracks(mLaorenInfo.ObjectID+"",
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
										mLaorenLocInfo = JSON.parseObject(response.getJSONObject("result").toString(), LaorenLocInfo.class);
										if(isLoactionLaoren()){
											ConverterLaorenLoaction(mLaorenLocInfo);
											showLaorenMarker();
											moveToLoaren();
											perfomZoom(16);
										}else{
											mToast.toastMsg("无法有效定位");
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
