package com.mapgoo.zero.ui;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.ui.widget.RuhuPagerAdapter;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class RufuxunshiActivity extends BaseActivity {

	private ViewPager mViewPager;
	private View mViewPager0;
	private View mViewPager1;
	private View mViewPager2;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_ruhuxunshi);
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

	@Override
	public void initViews() {
		ArrayList<View> pageViews = new ArrayList<View>();
		
		super.setupActionBar(getText(R.string.home_rufu).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		mViewPager = (ViewPager)findViewById(R.id.ruhu_vPager);
		
		mViewPager0 = View.inflate(this, R.layout.layout_ruhu_pager_0, null);
		mViewPager1  = View.inflate(this, R.layout.layout_ruhu_pager_1, null);
		mViewPager2  = View.inflate(this, R.layout.layout_ruhu_pager_2, null);
		
		inflate_view(pager1_item_view_id,mViewPager1);
		inflate_view(pager2_item_view_id,mViewPager2);
		
		pageViews.add(mViewPager0);
		pageViews.add(mViewPager1);
		pageViews.add(mViewPager2);
		
		RuhuPagerAdapter mPagerAdapter= new RuhuPagerAdapter(pageViews);
		mViewPager.setAdapter(mPagerAdapter);
		
	}
	
	int[] pager1_item_view_id=new int[]{R.id.pager_1_item_0,R.id.pager_1_item_1,R.id.pager_1_item_2};
	int[] pager2_item_view_id=new int[]{R.id.pager_2_item_0,R.id.pager_2_item_1};
	int[] pager_item_view_id=new int[]{R.id.ruhu_xinxi_item_0,R.id.ruhu_xinxi_item_1,R.id.ruhu_xinxi_item_2,R.id.ruhu_xinxi_item_3,R.id.ruhu_xinxi_item_4,R.id.ruhu_xinxi_item_5};
	int[] string_pager1_array_id=new int[]{R.array.ruhu_jiankan_xinxi,R.array.ruhu_weisheng_xinxi,R.array.ruhu_juzhu_xinxi};
	int[] string_pager2_array_id=new int[]{R.array.ruhu_shuidian_xinxi,R.array.ruhu_jingsheng_xinxi};
	
	private void inflate_view(int viewId[],View parent){
		for(int i=0;i<viewId.length;i++){
			View view = parent.findViewById(viewId[i]);
			inflate_xinxi_item(string_pager1_array_id[i],view);
		}
	}
	
	private void inflate_xinxi_item(int resId,View parent){
		Resources res = getResources();
		String str[] =  res.getStringArray(resId);
		
		for(int i=0;i<pager_item_view_id.length;i++){
			if(i==0){
				TextView text = (TextView)parent.findViewById(pager_item_view_id[i]);
				text.setText(str[i]);
			}else{
				CheckBox box = (CheckBox)parent.findViewById(pager_item_view_id[i]);
				box.setText(str[i]);
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

		default:
			break;
		}
	}

}
