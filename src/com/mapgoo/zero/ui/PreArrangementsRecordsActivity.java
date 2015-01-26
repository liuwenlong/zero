package com.mapgoo.zero.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huaan.icare.pub.R;
import com.mapgoo.zero.bean.PreArrangementRecord;
import com.mapgoo.zero.bean.ViolationRecord;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 预约记录
 * 
 * @Author yao
 */
public class PreArrangementsRecordsActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.layout_pulltorefersh_list);
	}

	private ArrayList<PreArrangementRecord> mPreArrangementRecordList;
	private CommonAdapter<PreArrangementRecord> mAdapter;
	
	private SimpleDateFormat mDateFormatter;
	
	@Override
	public void initData(Bundle savedInstanceState) {
		mPreArrangementRecordList = new ArrayList<PreArrangementRecord>();
		
		mDateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");

		// 测试数据
		try {
			
			PreArrangementRecord record1 = new PreArrangementRecord();
			record1.setPreArrangeType("预约洗车");
			record1.setPreArrangeCarBrand("路虎揽胜");
			record1.setPreArrangeCarLisenceNum("粤B666666");
			record1.setPreArrangeStore("路虎南山4S店");
			record1.setServiceAccept(true);
			record1.setCustomer("张三");
			record1.setRemarks("如果下雨天就不去了，商家可以自行我的取消预约");
			record1.setPreArrangeTime(mDateFormatter.parse("2014.11.12 12:26"));
			mPreArrangementRecordList.add(record1);
			
			PreArrangementRecord record2 = new PreArrangementRecord();
			record2.setPreArrangeType("预约保养");
			record2.setPreArrangeCarBrand("宝马X5");
			record2.setPreArrangeCarLisenceNum("粤B888888");
			record2.setPreArrangeStore("路虎南山4S店");
			record2.setServiceAccept(true);
			record2.setCustomer("张三");
			record2.setRemarks("如果下雨天就不去了，商家可以自行我的取消预约");
			record2.setPreArrangeTime(mDateFormatter.parse("2014.11.10 12:26"));
			mPreArrangementRecordList.add(record2);
			
			PreArrangementRecord record3 = new PreArrangementRecord();
			record3.setPreArrangeType("预约保养");
			record3.setPreArrangeCarBrand("阿斯顿马丁V8Vantage");
			record3.setPreArrangeCarLisenceNum("粤B999999");
			record3.setPreArrangeStore("路虎南山4S店");
			record3.setServiceAccept(true);
			record3.setCustomer("张三");
			record3.setRemarks("如果下雨天就不去了，商家可以自行我的取消预约");
			record3.setPreArrangeTime(mDateFormatter.parse("2014.11.06 12:26"));
			mPreArrangementRecordList.add(record3);
			
			PreArrangementRecord record4 = new PreArrangementRecord();
			record4.setPreArrangeType("预约试驾");
			record4.setPreArrangeStore("路虎南山4S店");
			record4.setPreArrangeCarBrand("宝马X5");
			record4.setServiceAccept(true);
			record4.setCustomer("张三");
			record4.setRemarks("如果下雨天就不去了，商家可以自行我的取消预约");
			record4.setPreArrangeTime(mDateFormatter.parse("2014.11.01 12:26"));
			mPreArrangementRecordList.add(record4);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	private ListView lv_targets;
	private PullToRefreshListView pull_refresh_listview;
	
	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_prearrangements_records).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.ic_4s_actionbar_bg, -1);

		
		pull_refresh_listview = (PullToRefreshListView) findViewById(R.id.pull_refresh_listview);
		lv_targets = pull_refresh_listview.getRefreshableView();
		
		lv_targets.setAdapter(mAdapter = new CommonAdapter<PreArrangementRecord>(mContext, mPreArrangementRecordList, R.layout.list_item_prearrangements_record) {

			@Override
			public void convert(ViewHolder holder, PreArrangementRecord item) {

				if (mPreArrangementRecordList.size() == 1) {
					holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
				} else {
					if (mPreArrangementRecordList.indexOf(item) == 0)
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_top_bg);
					else if (mPreArrangementRecordList.indexOf(item) == (mPreArrangementRecordList.size() - 1))
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_bottom_bg);
					else
						holder.setBackground(R.id.rl_item_wrapper, R.drawable.selector_item_card_middle_bg);
				}

				holder.setText(R.id.tv_prearrangement_type, "[" + item.getPreArrangeType() + "]");
				holder.setText(R.id.tv_prearrangement_time, mDateFormatter.format(item.getPreArrangeTime()));
				
				if (item.getPreArrangeType().contains(getText(R.string.title_prearrange_drive)))
					holder.setText(R.id.tv_prearrangement_target,
							Html.fromHtml(String.format(getText(R.string.prearrangement_store).toString(), item.getPreArrangeStore())));
				else
					holder.setText(
							R.id.tv_prearrangement_target,
							Html.fromHtml(String.format(getText(R.string.prearrangement_car).toString(), item.getPreArrangeCarBrand(),
									item.getPreArrangeCarLisenceNum())));
				
				holder.setPadding(R.id.rl_item_wrapper, DimenUtils.dip2px(mContext, 20), DimenUtils.dip2px(mContext, 10),
						DimenUtils.dip2px(mContext, 10), DimenUtils.dip2px(mContext, 10));
			}
		});
		
		lv_targets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				Intent intent = new Intent(mContext, PreArrangementDetailActivity.class);

				intent.putExtra("preArrangementRecord", mPreArrangementRecordList.get(pos - 1));	// 下拉刷新的头占第0个位置了

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

		default:
			break;
		}
	}

}
