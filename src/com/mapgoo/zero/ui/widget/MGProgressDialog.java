package com.mapgoo.zero.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaan.icare.xsy.R;

/**
 * 概述: 自定义Dialog 
 * 
 * @author yao
 * @version 1.0
 * @created 2014年11月8日
 */
public class MGProgressDialog extends Dialog {

	private Context mContext = null;
	private View mContentView;

	public MGProgressDialog(Context context) {

		super(context, R.style.loading_dialog);
		mContext = context;

		mContentView = getLayoutInflater().inflate(R.layout.layout_common_progress, null);

		setContentView(mContentView);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		setCanceledOnTouchOutside(false);
		//setCancelable(false);
	}

	public void setMessage(String msg) {
		TextView msgTextView = (TextView) mContentView.findViewById(R.id.tip_text_view);
		if (msgTextView != null)
			msgTextView.setText(msg);
	}

	@Override
	public void onWindowFocusChanged(boolean paramBoolean) {
		Animation localAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);
		((ImageView) findViewById(R.id.loading_image_view)).setAnimation(localAnimation);
	}

}
