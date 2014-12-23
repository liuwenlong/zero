package com.mapgoo.zero.ui;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

import com.huaan.icare.xsy.R;

/**
 * 概述: 智能车掌控
 * 
 * @Author yao
 */
public class SmartHandCtrlActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_handctrl);
	}

	
	private SoundPool mSoundPool;
	private int mStartEngSoundEffect;
	private int mLockEffect;
	private int mUnlockEffect;
	
	@Override
	public void initData(Bundle savedInstanceState) {

		mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
		mStartEngSoundEffect = mSoundPool.load(mContext, R.raw.voice_starteng, 1);
		mLockEffect = mSoundPool.load(mContext, R.raw.voice_lock, 1);
		mUnlockEffect = mSoundPool.load(mContext, R.raw.voice_unlock, 1);

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
		super.setupActionBar(getText(R.string.title_smart_handctrl).toString(), 2, R.drawable.ic_back_arrow_white,
				R.drawable.ic_actionbar_record, R.drawable.ic_handctrl_actionbar_bg, -1);

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
			
		case R.id.iv_handctrl_starteng:
			
			mSoundPool.play(mStartEngSoundEffect, 1, 1, 1, 0, 1);
			
			break;
			
		case R.id.iv_handctrl_unlock_ic:
			
			mSoundPool.play(mUnlockEffect, 1, 1, 1, 0, 1);
			
			break;
			
		case R.id.iv_handctrl_lock_ic:
			
			mSoundPool.play(mLockEffect, 1, 1, 1, 0, 1);
			
			break;
			
		case R.id.iv_pick_starteng_time:
			
			
			break;
			

		default:
			break;
		}
	}

}
