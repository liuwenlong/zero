package com.mapgoo.zero.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.MessageInfo;
import com.mapgoo.zero.ui.widget.EditTextView;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class ModifyPassWordActivity extends BaseActivity implements OnCheckedChangeListener {
	EditTextView et_old_pwd;
	EditTextView et_new_pwd;
	CheckBox see_pwd_old;
	CheckBox see_pwd_new;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_setting_pw);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar("修改密码", 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		et_old_pwd = (EditTextView) findViewById(R.id.old_pwd);
		et_new_pwd= (EditTextView) findViewById(R.id.new_pwd);
		see_pwd_old=(CheckBox)findViewById(R.id.cb_see_pwd_old);
		see_pwd_new=(CheckBox)findViewById(R.id.cb_see_pwd_new);
		see_pwd_old.setOnCheckedChangeListener(this);
		see_pwd_new.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	public void handleData() {

	}
	
	private void handleLogin() {

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		case R.id.tv_btn_login:
			startModifyPassword();
			break;			
		default:
			break;
		}
	}
	
private void startModifyPassword() {
	String old = et_old_pwd.getText().toString();
	String newpass = et_new_pwd.getText().toString();	
	
	UpdateUserPassword(old,newpass);
}
	
	private void UpdateUserPassword(String old,String newpass){
		if(mFwsUser!=null)
		ApiClient.UpdateUserPassword(mFwsUser.serviceId,old, newpass,
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
										mToast.toastMsg("密码修改成功,请重新登陆!");
										startActivity(new Intent(mContext, LoginActivity.class));
										finish();
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int id = buttonView.getId();
		EditTextView edit;
		switch (id) {
		case R.id.cb_see_pwd_old:
			edit = et_old_pwd;
			break;
		case R.id.cb_see_pwd_new:
			edit = et_new_pwd;
			break;
		default:
			return;
		}
		
		if (isChecked)
			edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		else
			edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
}
