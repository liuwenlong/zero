package com.mapgoo.zero.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.CarObject;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 违章查询
 * 
 * @Author yao
 */
public class ViolationQueryActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_violation_query);
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
		super.setupActionBar(getText(R.string.title_violation_query).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_convenient_service_actionbar_bg, -1);

		lv_targets = (ListView) findViewById(R.id.lv_targets);
		lv_targets
				.setAdapter(mAdapter = new CommonAdapter<CarObject>(mContext, mCarObjList, R.layout.list_item_violation_query_car_select) {

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

						holder.setPadding(R.id.rl_item_wrapper, DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10),
								DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10));

					}
				});

		lv_targets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				Intent intent = new Intent(mContext, ViolationQueryResultActivity.class);
				intent.putExtra("carObject", mCarObjList.get(position));
				startActivity(intent);

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

		case R.id.tv_btn_other:
			startActivity(new Intent(mContext, ViolationQueryOtherActivity.class));

			break;

		default:
			break;
		}
	}

}
