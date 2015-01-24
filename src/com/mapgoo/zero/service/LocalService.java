package com.mapgoo.zero.service;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.huaan.icare.family.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.RequestUtils;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenInfo;
import com.mapgoo.zero.bean.XsyUser;
import com.mapgoo.zero.ui.LoginActivity;
import com.mapgoo.zero.ui.MainActivity;
import com.mapgoo.zero.ui.widget.MyToast;
import com.mapgoo.zero.ui.widget.QuickShPref;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {
    private NotificationManager mNM;
	public static final String RESULTACTION = "com.mapgoo.service.result";
    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.app_name;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }
    public interface MyLoactionChange{
    	public void onMyLoactionChange(BDLocation location);
    }
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)	return;
			if(mMyLoactionChange!=null){
				mMyLoactionChange.onMyLoactionChange(location);
			}
			mLastBDLocation = location;
			//Updatelocation(location);
		}
		public void onReceivePoi(BDLocation poiLocation) {}
	}
	
	private void Updatelocation(final BDLocation location){
		//RequestUtils.setToken("sss'");
		new Thread(){
			public void run() {
				super.run();
				mNetwork.uploadPos(location);
			}}.start();
	}
		
		
//		ApiClient.UpdatePosition(Integer.parseInt((QuickShPref.getString(QuickShPref.PEOPLE_ON))), location,	null,
//				new Listener<JSONObject> (){
//					public void onResponse(JSONObject response) {
//						Log.d("onResponse", "MyLocationListenner:"+response.toString());
//						if (response.has("error")) {
//							try {
//								if (response.getInt("error") == 0) {
////									JSONArray array = response.getJSONArray("result");
//								}else{
//									//mToast.toastMsg(response.getString("reason"));
//								}
//							}catch (JSONException e) {e.printStackTrace();}
//						}
//					}},
//					new ErrorListener(){
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							Log.d("onErrorResponse", error.toString());
//							NetworkResponse response = error.networkResponse;
//							/************************add for 红米 401***********************/
//										String str = error.toString();
//										if(error instanceof NoConnectionError){
//											if(str.contains("No authentication challenges found")){
//												getToken();
//											}
//										}
//							/************************add for 红米 401 end***********************/
//										if (response != null) {
//											Log.d("onErrorResponse", "response.statusCode="+response.statusCode);
//											switch (response.statusCode) {
//												case 401:
//													getToken();
//													break;
//											}
//										}
//						}
//					});
	
//	private void getToken(){
//		if(mXsyUser != null){
//		ApiClient.loginInternel(mXsyUser.userName, mXsyUser.mPassword, 
//				null, 
//				new Listener<JSONObject>() {
//					@Override
//					public void onResponse(JSONObject response) {
//						Log.d("onResponse", response.toString());
//						if (response.has("error")) {
//							try {
//								if(response.getInt("error") == 0){
//									XsyUser user = JSON.parseObject(response.getJSONObject("result").toString(), XsyUser.class);
//									RequestUtils.setToken(user.token);
//									QuickShPref.putValueObject(QuickShPref.TOKEN, user.token);
//								}else{
//									String reason=response.getString("reason");
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//								QuickShPref.putValueObject(QuickShPref.isLogin, false);
//								//mContext.startActivity(new Intent(mContext, LoginActivity.class));
//							}
//						}
//					}
//				}, GlobalNetErrorHandler.getInstance(mContext, mXsyUser, null));
//		}else{
//			Log.e("getToken", "mXsyUser is null");
//		}
//	}
	
    LocationClient mLocClient;
    MyLocationListenner myListener= new MyLocationListenner();
    MyLoactionChange mMyLoactionChange;
    BDLocation mLastBDLocation;
    public void setMyLocationListenner(MyLoactionChange l){
    	mMyLoactionChange = l;
    	if(mLastBDLocation != null)
    		mMyLoactionChange.onMyLoactionChange(mLastBDLocation);
    }
    Context mContext;
    Network mNetwork;
    @Override
    public void onCreate() {
    	 locationInit();
    	 mContext = getBaseContext();
    	 mNetwork = new Network(this);
    }
    
    // 定位初始化
    private void locationInit(){
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1*60*1000);
		mLocClient.setLocOption(option);
		mLocClient.start();  	
    }
XsyUser mXsyUser;
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        if(intent!=null && intent.getExtras()!=null){
        	Serializable serializable = intent.getExtras().getSerializable("mXsyUser");
        	if(serializable!=null)
        		mXsyUser = (XsyUser)serializable;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mLocClient.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.app_name);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.app_name),
                       text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
    
    public void writeLog(String str){
    	Log.d("writeLog", str);
    }
}
