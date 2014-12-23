package com.mapgoo.zero.ui;

import java.text.SimpleDateFormat;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import com.huaan.icare.volunteer.R;
import com.mapgoo.zero.bean.PreArrangementRecord;

/**
 * 概述: 预约详情
 * 
 * @Author yao
 */
public class PreArrangementDetailActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_prearrangement_detail);
	}

	private PreArrangementRecord mPreArrangementRecord;

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mPreArrangementRecord = (PreArrangementRecord) savedInstanceState.getSerializable("preArrangementRecord");
		} else {
			mPreArrangementRecord = (PreArrangementRecord) getIntent().getSerializableExtra("preArrangementRecord");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("preArrangementRecord", mPreArrangementRecord);
		super.onSaveInstanceState(outState);
	}

	private TextView tv_prearrange_type;
	private TextView tv_prearrange_time;
	private TextView tv_prearrange_store;
	private TextView tv_prearrange_car;
	private TextView tv_prearrange_accept_status;
	private TextView tv_prearrange_customer;
	private TextView tv_prearrange_remarks;
	
	private TextView tv_car_type;

	private CardView cv_btn_wrapper;

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_detail).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_4s_actionbar_bg, -1);

		tv_prearrange_type = (TextView) findViewById(R.id.tv_prearrange_type);
		tv_prearrange_time = (TextView) findViewById(R.id.tv_prearrange_time);
		tv_prearrange_store = (TextView) findViewById(R.id.tv_prearrange_store);
		tv_prearrange_car = (TextView) findViewById(R.id.tv_prearrange_car);
		tv_prearrange_accept_status = (TextView) findViewById(R.id.tv_prearrange_accept_status);
		tv_prearrange_customer = (TextView) findViewById(R.id.tv_prearrange_customer);
		tv_prearrange_remarks = (TextView) findViewById(R.id.tv_prearrange_remarks);
		
		tv_car_type = (TextView) findViewById(R.id.tv_car_type);
		
		cv_btn_wrapper = (CardView) findViewById(R.id.cv_btn_wrapper);

	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void handleData() {
		if (mPreArrangementRecord != null) {
			tv_prearrange_type.setText(mPreArrangementRecord.getPreArrangeType());
			tv_prearrange_time.setText(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(mPreArrangementRecord.getPreArrangeTime()));
			tv_prearrange_store.setText(mPreArrangementRecord.getPreArrangeStore());
			
			
			if (mPreArrangementRecord.getPreArrangeType().contains(getText(R.string.title_prearrange_drive))) {
				tv_car_type.setText(R.string.prearrange_car_type);
				tv_prearrange_car.setText(mPreArrangementRecord.getPreArrangeCarBrand());
			} else {
				tv_car_type.setText(R.string.prearrange_car);
				tv_prearrange_car.setText(mPreArrangementRecord.getPreArrangeCarBrand() + " "
						+ mPreArrangementRecord.getPreArrangeCarLisenceNum());
			}
			
			tv_prearrange_accept_status.setText(mPreArrangementRecord.isServiceAccept() ? getText(R.string.prearrange_accept)
					: getText(R.string.prearrange_unaccept));

			tv_prearrange_customer.setText(mPreArrangementRecord.getCustomer());
			tv_prearrange_remarks.setText(mPreArrangementRecord.getRemarks());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:

			finish();

			break;

		case R.id.tv_btn_cancle:

			break;

		default:
			break;
		}
	}

}
