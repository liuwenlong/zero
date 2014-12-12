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
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mapgoo.zero.R;
import com.mapgoo.zero.bean.InternalApp;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DeviceUtil;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 便捷服务
 * 
 * @Author yao
 */
public class ConvenientServiceActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_convenience_service);
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
		mServicesAppLabelList = Arrays.asList(resources.getStringArray(R.array.convenient_app_label));
		
		mServicesAppIconList = new ArrayList<Drawable>();
		TypedArray typedArray = resources.obtainTypedArray(R.array.convenient_app_icon);
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
	
	private GridView gv_app;

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.title_convenient_service).toString(), 2, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_btn_help, R.drawable.ic_convenient_service_actionbar_bg, -1);

		gv_app = (GridView) findViewById(R.id.gv_convenient_service_app);
		
		gv_app.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0: // 充值中心
					startActivity(new Intent(mContext, RechargeCenterActivity.class));
					break;
				case 1: // 周边服务
//					startActivity(new Intent(mContext, ViolationQueryActivity.class));
					break;
				case 2: // 违章查询
					startActivity(new Intent(mContext, ViolationQueryActivity.class));
					break;
					
				case 3: // 更多定制
//					startActivity(new Intent(mContext, ViolationQueryActivity.class));
					mToast.toastMsg(R.string.not_open_yet);
					break;

				default:
					break;
				}

			}
		});
		
		gv_app.setAdapter(mAdapter = new CommonAdapter<InternalApp>(mContext, mInternalAppList, R.layout.grid_item_internal_app_icon) {

			@Override
			public void convert(ViewHolder holder, InternalApp item) {

				if (mGridItemWidth != 0)
					holder.setLayoutParams(R.id.rl_app_item_wrapper, new AbsListView.LayoutParams(mGridItemWidth, mGridItemWidth));

				holder.setCompoundDrawablesWithIntrinsicBounds(R.id.tv_app_item, null, item.getAppIcon(), null, null);
				holder.setText(R.id.tv_app_item, item.getAppName());

			}
		});
		
		Log.d("gv_app width", gv_app.getWidth() + "");
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
