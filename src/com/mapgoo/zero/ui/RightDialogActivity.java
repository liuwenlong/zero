package com.mapgoo.zero.ui;


import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.Listener;
import com.huaan.icare.xsy.R;
import com.mapgoo.zero.api.ApiClient;
import com.mapgoo.zero.api.GlobalNetErrorHandler;
import com.mapgoo.zero.api.ApiClient.onReqStartListener;
import com.mapgoo.zero.bean.LaorenLocInfo;
import com.mapgoo.zero.utils.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class RightDialogActivity extends BaseActivity implements OnClickListener {
	Intent intent = new Intent("android.intent.action.COMMAND");

	private Button btn_sforchengfan;
	private Button btn_jt;
	private Button btn_dx;
	private Button btn_cs;
	private ProgressBar pb_loading;
	private Handler mHandler;
	public boolean isGetOrderStaus = false; // 指令是否加载成功


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void exitbutton0(View v) {
		this.finish();
	}
	MyDialog dialog ;
    private void send1(String phone, String message){
    	 
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(), 0);
 
        SmsManager sms = SmsManager.getDefault();
 
        sms.sendTextMessage(phone, null, message, pi, null);
 
    }
void showDialog(String str){
	if(mLaorenLocInfo.HasMDT){
		if(mLaorenLocInfo.HasSIM){
			send1(mLaorenLocInfo.SIM, str);
			mToast.toastMsg("已发送指令");
			finish();
		}else{
			dialog = new MyDialog(this,R.style.MyDialog);
			dialog.show();
		}
	}else{
		mToast.toastMsg("没有设备,无法执行指令");
	}
}
public class MyDialog extends Dialog {

    Context context;
   public EditText simText;
    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public MyDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_set_sim_num);
        simText = (EditText)findViewById(R.id.dialog_enter_sim);
        findViewById(R.id.set_sim_num_btn).setOnClickListener(RightDialogActivity.this);
    }

}
public void SetSimnum(View v){
	String sim=
	dialog.simText.getText().toString();
	if(!StringUtils.isEmpty(sim))
		setSimTracks(sim);
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sforchengfan_0:
			showDialog("PZ0");
			break;
		case R.id.btn_sforchengfan_1:
			showDialog("PZ1");
			break;			
		case R.id.set_sim_num_btn:
			SetSimnum(v);
			dialog.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		
	}
	LaorenLocInfo mLaorenLocInfo;
	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(savedInstanceState!=null){
			
		}else{
			mLaorenLocInfo = (LaorenLocInfo)getIntent().getExtras().getSerializable("mLaorenLocInfo");
		}
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		setContentView(R.layout.right_dialogview);
		pb_loading = (ProgressBar) this.findViewById(R.id.pb_loading);
		pb_loading.setVisibility(View.GONE);		
	}

	@Override
	protected void handleData() {
		// TODO Auto-generated method stub
		
	}
	private void setSimTracks(String sim){
		if(MainActivity.mLaorenInfo!=null)
		ApiClient.setSimTracks(MainActivity.mLaorenInfo.ObjectID,sim,
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
										finish();
									}else{
										mToast.toastMsg(response.getString("reason"));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
						}
					},
					GlobalNetErrorHandler.getInstance(mContext, mXsyUser, getmProgressDialog()));		
	}	
	
}
