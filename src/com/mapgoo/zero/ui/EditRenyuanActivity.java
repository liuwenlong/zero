package com.mapgoo.zero.ui;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.RenyuanInfo;
import com.mapgoo.zero.ui.widget.NativeImageLoader;
import com.mapgoo.zero.ui.widget.NativeImageLoader.NativeImageCallBack;
import com.mapgoo.zero.utils.DimenUtils;
import com.mapgoo.zero.utils.ImageUtils;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class EditRenyuanActivity extends BaseActivity {
private ImageView mImageView;
RenyuanInfo mRenyuanInfo;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_add_or_modify_renyuan);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {

		} else {
			if(getIntent()!=null &&getIntent().getExtras()!=null)
				mRenyuanInfo = (RenyuanInfo)getIntent().getExtras().getSerializable("Renyuan");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getString(R.string.fws_renyuan_add), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mImageView = (ImageView)findViewById(R.id.fws_renyuan_pictrue);
		findViewById(R.id.fws_renyuan_birthday).setOnClickListener(this);
		initDisplay();
	}
	
	void initDisplay(){
		if(mRenyuanInfo != null){
			((EditText)findViewById(R.id.fws_renyuan_name)).setText(mRenyuanInfo.PeopleName);
			((EditText)findViewById(R.id.fws_renyuan_sex)).setText(mRenyuanInfo.getPeopleSexString());
			((EditText)findViewById(R.id.fws_renyuan_idcard)).setText(mRenyuanInfo.IDCard);
			if(mRenyuanInfo.Picture != null){
				Log.d("onResponse","info.AvatarImage="+ mRenyuanInfo.Picture);
				MyVolley.getImageLoader().get(mRenyuanInfo.Picture, 
						ImageLoader.getImageListener((ImageView)findViewById(R.id.fws_renyuan_pictrue), 
								R.drawable.list_item_zhiyuan_zhe_icon, R.drawable.list_item_zhiyuan_zhe_icon));
			}
		}
	}

	@Override
	public void handleData() {
		
	}
	Bitmap mBitmap = null;
	private void addPhoto(String path){
		Point point = new Point();
		point.set(DimenUtils.dip2px(mContext, 30), DimenUtils.dip2px(mContext, 30));
		mBitmap = NativeImageLoader.getInstance().loadNativeImage(path, point, new NativeImageCallBack() {
			public void onImageLoader(Bitmap bitmap, String path) {
				mImageView.setImageBitmap(bitmap);
				mBitmap = bitmap;
			}
		});
		if(mBitmap!=null)
			mImageView.setImageBitmap(mBitmap);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 100 && resultCode == RESULT_OK){
			String photo = data.getStringExtra("photo");
			addPhoto(photo);
		}
	}
	
	TextView mInputDate;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_ab_left_btn:
				finish();
				break;
			case R.id.add_shangpin_tijiao:
				saveRenyuan();
				break;
			case R.id.fws_renyuan_pictrue:
				startActivityForResult(new Intent(mContext, PhotoSelectActivity.class), 100);
				break;
			case R.id.fws_renyuan_birthday:
			{
				if(mInputDate == null)
					mInputDate = (TextView)findViewById(R.id.fws_renyuan_birthday);
				Calendar c = Calendar.getInstance();
				Dialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
						mInputDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
					}
				}, c.get(Calendar.YEAR), // 传入年份
						c.get(Calendar.MONTH), // 传入月份
						c.get(Calendar.DAY_OF_MONTH) // 传入天数
				);
				dialog.show();
				break;	
				}
			default:
				break;
		}
	}
	
	private String getPictureBase64(){
		String str =null;
		if(mBitmap !=null)
			str = ImageUtils.img2Base64(mContext, mBitmap);
		return str;
	}
	
	private RenyuanInfo getRenyuanInfo(){
		RenyuanInfo info;
		if(mRenyuanInfo == null)
			info=new RenyuanInfo();
		else
			info=mRenyuanInfo;
		
		info.PeopleName=((EditText)findViewById(R.id.fws_renyuan_name)).getText().toString();
		info.Birthday=((TextView)findViewById(R.id.fws_renyuan_birthday)).getText().toString();
		info.setPeopleSexString(((EditText)findViewById(R.id.fws_renyuan_sex)).getText().toString());
		info.IDCard=((EditText)findViewById(R.id.fws_renyuan_idcard)).getText().toString();
		info.ServiceID = mFwsUser.serviceId;
		
		return info;
	}
	
	public void saveRenyuan(){
		final RenyuanInfo info = getRenyuanInfo();
		if(info.PeopleName == null || info.PeopleName.isEmpty()){
			mToast.toastMsg("姓名不能为空");
			return;
		}
		ApiClient.savePeopleBasic(info,
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
								if(info.PeopleNo == 0)
									info.PeopleNo = response.getInt("result");
								setResult(RESULT_OK);
								PeopleImage(info.PeopleNo);
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
	
	public void PeopleImage(int PeopleNo){
		String picture = getPictureBase64();
		if(picture == null){
			mToast.toastMsg("提交成功");
			finish();
			return;
		}
		ApiClient.PeopleImage(PeopleNo,picture,
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
								mToast.toastMsg("提交成功");
								setResult(RESULT_OK);
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
}
