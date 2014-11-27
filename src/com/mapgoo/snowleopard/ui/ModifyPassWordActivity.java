package com.mapgoo.snowleopard.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.widget.EditTextView;

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
		super.setupActionBar(getText(R.string.settings_modify_pw).toString(), 1, R.drawable.ic_back_arrow_white, -1);
		
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
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int id = buttonView.getId();
		EditTextView edit;
		switch(id){
			case R.id.cb_see_pwd_old:
				edit = et_old_pwd;
				break;
			case R.id.cb_see_pwd_new:
				edit = et_new_pwd;
				break;
			default:
				return;
		}
		if(isChecked)
			edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		else
			edit.setTransformationMethod(PasswordTransformationMethod.getInstance());		
	}

}
