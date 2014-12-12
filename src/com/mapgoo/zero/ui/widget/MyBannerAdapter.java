package com.mapgoo.zero.ui.widget;

import java.util.ArrayList;

import com.mapgoo.zero.ui.WebActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MyBannerAdapter extends PagerAdapter {

	private ArrayList<View> mViewList;
	private Context context;
	
	public MyBannerAdapter(Context context, ArrayList<View> mViewList) {
		this.context = context;
		this.mViewList = mViewList;
	}

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView(mViewList.get(position));
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		super.finishUpdate(container);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		View adView = mViewList.get(position);
		adView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				context.startActivity(new Intent(context, WebActivity.class));

			}
		});

		container.addView(adView, 0);

		return mViewList.get(position);
	}
}
