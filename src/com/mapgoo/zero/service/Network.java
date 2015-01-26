package com.mapgoo.zero.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.huaan.icare.pub.R;
import com.mapgoo.zero.api.PositionUtil;
import com.mapgoo.zero.api.PositionUtil.Gps;
import com.mapgoo.zero.ui.widget.QuickShPref;

/**
 * 说明：所有的网络请求
 * 
 * @author Administrator 功能：发起请求，并把返回结果广播出去 修改时间：2012年10月10日下午4:26:26
 */
public class Network {
	private static InputStream mInputStream;
	private static DefaultHttpClient mClient;
	private LocalService mContext;
	private String mIMEI;

	private Socket mSocket;
	private InputStream mSocketReader;
	private OutputStream mSocketWriter;
	public static boolean mSocketjoin = false; // 判断Socket连接是否正常，如果不正常，则不再获取位置信息
	private SharedPreferences sp;
	private String getAddressURL = "http://rgc.vip.51ditu.com/rgc?pos=%d,%d&type=1"; // 51地址解析URL
	private static final String NAMESPACE = "http://tempuri.org/";

	// 小田的地址
	// public static String SERVICE_URL =
	// "http://192.168.1.130/VisiterService/";
	// 小钟的地址
	// public static String SERVICE_URL = "http://192.168.1.131/VisitsService/";
	// 外网
	public static String SERVICE_URL;
	public static String ip; // 外网
	public static int port; // 端口号
	public static int getaddress = 1;
	public static String VersionType = "0406027";

	private Object detail;

	public static String mObjectId = "0";
	public static String mHoldId = "0";
	public static int mUpLocationWay = 2; // 位置上报的定位方式 0为GPS定位，1为GPS+基站
	public static int mSuccessUpCount = 0; // 成功上报位置信息条数
	public static int mFailUpCount = 0; // 上报失败的位置信息条数
	private int rawlevel = 1; // 电池电量
	private boolean upData;

