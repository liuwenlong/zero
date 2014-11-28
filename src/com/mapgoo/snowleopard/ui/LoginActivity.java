package com.mapgoo.snowleopard.ui;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.j256.ormlite.dao.Dao;
import com.mapgoo.snowleopard.MGApp;
import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.api.ApiClient;
import com.mapgoo.snowleopard.api.ApiClient.onReqStartListener;
import com.mapgoo.snowleopard.bean.User;
import com.mapgoo.snowleopard.ui.widget.EditTextView;
import com.mapgoo.snowleopard.ui.widget.MGProgressDialog;
import com.mapgoo.snowleopard.utils.CryptoUtils;
import com.mapgoo.snowleopard.utils.LoadPref;
import com.mapgoo.snowleopard.utils.PhoneUtils;
import com.mapgoo.snowleopard.utils.SoftInputUtils;

public class LoginActivity extends BaseActivity implements ErrorListener, Listener<JSONObject>, onReqStartListener {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_login);
	}

	private Context mContext;
	private MGProgressDialog mProgressDialog;
	// 自定义的请求码 标示当前是哪一个请求
	private int reqCode = -1;
	private final int REQ_LOGIN = 999;
	
	@Override
	public void initData(Bundle savedInstanceState) {
		mContext = this;

		// 初始化loading dialog
		mProgressDialog = new MGProgressDialog(mContext);
		mProgressDialog.setCancelable(true);
		
		
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

//		case R.id.tv_btn_forgot_pwd:
//
//			startActivity(new Intent(mContext, SetNewPwdActivity.class));
//			break;

		case R.id.tv_btn_register:

//			startActivity(new Intent(mContext, RegisterActivity.class));

			break;

		default:
			break;
		}
	}

	
	private String mTelNum;
	private String mEncodedPwd;	// 加密后的密码
	
	// 登录处理
	private void handleLogin() {
		mTelNum = et_tel_num.getText().toString();
		String originalPwd = et_pwd.getText().toString();

		// 验证手机号
		if(!PhoneUtils.validatePhoneNum(mTelNum, et_tel_num))
			return ;

		// 验证密码格式
		if (originalPwd.length() == 0) {
			mToast.toastMsg(R.string.account_check_info_password_error_null);
			SoftInputUtils.requestFocus(mContext, et_pwd);

			return;
		}
		
		mEncodedPwd = CryptoUtils.MD5Encode(originalPwd);
		
		
		// TODO For Test
		Intent intent = new Intent(mContext, MainActivity.class);
		startActivity(intent);

//		reqCode = REQ_LOGIN;
//		ApiClient.login(mTelNum, mEncodedPwd);
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
			try {
				if (response.has("error")) {
					if (response.getInt("error") == 0) {
						if (mProgressDialog != null && mProgressDialog.isShowing())
							mProgressDialog.dismiss();

						User user = JSON.parseObject(response.getJSONObject("result").toString(), User.class);
						user.setUserPwd(mEncodedPwd);
						
						Dao<User, String> userDaoUser = User.getDao(MGApp.getHelper());

						// 用户资料入库操作
						if (userDaoUser.queryForId(mTelNum) != null)
							// 存在？->更新
							userDaoUser.update(user);
						else
							// 不存在？->添加
							userDaoUser.createIfNotExists(user);

					
						Intent intent = new Intent(mContext, MainActivity.class);
						intent.putExtra("telNum", user.getUserMobile());
						
						startActivity(intent);

						// 写入最后登录的用户id
						LoadPref.getInstance(mContext).beginTransaction().setLastLoginUserId(user.getUserMobile()).commit();
						
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
			} catch (SQLException e) {
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

}
