package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.huaan.icare.fws.R;
import com.mapgoo.zero.bean.InternalApp;
import com.mapgoo.zero.ui.widget.AutoScrollViewPager;
import com.mapgoo.zero.ui.widget.CirclePageIndicator;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.MyBannerAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DeviceUtil;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 我的4S店
 * 
 * @Author yao
 */
public class My4SStoreActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_4s_store);
	}

	private int mGridItemWidth;
	
	// TIPS 下面的容器数量请保持一致
	private List<Drawable> mServicesAppIconList;
	private List<String> mServicesAppLabelList;

	private List<InternalApp> mInternalAppList;

	private CommonAdapter<InternalApp> mAdapter;
		
	@Override
	public void initData(Bundle savedInstanceState) {
		
		Resources resources = getResources();
		mServicesAppLabelList = Arrays.asList(resources.getStringArray(R.array.my_4s_app_label));
		
		mServicesAppIconList = new ArrayList<Drawable>();
		TypedArray typedArray = resources.obtainTypedArray(R.array.my_4s_app_icon);
		
		for (int i = 0; i < typedArray.length(); i++) {
			mServicesAppIconList.add(typedArray.getDrawable(i));
		}
		typedArray.recycle();
		
		mInternalAppList = new ArrayList<InternalApp>();
		for (int i = 0; i < mServicesAppLabelList.size(); i++) {
			InternalApp internalApp = new InternalApp();
			internalApp.setAppName(mServicesAppLabelList.get(i));
			internalApp.setAppIcon(mServicesAppIconList.get(i));
			
			mInternalAppList.add(internalApp);
		}
		
		mGridItemWidth = getGridItemWidth(mContext);

		if (savedInstanceState != null) {

		} else {

		}
	}
	
	private int getGridItemWidth(Context context) {

		if (context == null || !(context instanceof Activity))
			return 0;

		// 计算GridView下的app格子宽度
		int screenWidth = DeviceUtil.getScreenWidth(context);

		int gridViewMargin = DimenUtils.dip2px(mContext, 5) * 2;
		int gridViewItemHorizantalSpace = DimenUtils.dip2px(mContext, 5) * 2;

		return (screenWidth - gridViewMargin - gridViewItemHorizantalSpace) / 3;
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	
	private AutoScrollViewPager vPager_banner;
	private TypedArray mAdImgs;
	private ArrayList<View> adList;
	
	private CirclePageIndicator indicator;
	
	private GridView gv_my_4s_app;
	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_4s_default).toString(), 4, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_btn_help, R.drawable.ic_4s_actionbar_bg, -1);

		vPager_banner = (AutoScrollViewPager) findViewById(R.id.vPager_banner);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mAdImgs = getResources().obtainTypedArray(R.array.ad_4s_imgs);
		adList = new ArrayList<View>();
		
		for (int i = 0; i < mAdImgs.length(); i++) {

			ImageView imageView = (ImageView) mInflater.inflate(R.layout.layout_banner_img, null);
			imageView.setImageDrawable(mAdImgs.getDrawable(i));
			adList.add(imageView);
		}
		mAdImgs.recycle();
		
		MyBannerAdapter bannerAdapter = new MyBannerAdapter(mContext, adList);
		vPager_banner.setAdapter(bannerAdapter);
		vPager_banner.setInterval(5000);
		vPager_banner.startAutoScroll();
		vPager_banner.setCurrentItem(0);
		
		indicator.setViewPager(vPager_banner);
		
		
		gv_my_4s_app = (GridView) findViewById(R.id.gv_my_4s_app);
		
		gv_my_4s_app.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				
				switch (position) {
				
				case 0: // 预约维修
					intent.setClass(mContext, PreArrangementActivity.class);
					intent.putExtra("position", position);
					
					startActivity(intent);
					break;

				case 1: // 预约洗车
					intent.setClass(mContext, PreArrangementActivity.class);
					intent.putExtra("position", position);
					
					startActivity(intent);
					break;

				case 2: // 预约保养
					intent.setClass(mContext, PreArrangementActivity.class);
					intent.putExtra("position", position);
					
					startActivity(intent);
					break;

				case 3: // 预约试驾
					intent.setClass(mContext, PreArrangementActivity.class);
					intent.putExtra("position", position);
					
					startActivity(intent);
					
					break;
					
				case 4: // 道路救援/一键救援
					//intent.setClass(mContext, CallRescueActivity.class);
					startActivity(intent);
					
					break;
					
				case 5: // 添加更多
					mToast.toastMsg(R.string.not_open_yet);
					break;

				default:
					break;
				}

			}
		});
		gv_my_4s_app
				.setAdapter(mAdapter = new CommonAdapter<InternalApp>(mContext, mInternalAppList, R.layout.grid_item_internal_app_icon) {

					@Override
					public void convert(ViewHolder holder, InternalApp item) {

						if (mGridItemWidth != 0)
							holder.setLayoutParams(R.id.rl_app_item_wrapper, new AbsListView.LayoutParams(mGridItemWidth, mGridItemWidth));

						holder.setCompoundDrawablesWithIntrinsicBounds(R.id.tv_app_item, null, item.getAppIcon(), null, null);
						holder.setText(R.id.tv_app_item, item.getAppName());

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
