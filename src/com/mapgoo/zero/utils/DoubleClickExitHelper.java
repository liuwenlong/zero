package com.mapgoo.zero.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.huaan.icare.family.R;
import com.mapgoo.zero.ui.widget.MyToast;

/**
 * 双击退出 创建日期 2014-05-12
 * 
 * @author 火蚁 (http://my.oschina.net/LittleDY)
 * 
 */
public class DoubleClickExitHelper {

	private final Activity mActivity;

	private boolean isOnKeyBacking = false;
	private Handler mHandler;
	private MyToast mMyToast;

	public DoubleClickExitHelper(Activity activity) {
		mActivity = activity;

		mHandler = new Handler(Looper.getMainLooper());
	}

	/**
	 * Activity onKeyDown事件
	 * */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode != KeyEvent.KEYCODE_BACK)
			return false;

		if (isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if (mMyToast != null)
				mMyToast.cancel();
			// 退出
//			AppManager.getAppManager().AppExit(mActivity);
			
			mActivity.finish();

			return true;
		} else {
			isOnKeyBacking = true;
			if (mMyToast == null)
				mMyToast = MyToast.getInstance(mActivity);
			
			String tips = mActivity.getText(R.string.click_again_to_exit_tip).toString() + mActivity.getText(R.string.app_name);

			mMyToast.toastMsg(tips);
			mHandler.postDelayed(onBackTimeRunnable, 2000);

			return true;
		}
	}

	private Runnable onBackTimeRunnable = new Runnable() {

		@Override
		public void run() {
			isOnKeyBacking = false;

			if (mMyToast != null)
				mMyToast.cancel();
		}
	};
}
