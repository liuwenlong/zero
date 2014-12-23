package com.mapgoo.zero.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaan.icare.xsy.R;

/**
 * 概述: 违章查询-其他车辆查询
 * 
 * @Author yao
 */
public class ViolationQueryOtherActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_violation_query_other);
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

	private TextView tv_add_car_belong_key;
	private View mPopupView;
	private Dialog mPopupDialog;

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_violation_query).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_convenient_service_actionbar_bg, -1);

		tv_add_car_belong_key = (TextView) findViewById(R.id.tv_add_car_belong_key);
		tv_add_car_belong_key.setOnClickListener(this);

		mPopupView = getLayoutInflater().inflate(R.layout.widget_add_car_belong_dialog, null);
		mPopupView.findViewById(R.id.dialog_cancel_btn).setOnClickListener(this);

		mPopupDialog = new Dialog(mContext, R.style.dialogUpdateTheme);
		mPopupDialog.setCanceledOnTouchOutside(false);
		mPopupDialog.setCancelable(true);
		mPopupDialog.setCanceledOnTouchOutside(true);
		mPopupDialog.addContentView(mPopupView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mPopupDialog.getWindow().setGravity(Gravity.BOTTOM); // 底部弹出

		// 设置dialog中的城市简称按钮点击事件
		for (int i = 0; i < ((LinearLayout) mPopupView).getChildCount(); i++) {
			View firstLevelChildView = ((LinearLayout) mPopupView).getChildAt(i);
			if (firstLevelChildView instanceof LinearLayout) {
				for (int j = 0; j < ((LinearLayout) firstLevelChildView).getChildCount(); j++) {
					View btnView = ((LinearLayout) firstLevelChildView).getChildAt(j);
					if (btnView instanceof Button && btnView.isClickable()) {

						btnView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								tv_add_car_belong_key.setText(((Button) v).getText());
								mPopupDialog.dismiss();
							}
						});
					}
				}
			}
		}

	}

	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:

			finish();

			break;
		case R.id.tv_add_car_belong_key:
			if (mPopupDialog.isShowing())
				mPopupDialog.dismiss();
			else
				mPopupDialog.show();
			break;

		// popupDialog中的取消按钮
		case R.id.dialog_cancel_btn:

			if (mPopupDialog.isShowing())
				mPopupDialog.dismiss();
			break;
		default:
			break;
		}
	}

}
