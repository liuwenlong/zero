package com.mapgoo.snowleopard.ui;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mapgoo.snowleopard.R;
import com.mapgoo.snowleopard.utils.CryptoUtils;
import com.mapgoo.snowleopard.utils.LockPatternPref;
import com.mapgoo.snowleopard.utils.StringUtils;
import com.mapgoo.snowleopard.widget.LockPatternView;
import com.mapgoo.snowleopard.widget.LockPatternView.Cell;
import com.mapgoo.snowleopard.widget.LockPatternView.DisplayMode;
import com.mapgoo.snowleopard.widget.LockPatternView.OnPatternListener;

/**
 * 概述: 手势密码
 * 
 * @Author yao
 */
public class LockPatternActivity extends BaseActivity implements OnPatternListener {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_lock_pattern);
	}

	private String mPatternStr;
	private LockPatternView lockPatternView;
	private TextView patternTopDescription;
	private int mErrorCount = 5;
	private Animation shakeAnim;

	@Override
	public void initData(Bundle savedInstanceState) {
		shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);

		if (savedInstanceState != null) {
			mPatternStr = savedInstanceState.getString("patternStr", "");
		} else {
			mPatternStr = LockPatternPref.getInstance(mContext).getLockPattern();
		}

		if (StringUtils.isEmpty(mPatternStr))
			startActivity(new Intent(mContext, LockPatternSetupActivity.class));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("patternStr", mPatternStr);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		lockPatternView = (LockPatternView) findViewById(R.id.lockView);
		patternTopDescription = (TextView) findViewById(R.id.patternTopDescription);
		lockPatternView.setOnPatternListener(this);
	}

	@Override
	public void handleData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.patternForgotPwd:

			new AlertDialog.Builder(mContext).setMessage(R.string.lock_pattern_error_reset_pattern_tips)
					.setPositiveButton(R.string.lock_pattern_error_relogin, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							finish();
						}
					}).setNegativeButton(R.string.cancel, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).setCancelable(false).create().show();

			break;

		case R.id.btnSwitchAccount: // 登录其他账户/更换账户
			

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

		if (StringUtils.isEmpty(mPatternStr)) {
			return;
		}
		
		Log.d("pattern", LockPatternView.patternToString(pattern));

		if (CryptoUtils.MD5Encode(LockPatternView.patternToString(pattern)).equals(mPatternStr)) {
			patternTopDescription.setVisibility(View.INVISIBLE);
			startActivity(new Intent(mContext, LockPatternSetupActivity.class));

			finish();

			mErrorCount = 0;
		} else {
			mErrorCount--;

			if (mErrorCount == 0) { // 重试次数已用完

				new AlertDialog.Builder(mContext).setTitle(R.string.prompt).setMessage(R.string.lock_pattern_error_exceed_retry_times)
						.setPositiveButton(R.string.confirm, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								finish();

							}
						}).setCancelable(false).create().show();

				return;
			}

			lockPatternView.setDisplayMode(DisplayMode.Wrong);

			patternTopDescription.setText(String.format(getText(R.string.lock_pattern_error_retry).toString(), mErrorCount));
			patternTopDescription.setTextColor(getResources().getColor(R.color.skin_main_txt_clr_red));
			patternTopDescription.setVisibility(View.VISIBLE);
			patternTopDescription.startAnimation(shakeAnim);

			lockPatternView.clearPattern(1000);
		}

	}
	
}
