package com.mapgoo.zero.api;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.mapgoo.zero.MGApp;
import com.mapgoo.zero.bean.PatrolBasicInfo;
import com.mapgoo.zero.bean.ServiceOrderSubmitInfo;
import com.mapgoo.zero.bean.VolunteerOrderSubmitInfo;
import com.mapgoo.zero.ui.widget.QuickShPref;
import com.mapgoo.zero.utils.ImageUtils;

/**
 * 概述: API客户端接口：用于访问网络数据 <br>
 *       
 *       TIPS 暂时以json为请求体的格式数据提交
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
public class ApiClient {

	private static Listener<JSONObject> mListener;
	private static ErrorListener mErrorListener;
	private static onReqStartListener mOnStartListener;
	public  static final boolean isDebuge = false;
	
	/**
	 * 概述: 每个activity请求网络之前必须要先设置Listeners <br>
	 * 用于每个请求成功或者失败的回调 <br>
	 * 
	 * @auther yao
	 * @param listener
	 * @param errorListener
	 */
	public static void setListeners(onReqStartListener onStartListener, Listener<JSONObject> listener, ErrorListener errorListener) {
		mOnStartListener = onStartListener;
		mListener = listener;
		mErrorListener = errorListener;
	}

	/**
	 * 概述: 请求开始的回调监听器
	 * 
	 * @author yao
	 * @version 1.0
	 * @created 2014年11月8日
	 */
	public interface onReqStartListener {
		/**
		 * 概述: 请求开始的回调方法
		 * 
		 * @auther yao
		 * @param reqCode
		 *            请求的reqCode
		 */
		public void onReqStart();
	}

	/**
	 * 概述: GET请求
	 * 
	 * @auther yao
	 * @param reqParams
	 *            请求参数
	 */
	private static void _GET(String url, Map<String, String> headerParams, Map<String, String> reqParams) {
		if (mListener != null && mErrorListener != null) {
			if(mOnStartListener != null)
				mOnStartListener.onReqStart(); // 请求开始的回调

			MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.GET, url, headerParams, reqParams, null, mListener,
					mErrorListener));
		}
	}
	
	private static void _GET_WITH_LISTENERS(String url, Map<String, String> headerParams, Map<String, String> reqParams,
			onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {

		if (responseListener != null && errorListener != null) {
			if (reqStartListener != null)	// 这个包容性太棒了，不用监听的话，我可以直接传null了
				reqStartListener.onReqStart(); // 请求开始的回调

			MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.GET, url, headerParams, reqParams, null, responseListener,
					errorListener));
		}
	}
	/**
	 * 概述: POST请求
	 * 
	 * @auther yao
	 * @param reqParams
	 *            POST请求参数/Form参数
	 * @param reqJsonObject
	 *            POST请求(Body) 暂时设置为：Content-Type=application/json
	 */
	private static void _POST(String url, Map<String, String> headerParams, Map<String, String> reqParams, Map<String, Object> reqBodyParams) {
		if (mListener != null && mErrorListener != null) {
			if(mOnStartListener != null)
				mOnStartListener.onReqStart(); // 请求开始的回调

			JSONObject reqJsonObject = null;
			if (reqBodyParams != null)
				reqJsonObject = new JSONObject(reqBodyParams);

			MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams,
					reqJsonObject == null ? null : reqJsonObject, mListener, mErrorListener));
		}
	}

	/**
	 * 概述: POST请求
	 * 
	 * @auther yao
	 * @param reqParams
	 *            POST请求参数/Form参数
	 * @param reqJsonObject
	 *            POST请求(Body) 暂时设置为：Content-Type=application/json
	 */
	private static void _POST_AftarOnReqStart(String url, Map<String, String> headerParams, Map<String, String> reqParams,
			Map<String, Object> reqBodyParams) {
		if (mListener != null && mErrorListener != null) {

			JSONObject reqJsonObject = new JSONObject(reqBodyParams);

			MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams, reqJsonObject,
					mListener, mErrorListener));
		}
	}
	
	/**
	 * 概述: POST请求
	 * 
	 * @auther yao
	 * @param reqParams
	 *            POST请求参数/Form参数
	 * @param reqJsonObject
	 *            POST请求(Body) 暂时设置为：Content-Type=application/json
	 */
	private static void _POST_WITH_LISTENERS(String url, Map<String, String> headerParams, Map<String, String> reqParams,
			Map<String, Object> reqBodyParams, onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {
		if (responseListener != null && errorListener != null) {
			if (reqStartListener != null)
				reqStartListener.onReqStart(); // 请求开始的回调

			JSONObject reqJsonObject = null;

			if (reqBodyParams != null)
				reqJsonObject = new JSONObject(reqBodyParams);

			MyVolley.addToRequestQueue(RequestUtils.getJsonObjectRequest(Method.POST, url, headerParams, reqParams,
					reqJsonObject == null ? null : reqJsonObject, responseListener, errorListener));
		}
	}
	

	/**
	 * 概述: 请求短信验证码
	 * 
	 * @auther yao
	 * @param appKey
	 * @param phoneNum
	 *            手机号
	 * @param reqCode
	 *            请求号
	 */
	public static void reqVerifyCode(String appKey, String phoneNum) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("appkey", appKey);
		reqParams.put("sim", phoneNum);

		_GET(URLs.SMS_VERIFY, null, reqParams);
	}

	/**
	 * 概述: 用户注册
	 * 
	 * @auther yao
	 * @param phoneNum
	 *            手机号
	 * @param verifyCode
	 *            验证码
	 * @param encodedPwd
	 *            密码/密文
	 * @param reqCode
	 *            请求号
	 */
	public static void userRegister(String phoneNum, String verifyCode, String encodedPwd) {
		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("mobile", phoneNum);
		reqBodyParams.put("verifyCode", verifyCode);
		reqBodyParams.put("pwd", encodedPwd);

		_POST(URLs.USER_REGISTER, null, null, reqBodyParams);
	}

	/**
	 * 概述: 登录
	 * 
	 * @auther yao
	 * @param telNum
	 *            电话号码
	 * @param encodedPwd
	 *            密码/密文
	 */
	public static void login(String telNum, String encodedPwd) {
		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("uid", telNum);
		reqBodyParams.put("pwd", encodedPwd);

		_POST(URLs.USER_LOGIN, null, null, reqBodyParams);
	}
	
	/**
	 * 概述: 登录接口，内部使用，当token失效是重新用来重新获取token
	 * 
	 * @auther yao
	 * @param telNum
	 * @param encodedPwd
	 * @param responseListener
	 * @param errorListener
	 */
	public static void loginInternel(String telNum, String encodedPwd, onReqStartListener reqStartListener, Listener<JSONObject> responseListener, ErrorListener errorListener) {
		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("uid", telNum);
		reqBodyParams.put("pwd", encodedPwd);

		_POST_WITH_LISTENERS(URLs.USER_LOGIN, null, null, reqBodyParams, reqStartListener, responseListener, errorListener);
	}
	

	/**
	 * 概述: 获取帐号下设备列表
	 * 
	 * @auther yao
	 * @param userId
	 *            uid
	 * @param authToken
	 *            令牌
	 */
	public static void getUserObjList(int userId, String authToken) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);

		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("uid", String.valueOf(userId));

		Log.d("uid", String.valueOf(userId));
		Log.d("Authorization", authToken);

		_GET(URLs.UserObjList, reqHeaderParams, reqParams);
	}

	public static void isIMEIExists(String authToken, String imei) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("imei", imei);

		_GET(URLs.isIMEIExists, reqHeaderParams, reqParams);

	}

	public static void submitWearerInfo(String authToken, String imei, String sim, String mHumanName, String mBirthday,
			boolean sex, double mHeight, double mWeight, Bitmap avatarBitmap) {

		if (mListener != null && mErrorListener != null) {
			if (mOnStartListener != null)
				mOnStartListener.onReqStart(); // 请求开始的回调

			Map<String, String> reqHeaderParams = new HashMap<String, String>();
			reqHeaderParams.put("Authorization", authToken);
			Log.d("Authorization", authToken);

			String avatarString = ImageUtils.img2Base64(MGApp.pThis, avatarBitmap);

			Map<String, Object> reqBodyParams = new HashMap<String, Object>();
			reqBodyParams.put("IMEI", imei);
			reqBodyParams.put("SIM", sim);
			reqBodyParams.put("HumanName", mHumanName);
			reqBodyParams.put("Birthday", mBirthday);
			reqBodyParams.put("Sex", sex);
			reqBodyParams.put("Height", mHeight);
			reqBodyParams.put("Weight", mWeight);
			reqBodyParams.put("Avatar", avatarString);

			Log.d("json", new JSONObject(reqBodyParams).toString());

			_POST_AftarOnReqStart(URLs.ObjectBasic, reqHeaderParams, null, reqBodyParams);
		}
	}

	/**
	 * 概述: 获取/查看佩戴者信息
	 *
	 * @auther yao
	 * @param authToken
	 * @param userId
	 * @param mObjectId
	 */
	public static void getWearerInfo(String authToken, int userId, String mObjectId) {

		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("userId", String.valueOf(userId));
		reqParams.put("ObjectId", mObjectId);

		_GET(URLs.ObjectBasic, reqHeaderParams, reqParams);
	}

	/**
	 * 概述: 修改/新增 静音时段
	 *
	 * @auther yao
	 * @param authToken
	 * @param objectID
	 * @param mMuteid
	 * @param mMutePeriodCMDVal
	 */
	public static void submitMutePeriod(String authToken, String objectID, int mMuteid, String mMutePeriodCMDVal) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("objectid", objectID);
		reqBodyParams.put("muteid", String.valueOf(mMuteid));
		reqBodyParams.put("value", mMutePeriodCMDVal);

		Log.d("objectid", objectID);
		Log.d("muteid", mMuteid + "");
		Log.d("value", mMutePeriodCMDVal);

		_POST(URLs.UpdateObjectMuteTime, reqHeaderParams, null, reqBodyParams);
	}
	
	/**
	 * 概述: 删除静音条目
	 *
	 * @auther yao
	 * @param authToken
	 * @param objectID
	 * @param muteID
	 */
	public static void delMuteTimeItem(String authToken, String objectID, String muteID) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);
		
		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("objectid", objectID);
		reqBodyParams.put("muteid", String.valueOf(muteID));

		Log.d("objectid", objectID);
		Log.d("muteid", muteID + "");

		_POST(URLs.DelObjectMuteTime, reqHeaderParams, null, reqBodyParams);
	}

	/**
	 * 概述: 设置与佩戴者的关系
	 *
	 * @auther yao
	 * @param authToken
	 * @param userId
	 * @param userMobile
	 * @param mIMEI
	 * @param relationship
	 * @param isAdmin
	 */
	public static void submitRelationship(String authToken, int userId, String userMobile, String mIMEI, String relationship,
			boolean isAdmin) {

		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("userid", userId);
		reqBodyParams.put("username", userMobile);
		reqBodyParams.put("imei", mIMEI);
		reqBodyParams.put("relation", relationship);
		reqBodyParams.put("ismanagement", isAdmin);
		
		_POST(URLs.UserObjRelation, reqHeaderParams, null, reqBodyParams);
	}

	/**
	 * 概述: 获取设备家庭成员信息
	 *
	 * @auther yao
	 * @param authToken
	 * @param mObjectId
	 */
	public static void getFamilyMembers(String authToken, String mObjectId) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("objectid", mObjectId);

		_GET(URLs.ObjectMember, reqHeaderParams, reqParams);
	}

	/**
	 * 概述: 设置用户SOS等级
	 *
	 * @auther yao
	 * @param authToken
	 * @param userId
	 * @param mObjectId
	 * @param sosLevel
	 */
	public static void setUserObjSOS(String authToken, String userId, String mObjectId, int sosLevel) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("userid", userId);
		reqBodyParams.put("objectid", mObjectId);
		reqBodyParams.put("soslevel", sosLevel);

		_POST(URLs.UpdateUserObjSOS, reqHeaderParams, null, reqBodyParams);

	}

	/**
	 * 概述: 设置用户白名单权限
	 *
	 * @auther yao
	 * @param authToken
	 * @param userId
	 * @param mObjectId
	 * @param b
	 */
	public static void setUserObjWhiteList(String authToken, String userId, String mObjectId, boolean isWhiteList) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("userid", userId);
		reqBodyParams.put("objectid", mObjectId);
		reqBodyParams.put("iswhitelist", isWhiteList);

		_POST(URLs.UpdateUserObjWhiteList, reqHeaderParams, null, reqBodyParams);

	}
	
	/**
	 * 概述: 移除家庭成员
	 *
	 * @auther yao
	 * @param authToken
	 * @param userID
	 * @param mObjectId
	 */
	public static void removeUserObjMember(String authToken, String userId, String mObjectId) {
		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("userid", userId);
		reqBodyParams.put("objectid", mObjectId);

		_POST(URLs.ObjectMember, reqHeaderParams, null, reqBodyParams);

	}

	/**
	 * 概述: 
	 *
	 * @auther yao
	 * @param authToken
	 * @param userId
	 * @param objectId
	 * @param phoneNum
	 */
	public static void reqUserMemberInvite(String authToken, int userId, String objectId, String phoneNum) {

		Map<String, String> reqHeaderParams = new HashMap<String, String>();
		reqHeaderParams.put("Authorization", authToken);
		Log.d("Authorization", authToken);

		Map<String, Object> reqBodyParams = new HashMap<String, Object>();
		reqBodyParams.put("userid", userId);
		reqBodyParams.put("objectid", objectId);
		reqBodyParams.put("mobile", phoneNum);

		_POST(URLs.InviteUserRelation, reqHeaderParams, null, reqBodyParams);
	}

	public static InputStream getResponse(String url) {
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
	
	/**
	 * 概述: get user name
	 * 
	 * @auther liu
	 * @param string of imei
	 * @param reqStartListener
	 * @param responseListener
	 * @param errorListener
	 */
	public static void getUserName(String imei,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("imei", imei);
		
		_GET_WITH_LISTENERS(URLs.getusername, null, reqParams, reqStartListener, responseListener, errorListener);
	}
	public static void getMessageList(int peopleNo,int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("userId", peopleNo+"");
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.NoticeBasic, null, reqParams, reqStartListener, responseListener, errorListener);
	}
	
	public static void getLoarenInfoList(String peopleNo,int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		if(isDebuge)
			reqParams.put("peopleNo", "0");
		else
			reqParams.put("peopleNo", peopleNo);
		
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.LaorenInfoList, null, reqParams, reqStartListener, responseListener, errorListener);
	}
	public static void getFuwuList(String peopleNo,int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		if(isDebuge)
			reqParams.put("serviceTypes", "");
		else
			reqParams.put("serviceTypes", peopleNo);
		
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.FuwuList, null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void getShangpinList(String serviceId,int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		if(isDebuge)
			reqParams.put("serviceId", "8");
		else
			reqParams.put("serviceId", serviceId);
		
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.ShangpinList, null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	
	public static void postServiceOrderSubmit(ServiceOrderSubmitInfo info,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();

		reqParams.put("BusinessID", info.BusinessID);
		reqParams.put("HoldID", info.HoldID);
		reqParams.put("ObjectID", info.ObjectID);
		reqParams.put("OrderTime", info.OrderTime);
		
		reqParams.put("PeopleNo", info.PeopleNo);
		reqParams.put("Remark", info.Remark);
		reqParams.put("ServiceFee", info.ServiceFee);
		reqParams.put("Serviceltem", info.Serviceltem);
		
		reqParams.put("UserID", info.UserID);
		
		
		_POST_WITH_LISTENERS(URLs.ServiceOrderSubmit, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void postVolunteerOrderSubmit(VolunteerOrderSubmitInfo info,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();

		reqParams.put("BusinessID", info.BusinessID);
		reqParams.put("HoldID", info.HoldID);
		reqParams.put("ObjectID", info.ObjectID);
		reqParams.put("OrderTime", info.OrderTime);
		
		reqParams.put("PeopleNo", info.PeopleNo);
		reqParams.put("Remark", info.Remark);
		reqParams.put("OrderContent", info.OrderContent );
		reqParams.put("UserID", info.UserID);
		
		_POST_WITH_LISTENERS(URLs.VolunteerOrderSubmit, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}	
	public static void getZhiyuanzheList(int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.VolunteerBasic, null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	
	public static void getOrderFormList(String peopleNo, int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("peopleNo", peopleNo);
		reqParams.put("p", pager+"");
		reqParams.put("pRowCount", rawcount+"");
		
		_GET_WITH_LISTENERS(URLs.OrderBasic, null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void getOrderFormDetailList(int  recId, int pager,int rawcount,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("recId", recId+"");
		_GET_WITH_LISTENERS(URLs.OrderBasic, null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void getPatrolBasic(String  humanId, String peopleNo,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		if(humanId!=null && peopleNo!=null){
			Map<String, String> reqParams = new HashMap<String, String>();
			reqParams.put("humanId", humanId);
			reqParams.put("peopleNo", peopleNo);
			_GET_WITH_LISTENERS(URLs.PatrolBasic, null, reqParams, reqStartListener, responseListener, errorListener);	
		}
	}
	public static void setPatrolSign(int  sign, int humanId, int peopleNo,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("sign", sign);
		reqParams.put("humanId", humanId);
		reqParams.put("peopleNo", peopleNo);
		_POST_WITH_LISTENERS(URLs.PatrolSign, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void postPatrolBasic(PatrolBasicInfo info,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("HumanID", info.HumanID);
		reqParams.put("Hypotension", info.Hypotension);
		reqParams.put("Hypertension", info.Hypertension);
		reqParams.put("ListeningExpress", info.ListeningExpress);
		reqParams.put("Eating", info.Eating);
		reqParams.put("Action", info.Action);
		reqParams.put("Medical", info.Medical);
		reqParams.put("Disease", info.Disease);
		reqParams.put("PersonalHygiene", info.PersonalHygiene);
		reqParams.put("DressHygiene", info.DressHygiene);
		reqParams.put("SmellHygiene", info.SmellHygiene);
		reqParams.put("AroundHygiene", info.AroundHygiene);
		reqParams.put("ResidentialHygiene", info.ResidentialHygiene);
		reqParams.put("Indoor", info.Indoor);
		reqParams.put("Outdoor", info.Outdoor);
		reqParams.put("Neighborhood", info.Neighborhood);
		reqParams.put("GarbageCollection", info.GarbageCollection);
		reqParams.put("HousesDamaged", info.HousesDamaged);
		reqParams.put("CoalGasSafety", info.CoalGasSafety);
		reqParams.put("CoalFireSafety", info.CoalFireSafety);
		reqParams.put("ConduitSafety", info.ConduitSafety);
		reqParams.put("ApplianceSafety", info.ApplianceSafety);
		reqParams.put("CircuitSafety", info.CircuitSafety);
		reqParams.put("Autism", info.Autism);
		reqParams.put("Memory", info.Memory);
		reqParams.put("Emotion", info.Emotion);
		reqParams.put("Thinking", info.Thinking);
		reqParams.put("Pressure", info.Pressure);
		reqParams.put("FamilySafety", info.FamilySafety);
		reqParams.put("SafetyImg1", info.SafetyImg1);
		reqParams.put("SafetyImg2", info.SafetyImg2);
		reqParams.put("SafetyImg3", info.SafetyImg3);
		reqParams.put("SafetyImg4", info.SafetyImg4);
		
		_POST_WITH_LISTENERS(URLs.PatrolBasic, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	
	public static void SetNoticeRead(int noticeId,int userId,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("noticeId", noticeId);
		reqParams.put("userId", userId);
		_POST_WITH_LISTENERS(URLs.SetNoticeRead, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void OrderCancel(int recId,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("recId", recId);
		_POST_WITH_LISTENERS(URLs.OrderCancel, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}	
	public static void OrderComplete(int recId,int comment,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("recId", recId);
		reqParams.put("comment", comment);
		_POST_WITH_LISTENERS(URLs.OrderComplete, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}		
	public static void getTracks(String objectId,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, String> reqParams = new HashMap<String, String>();
		reqParams.put("objectId", objectId);
		_GET_WITH_LISTENERS(URLs.Tracks, null, reqParams, reqStartListener, responseListener, errorListener);	
	}
	public static void setSimTracks(int objectId,String sim,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("objectId", objectId);
		reqParams.put("sim", sim);
		_POST_WITH_LISTENERS(URLs.Tracks, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}	
	public static void UpdateUserPassword(int userid,String old,String newpass,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		String imei = QuickShPref.getString(QuickShPref.IEMI);
		reqParams.put("OldPassword", old);
		reqParams.put("NewPassword", newpass);
		reqParams.put("UserID", userid);
		_POST_WITH_LISTENERS(URLs.UpdateUserPassword, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}	
	public static void UpdateUserImage(int peopleNo,String avatar,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("peopleNo", peopleNo);
		if(avatar == null)
			return;
		reqParams.put("avatar", avatar);
		_POST_WITH_LISTENERS(URLs.UpdateUserImage, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
	public static void UpdatePosition(int peopleNo,BDLocation location,onReqStartListener reqStartListener, Listener<JSONObject> responseListener,
			ErrorListener errorListener) {
			Map<String, Object> reqParams = new HashMap<String, Object>();
			reqParams.put("peopleNo", peopleNo);
			reqParams.put("GPSTime", location.getTime());
			reqParams.put("Lon", location.getLongitude());
			reqParams.put("Lat", location.getLatitude());
			reqParams.put("Speed", location.getSpeed());
			reqParams.put("Direct", 0);
		_POST_WITH_LISTENERS(URLs.Position, null,null, reqParams, reqStartListener, responseListener, errorListener);		
	}
}
