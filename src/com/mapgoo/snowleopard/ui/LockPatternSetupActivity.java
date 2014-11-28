package com.mapgoo.snowleopard.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.ui.widget.LockPatternView;
import com.mapgoo.snowleopard.ui.widget.PatternIndicatorView;
import com.mapgoo.snowleopard.ui.widget.LockPatternView.Cell;
import com.mapgoo.snowleopard.ui.widget.LockPatternView.DisplayMode;
import com.mapgoo.snowleopard.ui.widget.LockPatternView.OnPatternListener;
import com.mapgoo.snowleopard.utils.CryptoUtils;
import com.mapgoo.snowleopard.utils.LockPatternPref;

/**
 * 概述: 手势密码/本地安全密码-设置
 * 
 * @Author yao
 */
public class LockPatternSetupActivity extends BaseActivity implements OnPatternListener {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_lock_pattern_setup);
	}

	private Animation shakeAnim;
	@Override
	public void initData(Bundle savedInstanceState) {
		shakeAnim = AnimationUtils.loadAnimation(mContext, R.anim.shake_x);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	private PatternIndicatorView patternIndicator;
	private TextView patternTopDescription;
	private LockPatternView lockPatternView;
	private TextView resetLockPattern;
	private List<Cell> choosePattern;

	@Override
	public void initViews() {
		patternIndicator = (PatternIndicatorView) findViewById(R.id.patternIndicator);
		patternTopDescription = (TextView) findViewById(R.id.patternTopDescription);
		resetLockPattern = (TextView) findViewById(R.id.resetLockPattern);
		lockPatternView = (LockPatternView) findViewById(R.id.lockView);
		lockPatternView.setOnPatternListener(this);
		
		choosePattern = null;
		
		patternTopDescription.setTextColor(getResources().getColor(R.color.skin_main_txt_clr_light_white));
		lockPatternView.clearPattern();
		lockPatternView.enableInput();
	}

	@Override
	public void handleData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resetLockPattern:
			choosePattern = null;

			patternIndicator.clear();

			patternTopDescription.setTextColor(getResources().getColor(R.color.skin_main_txt_clr_light_white));
			patternTopDescription.setText(getText(R.string.lock_pattern_draw_lockpattern));

			resetLockPattern.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// disable back key
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onPatternStart() {
	}

	@Override
	public void onPatternCleared() {
	}

	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
	}
	
	@Override
	public void onPatternDetected(List<Cell> pattern) {

		Log.d("pattern", LockPatternView.patternToString(pattern));

		if (choosePattern == null && pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
			patternTopDescription.setText(R.string.lock_pattern_error_less_then_4);
			lockPatternView.setDisplayMode(DisplayMode.Wrong);

			lockPatternView.clearPattern(1000);

			return;
		}

		if (choosePattern == null) {
			choosePattern = new ArrayList<Cell>(pattern);

			patternIndicator.setPattern(LockPatternView.patternToStringNum(pattern));

			patternTopDescription.setText(R.string.lock_pattern_draw_lockpattern_again);
			patternTopDescription.setTextColor(getResources().getColor(R.color.skin_main_txt_clr_light_white));

			lockPatternView.clearPattern(1000);

			return;
		} else {
			if (choosePattern.equals(pattern)) {

				// 记录到本地
				LockPatternPref.getInstance(mContext).beginTransaction()
						.setLockPattern(CryptoUtils.MD5Encode(LockPatternView.patternToString(choosePattern))).commit();

				finish();

			} else {
				lockPatternView.setDisplayMode(DisplayMode.Wrong);

				patternTopDescription.setText(R.string.lock_pattern_error_not_the_same);
				patternTopDescription.setTextColor(getResources().getColor(R.color.skin_main_txt_clr_red));
				patternTopDescription.startAnimation(shakeAnim);

				lockPatternView.clearPattern(1000);
				resetLockPattern.setVisibility(View.VISIBLE);
			}

		}

	}

}
