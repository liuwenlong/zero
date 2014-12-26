package com.mapgoo.zero.ui;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.MyVolley;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.FwsShangpinInfo;
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
public class EditShangpinActivity extends BaseActivity {
private ImageView mImageView;
FwsShangpinInfo mFwsShangpinInfo;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_add_or_modify_shangpin);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (savedInstanceState != null) {

		} else {
			if(getIntent()!=null &&getIntent().getExtras()!=null)
				mFwsShangpinInfo = (FwsShangpinInfo)getIntent().getExtras().getSerializable("Renyuan");
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
		findViewById(R.id.fws_shangpin_start_time).setOnClickListener(this);
		findViewById(R.id.fws_shangpin_end_time).setOnClickListener(this);
		initDisplay();
	}
	
	void initDisplay(){
		if(mFwsShangpinInfo  != null){
			((EditText)findViewById(R.id.fws_shangpin_name)).setText(mFwsShangpinInfo.ProjectName);
			//((EditText)findViewById(R.id.fws_shangpin_)).setText(mFwsShangpinInfo.getPeopleSexString());
			((TextView)findViewById(R.id.fws_shangpin_start_time)).setText(mFwsShangpinInfo.getStartTime());
			((TextView)findViewById(R.id.fws_shangpin_end_time)).setText(mFwsShangpinInfo.getEndTime());
			((EditText)findViewById(R.id.fws_shangpin_price)).setText(mFwsShangpinInfo.Price);
			((EditText)findViewById(R.id.fws_shangpin_remark)).setText(mFwsShangpinInfo.Remark);
			
			if(mFwsShangpinInfo.ImagePath != null){
				Log.d("onResponse","info.AvatarImage="+ mFwsShangpinInfo.ImagePath);
				MyVolley.getImageLoader().get(mFwsShangpinInfo.ImagePath, 
						ImageLoader.getImageListener((ImageView)findViewById(R.id.fws_renyuan_pictrue), 
								R.drawable.fws_add_shangpin_picture, R.drawable.fws_add_shangpin_picture));
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
	
	TextView mInputStartTime;
	TextView mInputEndTime;
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
			case R.id.fws_shangpin_start_time:{
				if(mInputStartTime == null)
					mInputStartTime = (TextView)findViewById(R.id.fws_shangpin_start_time);
				Calendar c = Calendar.getInstance();
				Dialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						String hour = "";
						if (hourOfDay < 10) {
							hour = "0" + hourOfDay;
						} else {
							hour = "" + hourOfDay;
						}
						String min = "";
						if (minute < 10) {
							min = "0" + minute;
						} else {
							min = "" + minute;
						}
						mInputStartTime.setText(hour + ":" + min);
					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
				dialog.show();
				break;
			}
			case R.id.fws_shangpin_end_time:{
				if(mInputEndTime == null)
					mInputEndTime = (TextView)findViewById(R.id.fws_shangpin_end_time);
				Calendar c = Calendar.getInstance();
				Dialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						String hour = "";
						if (hourOfDay < 10) {
							hour = "0" + hourOfDay;
						} else {
							hour = "" + hourOfDay;
						}
						String min = "";
						if (minute < 10) {
							min = "0" + minute;
						} else {
							min = "" + minute;
						}
						mInputEndTime.setText(hour + ":" + min);
					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
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
	
	private String getPeriod(){
		String Period;
		Period = ((TextView)findViewById(R.id.fws_shangpin_start_time)).getText().toString();
		Period += "-";
		Period += ((TextView)findViewById(R.id.fws_shangpin_end_time)).getText().toString();
		return Period;
	}
	private FwsShangpinInfo getRenyuanInfo(){
		FwsShangpinInfo info;
		if(mFwsShangpinInfo == null){
			info=new FwsShangpinInfo();
		}else{
			info=mFwsShangpinInfo;
			
		}
		info.ProjectName = ((EditText)findViewById(R.id.fws_shangpin_name)).getText().toString();
		info.Price = ((EditText)findViewById(R.id.fws_shangpin_price)).getText().toString();
		info.Remark = ((EditText)findViewById(R.id.fws_shangpin_remark)).getText().toString();
		info.Period = getPeriod();
		info.ServiceID = mFwsUser.serviceId;
		return info;
	}
	
	public void saveRenyuan(){
		final FwsShangpinInfo info = getRenyuanInfo();
		if(info.ProjectName == null || info.ProjectName.isEmpty()){
			mToast.toastMsg("姓名不能为空");
			return;
		}
		ApiClient.saveProjectBasic(info,
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
								setResult(RESULT_OK);
								if(info.ProjectID == 0)
									info.ProjectID = response.getInt("result");
								ProjectImage(info.ProjectID);
							}else{
								mToast.toastMsg(response.getString("reason"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
				}},
			GlobalNetErrorHandler.getInstance(mContext, mFwsUser, getmProgressDialog()));		
	}
	
	public void ProjectImage(int projectId){
		String picture = getPictureBase64();
		if(picture == null){
			mToast.toastMsg("提交成功");
			finish();
			return;
		}
		ApiClient.ProjectImage(projectId,picture,
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
			GlobalNetErrorHandler.getInstance(mContext, mFwsUser, getmProgressDialog()));		
	}
}
