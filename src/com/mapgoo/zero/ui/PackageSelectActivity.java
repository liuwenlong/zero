package com.mapgoo.zero.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.huaan.icare.fws.R;
import com.mapgoo.zero.bean.Package;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 套餐选择
 * 
 * @Author yao
 */
public class PackageSelectActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_service_package_select);
	}

	
	private ArrayList<Package> mPackageList;
	private CommonAdapter<Package> mAdapter;
	
	@Override
	public void initData(Bundle savedInstanceState) {
		mPackageList = new ArrayList<Package>();

		// 测试
		Package package1  = new Package();
		package1.setPackageName("1年套餐");
		package1.setPackagePrice(60.0f);
		mPackageList.add(package1);
		
		//
		Package package2  = new Package();
		package2.setPackageName("月度套餐");
		package2.setPackagePrice(20.0f);
		mPackageList.add(package2);

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
		super.setupActionBar(getText(R.string.title_package_select).toString(), 2, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_record, R.drawable.ic_convenient_service_actionbar_bg, -1);
		
		lv_targets = (ListView) findViewById(R.id.lv_targets);
		lv_targets.setAdapter(mAdapter = new CommonAdapter<Package>(mContext, mPackageList, R.layout.list_item_package) {

			@Override
			public void convert(ViewHolder holder, Package item) {

				if (mPackageList.size() == 1) {
					holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
				} else {
					if (mPackageList.indexOf(item) == 0)
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
					else if (mPackageList.indexOf(item) == (mPackageList.size() - 1))
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_bottom_bg);
					else
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_middle_bg);
				}

				holder.setText(R.id.tv_package_name, item.getPackageName());
				holder.setText(R.id.tv_package_price,
						Html.fromHtml(String.format(getText(R.string.package_price).toString(), item.getPackagePrice())));

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
			
			startActivity(new Intent(mContext, PaymentsSelectActivity.class));

			break;

		default:
			break;
		}
	}

}