	public Network(LocalService context) {
		mContext = context;
		
		new Thread(){
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				socketJoin();
			}
		}.start();
		
	}

	// Socket 重连方法
	public void socketJoin() {
		try {

			mSocket = new Socket(getService_ip(), getService_port());
			mSocket.setKeepAlive(true);
			mSocket.setTcpNoDelay(true);
			// 设置超时时间(毫秒)
			mSocket.setSoTimeout(10000);

			mSocketReader = mSocket.getInputStream();
			mSocketWriter = mSocket.getOutputStream();
			mContext.writeLog("---------------->socket连接成功");
			
			mSocketjoin = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			mContext.writeLog("---------------->socket连接失败");
			mSocketjoin = false;
		} catch (IOException e) {
			e.printStackTrace();
			mContext.writeLog("---------------->socket连接失败");
			mSocketjoin = false;
		}
	}

	// 断开Socket连接
	public void destroy() {
		try {
			if (mSocketReader != null) {
				mSocketReader.close();
				mSocketReader = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (mSocketWriter != null) {
				mSocketWriter.close();
				mSocketWriter = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (mSocket != null) {
				mSocket.close();
				mSocket = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static InputStream getResponse(String url) {
		try {
			HttpParams httpParameters;
			httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 8000);
			HttpConnectionParams.setSoTimeout(httpParameters, 5000);

			mClient = new DefaultHttpClient(httpParameters);
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = mClient.execute(getMethod);

			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				getMethod.abort();
				return null;
			}

			HttpEntity entry = response.getEntity();
			mInputStream = entry.getContent();

			return mInputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void clean() {
		try {
			if (mInputStream != null) {
				mInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (mClient != null) {
			mClient.getConnectionManager().shutdown();
			mClient = null;
		}
	}

	private void sendResultToApp(int type, Bundle bundle) {
		Intent intent = new Intent(LocalService.RESULTACTION);
		bundle.putInt("type", type);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		mContext.sendBroadcast(intent);
	}

	// 获取服务器IP
	private String getService_ip() {
		if (ip == null || ip.equals("")) {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			ip = sp.getString("SERVICE_IP",
					mContext.getResources().getString(R.string.service_ip));
		}
		Log.i("getService_ip", ip);
		return ip;
	}

	// 获取服务器端口
	private int getService_port() {
		if (port == 0) {
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			port = Integer.valueOf(sp.getString("SERVICE_DUANKOU", mContext
					.getResources().getString(R.string.service_port)));
		}
		Log.i("getService_port", port+"");
		return port;
	}

	
	/*
	 * 将百度定位的时间转换成标准格式2013-09-01 09:05:02
	 */
	private String formatTime(String time){
			Pattern pattern = Pattern.compile("\\s*(\\d+)-(\\d+)-(\\d+)\\s+(\\d+):(\\d+):(\\d+)\\s*");
			Matcher matcher = pattern.matcher(time);
			String ret = null;
			int y,m,d,h,min,s;

			if(matcher.find()){ 
				try {
					y =  Integer.parseInt(matcher.group(1));
					m =  Integer.parseInt(matcher.group(2));
					d =  Integer.parseInt(matcher.group(3));
					h =  Integer.parseInt(matcher.group(4));
					min =  Integer.parseInt(matcher.group(5));
					s =  Integer.parseInt(matcher.group(6));
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
				ret = String.format("%04d-%02d-%02d %02d:%02d:%02d", y,m,d,h,min,s);
				Log.d("time", ret);
			}
			return ret;
	}
	/**
	 * 上传位置
	 * 
	 * @param location
	 */
	public void uploadPos(BDLocation location) {
		mIMEI = QuickShPref.getString(QuickShPref.IEMI);
		String time = location.getTime();
		time = formatTime(time);
		if(time == null || time.length()<19){
			if(time!=null)
				Log.d("tag", "定位数据有误,不上报:"+time);
			return;
		}
		Gps gps = PositionUtil.bd09_To_Gps84(location.getLatitude(), location.getLongitude());
		
		double lon = gps.mLon;//location.getLongitude(); // 经度
		double lat =	gps.mLat; // 纬度

		/**
		 * location.getLocType() 的值 61 ： GPS定位结果 62 ： 扫描整合定位依据失败。此时定位结果无效。 63 ：
		 * 网络异常，没有成功向服务器发起请求。此时定位结果无效。 65 ： 定位缓存的结果。 161： 表示网络定位结果 162~167：
		 * 服务端定位失败
		 */
		byte f;
		if (location.getLocType() < 100) {
			f = 0x06; // GPS 6
		} else {
			f = 0x0F; // 基站 15
		}

		sp = mContext.getSharedPreferences("user", Activity.MODE_PRIVATE);
		// 获取上报方式
		SharedPreferences sp2 = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		int uplocation_way = Integer.parseInt(sp2.getString("UPLOCATION_WAY",
				String.valueOf(Network.mUpLocationWay)));

		String strFlag = sp.getString("StrFlag", "2"); // 登录方式: 1、SIM 2、IMEI
														// 3、ObjectId
		String cardNum = sp.getString("CardNum", ""); // 手机号码

//		if (uplocation_way == 1) { // 如果客户选择了只上报GPS位置信息
//			// 获取GPS获取的经纬度
//			SharedPreferences sp3 = mContext.getSharedPreferences(
//					"mapgooService", Activity.MODE_PRIVATE);
//			lon = sp3.getFloat("gpsLon", 0);
//			lat = sp3.getFloat("gpsLat", 0);
//
//			f = 0x06;
//
//			// 如果经纬度为0，则不上报
//			if (lon == 0 || lat == 0) {
//				return;
//			}
//		}

		int lonDu = (int) lon;
		float lonFen = (float) (lon - lonDu) * 60;
		int lonFenInt = (int) lonFen;
		String lonString = String.format("%03d%02d%04d", lonDu, lonFenInt,
				(int) ((lonFen - lonFenInt) * 10000));

		int latDu = (int) lat;
		float latFen = (float) (lat - latDu) * 60;
		int latFenInt = (int) latFen;
		String latString = String.format("%02d%02d%04d", latDu, latFenInt,
				(int) ((latFen - latFenInt) * 10000));

		String speed = String.format("%02d",
				Math.round(location.getSpeed() * 3.6f / 3.704f));
		int derect = Math.round(0) / 10;
		if (derect < 0) {
			derect = 0;
		}
		String direction = String.format("%02d", derect);

		StringBuffer buffer = new StringBuffer();
//		if (strFlag.equals("1")) {
//			buffer.append("*HQ200" + cardNum + ",BA&A"); // 手机sim卡号登录就上传sim卡号
//		} else {
			buffer.append("*HQ200" + mIMEI + ",BA&A"); // imei号登录就上传imei号
//		}

		buffer.append(time.subSequence(11, 13));
		buffer.append(time.subSequence(14, 16));
		buffer.append(time.subSequence(17, 19));
		buffer.append(latString);
		buffer.append(lonString);
		buffer.append((char) f);
		buffer.append(speed);
		buffer.append(direction);
		buffer.append(time.subSequence(8, 10));
		buffer.append(time.subSequence(5, 7));
		buffer.append(time.subSequence(2, 4));

		// 加入电池电量协议
		buffer.append("&M");
		if (rawlevel <= 0 || rawlevel >= 100) { // <= 0设为0.1%，大于或等于100设为99.9%
			buffer.append(rawlevel <= 0 ? "001" : "999");
		} else {
			if (rawlevel > 0 && rawlevel <= 9) { // 如果在0--9，在数值前面加0
				buffer.append("0" + String.valueOf(rawlevel) + "0");
			} else {
				buffer.append(String.valueOf(rawlevel) + "0");
			}
		}

		buffer.append('#');
		// begin 将上报的相关信息写入日志
		String uplocationType = (location.getLocType() < 100) ? "GPS" : "基站";
		mContext.writeLog("定位方式:" + uplocationType + ", 经度:" + lon + ", 纬度:"
				+ lat+",时间:"+time);
		// 往日志文件里写入上报的协议内容
		mContext.writeLog("上报的协议内容为:" + buffer.toString());
		// end 将上报的相关信息写入日志
		// *HQ200868159000227640,BA&A171709222994821135478590000290912&M950#
		// 获取上报不成功的数据
		String upLocationData = sp.getString("UPLOCATION_DATA", "");
		if (mSocketWriter != null) {
			try {
				mSocketWriter.write(buffer.toString().getBytes());
				mContext.writeLog("socket上报位置成功");
				// 盲区补报
				if (!upLocationData.equals("")) {
					if (!upData) { // 如果补报已完成，才进行下一轮补报
						UpLocationThread thread = new UpLocationThread(
								upLocationData);
						// 开启线程，补报未上报成功的数据
						thread.start();
					}
				}
				mSuccessUpCount++; // 统计成功上报的条数
			} catch (Exception e) {
				e.printStackTrace();

				mContext.writeLog("socket上报位置失败，重新连接");
				mFailUpCount++; // 统计上报失败的条数

				// try {
				// mSocketReader.close();
				// mSocketWriter.close();
				// mSocket.close();
				// } catch (IOException ioe) {
				// ioe.printStackTrace();
				// }
				// 断开socket连接
				destroy();
				// 调用socket重连方法
				socketJoin();
				try {
					mSocketWriter.write(buffer.toString().getBytes());
					mContext.writeLog("socket补报位置成功");
				} catch (Exception e1) {
					if (!upData) {
						// 添加盲区补报数据
						if (!upLocationData.equals("")) {
							// 如果超过一百条，则去掉首条记录
							if (upLocationData.split("\\|\\|").length > 100) {
								int index = upLocationData.indexOf("||") + 2;
								upLocationData = upLocationData
										.substring(index);
							}
						}
						// 用"||"来分隔数据
						upLocationData += buffer.toString() + "||";

						SharedPreferences.Editor editor = sp.edit();
						editor.putString("UPLOCATION_DATA", upLocationData);
						editor.commit(); // 提交
					}
				}

			}
		} else {
			// 调用socket重连方法
			socketJoin();
			try {
				mSocketWriter.write(buffer.toString().getBytes());
				mContext.writeLog("socket补报位置成功");
			} catch (Exception e) {
				if (!upData) {
					// 添加盲区补报数据
					if (!upLocationData.equals("")) {
						// 如果超过一百条，则去掉首条记录
						if (upLocationData.split("\\|\\|").length > 100) {
							int index = upLocationData.indexOf("||") + 2;
							upLocationData = upLocationData.substring(index);
						}
					}
					// 用"||"来分隔数据
					upLocationData += buffer.toString() + "||";

					SharedPreferences.Editor editor = sp.edit();
					editor.putString("UPLOCATION_DATA", upLocationData);
					editor.commit(); // 提交
				}
			}

		}

	}

	// 开启线程，用来补报未上报成功的数据
	private class UpLocationThread extends Thread {

		String upLocation;

		public UpLocationThread(String upLocationData) {
			upLocation = upLocationData;
		}

		@Override
		public void run() {
			upData = true;
			String[] arrayUpLocation = upLocation.split("\\|\\|");
			int length = arrayUpLocation.length;
			for (int i = 0; i < length; i++) {
				try {
					sleep(2000);
					if (mSocketWriter != null) {
						mSocketWriter.write(arrayUpLocation[i].getBytes());
					} else {
						// 调用socket重连方法
						socketJoin();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
			upData = false;

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("UPLOCATION_DATA", "");
			editor.commit(); // 提交

		}

	};

	/**
	 * 根据URL获取到数据并转化为String，从而避免出现乱码问题
	 */
	public static String getTax(String uri) {
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet(uri);
		request.setHeader("Connection", "close");
		HttpResponse response;
		String data = "";
		try {
			response = client.execute(request);
			data = EntityUtils.toString(response.getEntity()).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
