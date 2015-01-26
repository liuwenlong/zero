package com.mapgoo.zero.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.huaan.icare.pub.R;

public class FragmentUtils {

	/**
	 * 概述: 添加/新增/第一次加载一个fragment
	 *
	 * @auther yao
	 * @param context
	 * @param fragment
	 */
	public static void add(FragmentManager fragmentManager, int containerViewId, Fragment fragment) {

		FragmentTransaction ft = fragmentManager.beginTransaction();

		// ft.setCustomAnimations(
		// R.animator.fragment_slide_left_enter,
		// R.animator.fragment_slide_left_exit,
		// R.animator.fragment_slide_right_enter,
		// R.animator.fragment_slide_right_exit);

		ft.add(containerViewId, fragment);
		ft.commit();
	}
	
	/**
	 * 概述: 更换/变换/替换Fragment
	 *
	 * @auther yao
	 * @param fragmentManager
	 * @param fragment
	 */
	public static void replace(FragmentManager fragmentManager, int containerViewId,Fragment fragment) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setCustomAnimations(
				 R.animator.fragment_slide_left_enter,
		         R.animator.fragment_slide_left_exit,
		         R.animator.fragment_slide_right_enter,
		         R.animator.fragment_slide_right_exit);
		ft.replace(containerViewId, fragment);
		ft.addToBackStack("");
		ft.commit();
	}
}
