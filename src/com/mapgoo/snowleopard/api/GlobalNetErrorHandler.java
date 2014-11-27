package com.mapgoo.snowleopard.api;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.j256.ormlite.dao.Dao;
import com.mapgoo.snowleopard.MGApp;
import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.api.ApiClient.onReqStartListener;
import com.mapgoo.snowleopard.bean.User;
import com.mapgoo.snowleopard.utils.StringUtils;
import com.mapgoo.snowleopard.widget.MGProgressDialog;
import com.mapgoo.snowleopard.widget.MyToast;

/**
 * 概述: 自定义Error处理、主要处理：拦截401(Authorization Error)错误， 重获token的机制
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月25日
 */
public class GlobalNetErrorHandler implements ErrorListener {
	
	private static GlobalNetErrorHandler mInstance = null;
	private GlobalNetErrorHandler(){}
	
	/**
	 * 概述: 单例、全局唯一
	 *
	 * @auther yao
	 * @return
	 */
	public static GlobalNetErrorHandler getInstance(Context context, User curUser, MGProgressDialog progressDialog) {
		mContext = context;
		mProgressDialog = progressDialog;
		mCurUser = curUser;

		if (mInstance == null) {

			synchronized (GlobalNetErrorHandler.class) {
				if (mInstance == null)
					mInstance = new GlobalNetErrorHandler();
			}
		}

		return mInstance;
	}
	
	private static User mCurUser;
	private static MGProgressDialog mProgressDialog;
	private static Context mContext;

	@Override
	public void onErrorResponse(VolleyError error) {

		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();

		VolleyErrorHelper.handleError(error, mContext, mCurUser); // 拦截/处理 error信息
	}

	public static class VolleyErrorHelper {
		
		public static void handleError(VolleyError error, Context context, User curUser) {
			String errorMsg = getMessage(error, context, curUser);

			if (!StringUtils.isEmpty(errorMsg))
				MyToast.getInstance(mContext).toastMsg(errorMsg);
		}

		/**
		 * Returns appropriate message which is to be displayed to the user
		 * against the specified error object.
		 * 
		 * @param error
		 * @param context
		 * @return
		 */
		public static String getMessage(VolleyError error, Context context, User curUser) {
			if (error instanceof TimeoutError)
				return context.getResources().getString(R.string.network_timeout_req_again);
			else if (isServerProblem(error))
				return handleServerError(error, context, curUser);
			else if (isNetworkProblem(error))
				return context.getResources().getString(R.string.bad_network);

			return context.getResources().getString(R.string.bad_network);
		}

		/**
		 * Determines whether the error is related to network
		 * 
		 * @param error
		 * @return
		 */
		private static boolean isNetworkProblem(VolleyError error) {
			return (error instanceof NetworkError) || (error instanceof NoConnectionError);
		}

		/**
		 * Determines whether the error is related to server
		 * 
		 * @param error
		 * @return
		 */
		private static boolean isServerProblem(VolleyError error) {
			return (error instanceof ServerError) || (error instanceof AuthFailureError);
		}

		/**
		 * Handles the server error, tries to determine whether to show a stock
		 * message or to show a message retrieved from the server.
		 * 
		 * @param err
		 * @param context
		 * @return
		 */
		private static String handleServerError(VolleyError error, final Context context, final User curUser) {

			NetworkResponse response = error.networkResponse;

			if (response != null) {
				switch (response.statusCode) {
//				case 404:
//				case 422:
				case 401:
					
					final MGProgressDialog progressDialog = new MGProgressDialog(context);
					progressDialog.setCancelable(true);
					
					// 重新获取token
					ApiClient.loginInternel(curUser.getUserMobile(), curUser.getUserPwd(), new onReqStartListener() {

						@Override
						public void onReqStart() {

							if (progressDialog != null && !progressDialog.isShowing()) {
								progressDialog.setMessage(context.getText(R.string.token_expire_and_reget).toString());
								progressDialog.show();
							}

						}
					}, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {

							try {
								User user = JSON.parseObject(response.getJSONObject("result").toString(), User.class);
								
								curUser.setAuthToken(user.getAuthToken());
								curUser.setLoginDate(user.getLoginDate());
								curUser.setLoginCount(user.getLoginCount());

								Dao<User, String> userDaoUser = User.getDao(MGApp.getHelper());

								// 用户资料入库操作
								if (userDaoUser.queryForId(curUser.getUserMobile()) != null)
									// 存在？->更新
									userDaoUser.update(curUser);
								else
									// 不存在？->添加
									userDaoUser.createIfNotExists(curUser);
								

								if (progressDialog != null && progressDialog.isShowing())
									progressDialog.dismiss();

								MyToast.getInstance(context).toastMsg(context.getText(R.string.token_reget_success_and_do_your_stuff_again));

							} catch (SQLException e) {
								e.printStackTrace();
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

					}, GlobalNetErrorHandler.getInstance(context, curUser, null));
					
					return "";
					
				default:
					return context.getResources().getString(R.string.bad_network);
				}
			}
			
			return context.getResources().getString(R.string.bad_network);
		}
	}
}