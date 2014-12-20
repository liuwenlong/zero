package com.mapgoo.zero.ui;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.j256.ormlite.dao.Dao;
import com.mapgoo.zero.MGApp;
import com.mapgoo.zero.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.RequestUtils;
import com.mapgoo.zero.bean.User;
import com.mapgoo.zero.bean.XsyUser;
import com.mapgoo.zero.ui.widget.EditTextView;
import com.mapgoo.zero.ui.widget.MGProgressDialog;
import com.mapgoo.zero.ui.widget.QuickShPref;
import com.mapgoo.zero.utils.CryptoUtils;
import com.mapgoo.zero.utils.LoadPref;
import com.mapgoo.zero.utils.PhoneUtils;
import com.mapgoo.zero.utils.SoftInputUtils;

public class LoginActivity extends BaseActivity implements ErrorListener, Listener<JSONObject>, onReqStartListener {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_login);
	}

	private Context mContext;
	// 自定义的请求码 标示当前是哪一个请求
	private int reqCode = -1;
	private final int REQ_LOGIN = 999;
	
	@Override
	public void initData(Bundle savedInstanceState) {
		mContext = this;

		// 初始化loading dialog
		mProgressDialog = new MGProgressDialog(mContext);
		mProgressDialog.setCancelable(true);
		
		GlobalNetErrorHandler.getInstance(mContext, null, null);
	}
	
	// TIPS 注意设置监听器
	@Override
	protected void onResume() {
		super.onResume();

		// 设置Api回调监听
		ApiClient.setListeners(this, this, this);
	}

	private EditTextView et_tel_num;
	private EditTextView et_pwd;
	private CheckBox cb_see_pwd;

	@Override
	public void initViews() {

		et_tel_num = (EditTextView) findViewById(R.id.et_tel_num);
		et_tel_num.setSelection(et_tel_num.getText().length());
		et_pwd = (EditTextView) findViewById(R.id.et_pwd);
		
		if(QuickShPref.getString(QuickShPref.PASS_WORD)!=null)
			et_pwd.setText(QuickShPref.getString(QuickShPref.PASS_WORD));
			
		et_pwd.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					handleLogin();
				}
				return true;
			}
		});

		// 密码是否可见
		cb_see_pwd = (CheckBox) findViewById(R.id.cb_see_pwd);
		cb_see_pwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked)
					et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				else
					et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				
				SoftInputUtils.requestFocus(mContext, et_pwd);
			}
		});
		getUserNmae();
	}

	private void getUserNmae(){
		ApiClient.getUserName(getIMEI(), new onReqStartListener(){
			public void onReqStart() {
				getmProgressDialog().setMessage("正在获取用户名");
				getmProgressDialog().show();
			}},
			new Listener<JSONObject>(){
				public void onResponse(JSONObject response) {
					Log.d("getUserName", response.toString());
					if (response.has("error")) {
						try {
							if (response.getInt("error") == 0) {
								if (mProgressDialog != null && mProgressDialog.isShowing())
									mProgressDialog.dismiss();
								String result = response.getString("result");
								et_tel_num.setText(result);
							}else if(response.getInt("error") == 1){
								Toast.makeText(mContext, response.getString("reason"), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					getmProgressDialog().dismiss();
			}}, 
				GlobalNetErrorHandler.getInstance(mContext, null, getmProgressDialog())
			);		
	}
	
	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_btn_login:
			handleLogin();
			break;
		case R.id.tv_btn_register:
			break;
		case R.id.login_refresh_btn:
			getUserNmae();
			break;
		default:
			break;
		}
	}
	
	private String mTelNum;
	private String mEncodedPwd;	// 加密后的密码
	
	// 登录处理
	private void handleLogin() {
		String mTelNum = et_tel_num.getText().toString();
		String originalPwd = et_pwd.getText().toString();

		// 验证手机号
		if(TextUtils.isEmpty(mTelNum))
			return ;

		// 验证密码格式
		if (TextUtils.isEmpty(originalPwd)) {
			mToast.toastMsg(R.string.account_check_info_password_error_null);
			SoftInputUtils.requestFocus(mContext, et_pwd);

			return;
		}
		
//		mEncodedPwd = CryptoUtils.MD5Encode(originalPwd);
		reqCode = REQ_LOGIN;
		ApiClient.login(getIMEI(), originalPwd);
	}

	// 点击空白区域 隐藏键盘
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			SoftInputUtils.hideSoftInput(mContext);
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onReqStart() {
		if (reqCode == REQ_LOGIN) {
			mProgressDialog.setMessage(getText(R.string.login_loading).toString());
			if (!mProgressDialog.isShowing())
				mProgressDialog.show();
		}
	}

	@Override
	public void onResponse(JSONObject response) {
		if (reqCode == REQ_LOGIN) {
			Log.d("onResponse", response.toString());
			try {
				if (response.has("error")) {
					if (response.getInt("error") == 0) {
						if (mProgressDialog != null && mProgressDialog.isShowing())
							mProgressDialog.dismiss();

						mXsyUser = JSON.parseObject(response.getJSONObject("result").toString(), XsyUser.class);
						mXsyUser.mPassword =  et_pwd.getText().toString();;
						
						RequestUtils.setToken(mXsyUser.token);
						QuickShPref.putValueObject(QuickShPref.PASS_WORD, mXsyUser.mPassword);
						Log.d("onResponse", "token="+mXsyUser.token);
						
						Intent intent = new Intent(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						if (mProgressDialog != null && mProgressDialog.isShowing())
							mProgressDialog.dismiss();
						
						mToast.toastMsg(response.has("error") ? response.getString("reason").toString() : getText(R.string.bad_network)
								.toString());
					} 
						
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();

		mToast.toastMsg(getText(R.string.bad_network).toString());
	}
	
	public String getIMEI(){
		String imei = QuickShPref.getString(QuickShPref.IEMI);
		if(imei == null){
			imei =((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
			QuickShPref.putValueObject(QuickShPref.IEMI, imei);
		}
		if(ApiClient.isDebuge)
			imei = "56456456";
		return imei;
	}
	
	
}
