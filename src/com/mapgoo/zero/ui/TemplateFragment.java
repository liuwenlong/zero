package com.mapgoo.zero.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huaan.icare.family.R;

/**
 * 概述: 模版Fragment
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月19日
 */
public class TemplateFragment extends BaseFragment {

	private LayoutInflater mInflater;
	private View mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;

		mRootView = inflater.inflate(R.layout.layout_fragment_container, container, false);

		return mRootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData(savedInstanceState);

		initViews();
	}

	private Context mContext;

	private void initData(Bundle savedInstanceState) {
		mContext = getActivity();

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void initViews() {

	}

	@Override
	public void onClick(View v) {
	}

}
