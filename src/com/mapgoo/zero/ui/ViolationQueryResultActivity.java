package com.mapgoo.zero.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huaan.icare.family.R;
import com.mapgoo.zero.bean.CarObject;
import com.mapgoo.zero.bean.ViolationRecord;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 违章查询-结果/记录
 * 
 * @Author yao
 */
public class ViolationQueryResultActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.layout_pulltorefersh_list);
	}

	private ArrayList<ViolationRecord> mViolationRecordList;
	private CommonAdapter<ViolationRecord> mAdapter;
	
	// 测试数据
	private String mCarObjName="路虎揽胜 粤B88888";

	private SimpleDateFormat mDateFormatter;
	
	private CarObject carObject;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void initData(Bundle savedInstanceState) {
		mViolationRecordList = new ArrayList<ViolationRecord>();
		mDateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		// 测试数据
		try {

			ViolationRecord violationRecord = new ViolationRecord();
			violationRecord.setDesc("机动车违反规定停放");
			violationRecord.setDate(mDateFormatter.parse("2014.11.12 12:26"));
			violationRecord.setAddr("广东省深圳市南山区荔香公园西门");
			violationRecord.setBill(200.0f);
			violationRecord.setGrade(0);
			mViolationRecordList.add(violationRecord);

			ViolationRecord violationRecord1 = new ViolationRecord();
			violationRecord1.setDesc("机动车违反规定停放");
			violationRecord1.setDate(mDateFormatter.parse("2014.11.12 12:26"));
			violationRecord1.setAddr("广东省深圳市南山区荔香公园西门");
			violationRecord1.setBill(200.0f);
			violationRecord1.setGrade(0);
			mViolationRecordList.add(violationRecord1);

			ViolationRecord violationRecord2 = new ViolationRecord();
			violationRecord2.setDesc("机动车违反规定停放");
			violationRecord2.setDate(mDateFormatter.parse("2014.11.12 12:26"));
			violationRecord2.setAddr("广东省深圳市南山区荔香公园西门");
			violationRecord2.setBill(200.0f);
			violationRecord2.setGrade(0);
			mViolationRecordList.add(violationRecord2);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (savedInstanceState != null) {
			carObject = (CarObject) savedInstanceState.getSerializable("carObject");
		} else {
			carObject = (CarObject) getIntent().getSerializableExtra("carObject");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("carObject", carObject);
		super.onSaveInstanceState(outState);
	}

	private ListView lv_targets;
	private PullToRefreshListView pull_refresh_listview;
	@Override
	public void initViews() {
		super.setupActionBar(carObject.getCarBrand() + " " + carObject.getCarLicenseNo(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_convenient_service_actionbar_bg, -1);
		
		pull_refresh_listview = (PullToRefreshListView) findViewById(R.id.pull_refresh_listview);
		lv_targets = pull_refresh_listview.getRefreshableView();
		
		lv_targets.setAdapter(mAdapter = new CommonAdapter<ViolationRecord>(mContext, mViolationRecordList, R.layout.list_item_violation_query_record) {

			@Override
			public void convert(ViewHolder holder, ViolationRecord item) {

				if (mViolationRecordList.size() == 1) {
					holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
				} else {
					if (mViolationRecordList.indexOf(item) == 0)
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
					else if (mViolationRecordList.indexOf(item) == (mViolationRecordList.size() - 1))
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_bottom_bg);
					else
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_middle_bg);
				}

				holder.setText(R.id.tv_record_title, item.getDesc());
				holder.setText(R.id.tv_record_time, mDateFormatter.format(item.getDate()));
				holder.setText(R.id.tv_record_addr_desc, item.getDesc());
				holder.setText(R.id.tv_record_penalty,
						String.format(getText(R.string.violation_qenalty).toString(), item.getBill(), item.getGrade()));

				holder.setPadding(R.id.rl_item_wrapper, DimenUtils.dip2px(mContext, 20), DimenUtils.dip2px(mContext, 10),
						DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10));
				
				holder.setOnClickListner(R.id.rl_item_wrapper, new OnClickListener() {

					@Override
					public void onClick(View v) {
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

		default:
			break;
		}
	}

}
