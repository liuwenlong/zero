package com.mapgoo.zero.ui;


import com.mapgoo.zero.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class RightDialogActivity extends Activity implements OnClickListener {
	Intent intent = new Intent("android.intent.action.COMMAND");

	private Button btn_sforchengfan;
	private Button btn_jt;
	private Button btn_dx;
	private Button btn_cs;
	private ProgressBar pb_loading;
	private Handler mHandler;
	public boolean isGetOrderStaus = false; // 指令是否加载成功

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.right_dialogview);

		btn_sforchengfan = (Button) this.findViewById(R.id.btn_sforchengfan);
		btn_sforchengfan.setOnClickListener(this);
		btn_cs = (Button) this.findViewById(R.id.btn_cs);
		pb_loading = (ProgressBar) this.findViewById(R.id.pb_loading);
		pb_loading.setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	};



	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void exitbutton0(View v) {
		this.finish();
	}



	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			case 3:
				break;
			case 2:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			default:
				break;
			}

			return true;
		}
	});
void showDialog(){
	Dialog dialog = new MyDialog(this,R.style.MyDialog);
	dialog.show();
}
public class MyDialog extends Dialog {

    Context context;
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
    }

}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sforchengfan:
			showDialog();
			break;

		default:
			break;
		}
	}
}
