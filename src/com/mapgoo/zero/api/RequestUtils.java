package com.mapgoo.zero.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.common.StringUtils;

public class RequestUtils {

	public static JsonObjectRequest getJsonObjectRequest(final int method, String url, final Map<String, String> headerParams,
			final Map<String, String> reqParams, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		
		// 如果是get请求，使用请求参数拼接url
		if (method == Method.GET && reqParams != null)
			url = getURL(url, reqParams);
		
		Log.d("url", url);
		if (jsonRequest != null)
			Log.d("jsonRequest", jsonRequest.toString());
		
		
		JsonObjectRequest jsonObjectRequest =  new JsonObjectRequest(method, url, jsonRequest == null ? null : jsonRequest, listener, errorListener) {

			// 如果是POST请求，则设置请求参数
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				if (method == Method.POST && reqParams != null)
					return reqParams;

				return super.getParams();
			}

			// TIPS !!!
			// 设置请求头
			// 统一设置token，不用再每次传参时，传入token
			// 当token失效时，需调用setToken，设置新的token
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> reqHeaderParams = new HashMap<String, String>();
				// 本来就是post-json请求，不用重复设置
				// reqHeaderParams.put("Content-Type", "application/json");

//				if (!TextUtils.isEmpty(mAppKey))
//					reqHeaderParams.put("AppKey", mAppKey);

				if (!TextUtils.isEmpty(mToken))
					reqHeaderParams.put("Authorization", mToken);

				if (headerParams != null)
					reqHeaderParams.putAll(headerParams);
				
				Log.d("reqHeaderParams", new JSONObject(reqHeaderParams).toString());

				return reqHeaderParams;
			}
		};
		
		// 设置超时时常以及重试次数
		// TIPS，这里暂时设置超时次数仅为一次，即一次超时后不再重新请求
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));
		
		return jsonObjectRequest;

	}

	private static String mToken;

	/**
	 * 概述: 设置请求token
	 * 
	 * @auther yao
	 * @param newToken
	 */
	public static void setToken(String newToken) {
		if (TextUtils.isEmpty(newToken))
			return;

		mToken = newToken;
	}

	private static String mAppKey;

	/**
	 * 概述: 获取AppKey
	 * 
	 * @auther yao
	 * @param context
	 * @return
	 */
	public static String getAppKey(Context context) {
		String result = "app";
//		XmlResourceParser parser = context.getResources().getXml(R.xml.appkey_cfg);
//		try {
//			int event = parser.getEventType();
//			while (event != XmlPullParser.END_DOCUMENT) {
//				if ((event == XmlPullParser.START_TAG) && "appkey".equals(parser.getName()))
//					result = parser.nextText();
//
//				event = parser.next(); // 进入下一个元素并触发相应事件
//			}
//		} catch (XmlPullParserException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			parser.close();
//		}

		return result;
	}

	public static void setAppKey(String appKey) {
		if (!TextUtils.isEmpty(appKey))
			mAppKey = appKey;
	}

	/**
	 * 概述: GET请求-URL拼接
	 * 
	 * @auther yao
	 * @param url
	 * @param params
	 * @return
	 */
	private static String getURL(String url, Map<String, String> params) {

		StringBuilder sb = new StringBuilder();
		sb.append(url).append("?");

		if (params != null && params.size() != 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {

				// 如果请求参数中有中文，需要进行URLEncoder编码
				try {
					sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					Log.e("RequestError", "GET请求, 参数编码错误");
					e.printStackTrace();
				}
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1); // del last "&"
		}

		return sb.toString();
	}
}