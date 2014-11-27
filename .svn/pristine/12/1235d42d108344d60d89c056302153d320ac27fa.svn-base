package com.mapgoo.snowleopard.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * 概述: 扩展的/自定义的 RequestUtils <br>
 * 用于获取各种封装后的Request <br>
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
public class RequestUtils {

	public static JsonObjectRequest getJsonObjectRequest(final int method, String url, final Map<String, String> headerParams,
			final Map<String, String> reqParams, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {

		// 如果是get请求，使用请求参数拼接url
		if (method == Method.GET && reqParams != null)
			url = getURL(url, reqParams);

		return new JsonObjectRequest(method, url, jsonRequest == null ? null : jsonRequest, listener, errorListener) {

			// 如果是POST请求，则设置请求参数
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				if (method == Method.POST && reqParams != null)
					return reqParams;

				return super.getParams();
			}

			// 设置请求头
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if (headerParams != null)
					return headerParams;

				return super.getHeaders();
			}
		};

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