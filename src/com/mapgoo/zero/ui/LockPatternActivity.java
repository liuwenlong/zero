package com.mapgoo.zero.ui;

import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.huaan.icare.xsy.R;
import com.mapgoo.zero.ui.widget.LockPatternView;
import com.mapgoo.zero.ui.widget.SimpleDialogBuilder;
import com.mapgoo.zero.ui.widget.LockPatternView.Cell;
import com.mapgoo.zero.ui.widget.LockPatternView.DisplayMode;
import com.mapgoo.zero.ui.widget.LockPatternView.OnPatternListener;
import com.mapgoo.zero.utils.CryptoUtils;
import com.mapgoo.zero.utils.LockPatternPref;
import com.mapgoo.zero.utils.StringUtils;

/**
 * 概述: 手势密码
 * 
 * @Author yao
 */
public class LockPatternActivity extends BaseActivity implements OnPatternListener {

	public static final String START_FROM = "startfrom";
	public static final int START_FROM_SPLASH = 1;
	public static final int START_FROM_SETINGS= 2;	
	public static final int START_FROM_UNLOCK= 3;	
	
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_lock_pattern);
	}

	private String mPatternStr;
	private int mStartForm;
	private LockPatternView lockPatternView;
	private TextView patternTopDescription;
	private int mErrorCount = 5;
	private Animation shakeAnim;

	@Override
	public void initData(Bundle savedInstanceState) {
		shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);

		if (savedInstanceState != null) {
			mPatternStr = savedInstanceState.getString("patternStr", "");
			mStartForm = savedInstanceState.getInt(START_FROM);
		} else {
			mPatternStr = LockPatternPref.getInstance(mContext).getLockPattern();
			mStartForm = getIntent().getIntExtra(START_FROM, START_FROM_UNLOCK);
		}

		if (StringUtils.isEmpty(mPatternStr)) {
			if(mStartForm==START_FROM_SETINGS)
				startActivity(new Intent(mContext, LockPatternSetupActivity.class));
			else if(mStartForm==START_FROM_SPLASH)
				startActivity(new Intent(mContext, LoginActivity.class));
			finish();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("patternStr", mPatternStr);
		outState.putInt(START_FROM, mStartForm);
		super.onSaveInstanceState(outState);
	}

	private TextView mAlertView;

	@Override
	public void initViews() {
		lockPatternView = (LockPatternView) findViewById(R.id.lockView);
		patternTopDescription = (TextView) findViewById(R.id.patternTopDescription);
		lockPatternView.setOnPatternListener(this);

		mAlertView = (TextView) mInflater.inflate(R.layout.layout_alert_dialog_view, null);
	}

	@Override
	public void handleData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.patternForgotPwd:

			mAlertView.setText(getText(R.string.lock_pattern_error_reset_pattern_tips));

			new SimpleDialogBuilder(mContext).setContentView(mAlertView).setNegativeButton(R.string.cancel, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();

				}
			}).setPositiveButton(R.string.lock_pattern_error_relogin, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					finish();
				}
			}).create().show();

			break;

		case R.id.btnSwitchAccount: // 登录其他账户/更换账户
			
			startActivity(new Intent(mContext, LoginActivity.class));
			finish();

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

			switch(mStartForm){
				case START_FROM_SPLASH:
					startActivity(new Intent(mContext, LoginActivity.class));
					break;
				case START_FROM_SETINGS:
					startActivity(new Intent(mContext, LockPatternSetupActivity.class));
					break;
				case START_FROM_UNLOCK:
				default:	
					break;
			}

			finish();
		} else {
			mErrorCount--;

			if (mErrorCount == 0) { // 重试次数已用完

				new SimpleDialogBuilder(mContext).setContentView(mAlertView)
						.setPositiveButton(R.string.lock_pattern_error_relogin, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

//								startActivity(new Intent(mContext, LoginActivity.class));

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
