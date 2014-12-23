package com.mapgoo.zero.recevier;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huaan.icare.volunteer.R;
import com.mapgoo.zero.ui.LockPatternActivity;
import com.mapgoo.zero.utils.LockPatternPref;
import com.mapgoo.zero.utils.StringUtils;

/***
 * 概述: 锁屏的广播接收
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月27日
 */
public class LockScreenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) { // 系统解锁后

			if (isRunningForeground(context)) {


				// 进入解锁界面
				Intent newIntent = new Intent(context, LockPatternActivity.class);
				newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				newIntent.putExtra(LockPatternActivity.START_FROM, LockPatternActivity.START_FROM_UNLOCK);
				context.startActivity(newIntent);

			}
		}
	}

	public boolean isRunningForeground(Context context) {
		String packageName = getPackageName(context);
		String topActivityClassName = getTopActivityName(context);
		if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
			Log.d(context.getText(R.string.app_name).toString(), "isRunningForeGround");
			return true;
		} else {
			Log.d(context.getText(R.string.app_name).toString(), "isRunningBackGround");
			return false;
		}
	}

	public String getTopActivityName(Context context) {
		String topActivityClassName = null;
		ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
		if (runningTaskInfos != null) {
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityClassName = f.getClassName();
		}
		return topActivityClassName;
	}

	public String getPackageName(Context context) {
		String packageName = context.getPackageName();
		return packageName;
	}

}
