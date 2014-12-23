package com.mapgoo.zero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mapgoo.zero.R;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class YuyuefuwuActivity extends BaseActivity {

	public final static int YUYUE_FUWU_JIAZHENG = 1;
	public final static int YUYUE_FUWU_YILIAO = 2;
	public final static int YUYUE_FUWU_XIYU = 3;
	public final static int YUYUE_FUWU_LIHUA = 4;
	public final static int YUYUE_FUWU_CANYING = 5;
	public final static int YUYUE_FUWU_CHAOSHI = 6;
	public final static int YUYUE_FUWU_SHUCAI = 7;
	public final static int YUYUE_FUWU_JIANSHENG = 8;
	public final static int YUYUE_FUWU_ZHIYUANZHE = 9;
	public final static String[] UrlType={"0","2","5","4","3","1,8","9","6,7","10","4"};
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_yuyuefuwu);
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
		super.setupActionBar(getText(R.string.home_yuyue).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);

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
		case R.id.yuyue_jiazheng:
			startDianpuActivity(YUYUE_FUWU_JIAZHENG);
			break;
		case R.id.yuyue_yiliao:
			startDianpuActivity(YUYUE_FUWU_YILIAO);
			break;
		case R.id.yuyue_xiyu:
			startDianpuActivity(YUYUE_FUWU_XIYU);
			break;
		case R.id.yuyue_lihua:
			startDianpuActivity(YUYUE_FUWU_LIHUA);
			break;
		case R.id.yuyue_canying:
			startDianpuActivity(YUYUE_FUWU_CANYING);
			break;	
		case R.id.yuyue_chaoshi:
			startDianpuActivity(YUYUE_FUWU_CHAOSHI);
			break;
		case R.id.yuyue_shucai:
			startDianpuActivity(YUYUE_FUWU_SHUCAI);
			break;
		case R.id.yuyue_jiansheng:
			startDianpuActivity(YUYUE_FUWU_JIANSHENG);
			break;
		case R.id.yuyue_zhiyuan:
			startDianpuActivity(YUYUE_FUWU_ZHIYUANZHE);			
			break;
		default:
			break;
		}
	}

	private void startDianpuActivity(int type){
		Intent intent = new Intent(mContext,DianpuActivity.class);
		intent.putExtra("fuwutype", type);
		startActivity(intent);
	}
	
	
}
