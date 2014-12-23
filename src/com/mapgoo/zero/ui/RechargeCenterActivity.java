package com.mapgoo.zero.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.huaan.icare.volunteer.R;
import com.mapgoo.zero.bean.CarObject;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 充值中心
 * 
 * @Author yao
 */
public class RechargeCenterActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_service_recharge_center);
	}

	private ArrayList<CarObject> mCarObjList;
	private CommonAdapter<CarObject> mAdapter;

	@Override
	public void initData(Bundle savedInstanceState) {
		mCarObjList = new ArrayList<CarObject>();
		
		// 测试数据
		CarObject carObject = new CarObject();
		carObject.setCarBrand("路虎揽胜");
		carObject.setCarLicenseNo("粤B888888");
		carObject.setCarLogoResId(R.drawable.car_logo_2);
		carObject.setValidRemainDays(60);
		mCarObjList.add(carObject);
		
		CarObject carObject2 = new CarObject();
		carObject2.setCarBrand("宝马X5");
		carObject2.setCarLicenseNo("粤B666666");
		carObject2.setCarLogoResId(R.drawable.car_logo_1);
		carObject2.setValidRemainDays(80);
		mCarObjList.add(carObject2);
		

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	
	private ListView lv_targets;

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_recharge_center).toString(), 2, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_record, R.drawable.ic_convenient_service_actionbar_bg, -1);

		
		lv_targets = (ListView) findViewById(R.id.lv_targets);
		lv_targets.setAdapter(mAdapter = new CommonAdapter<CarObject>(mContext, mCarObjList, R.layout.list_item_recharge_target) {

			@Override
			public void convert(ViewHolder holder, CarObject item) {

				if (mCarObjList.size() == 1) {
					holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
				} else {
					if (mCarObjList.indexOf(item) == 0)
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
					else if (mCarObjList.indexOf(item) == (mCarObjList.size() - 1))
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_bottom_bg);
					else
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_middle_bg);
				}

				holder.setImageResource(R.id.iv_car_logo, item.getCarLogoResId());
				holder.setText(R.id.tv_car_brand, item.getCarBrand());
				holder.setText(R.id.tv_carLicenseNo, item.getCarLicenseNo());
				holder.setText(R.id.tv_valid_remaining_days,
						Html.fromHtml(String.format(getText(R.string.valid_remaining_days).toString(), item.getValidRemainDays())));

				holder.setPadding(R.id.rl_item_wrapper, DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10),
						DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10));
				
				
				holder.setOnClickListner(R.id.rl_item_wrapper, new OnClickListener() {

					@Override
					public void onClick(View v) {
						CheckBox checkBox = (CheckBox) v.findViewById(R.id.item_checkbox);

						if (checkBox.isChecked())
							checkBox.setChecked(false);
						else
							checkBox.setChecked(true);
					}
				});
				
			}
		});
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
			
		case R.id.tv_btn_confirm:
			
			startActivity(new Intent(mContext, PackageSelectActivity.class));
			
			break;

		default:
			break;
		}
	}

}
