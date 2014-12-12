package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class UIButton extends ImageView {
	public UIButton(Context context) {
		super(context);
	}

	public UIButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UIButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			setColorFilter(0x33000000);
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE
				|| event.getAction() == MotionEvent.ACTION_CANCEL) {
			setColorFilter(Color.TRANSPARENT);
		}
		return super.onTouchEvent(event);
	}
}
