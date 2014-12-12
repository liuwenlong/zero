package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class HomeButtonView extends RelativeLayout {
	private boolean mIsScaleDown;
	private ScaleAnimation mScaleDownAnim;
	private ScaleAnimation mScaleUpAnim;
	private float m10DIP;

	public HomeButtonView(Context paramContext) {
		this(paramContext, null);
	}

	public HomeButtonView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public HomeButtonView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init();
	}

	private void init() {
		m10DIP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0F, getResources().getDisplayMetrics());
		
		mScaleDownAnim = new ScaleAnimation(1.0F, 0.95F, 1.0F, 0.95F, 1, 0.5F, 1, 0.5F);
		mScaleDownAnim.setFillAfter(true);
		mScaleDownAnim.setDuration(200L);

		mScaleUpAnim = new ScaleAnimation(0.95F, 1.0F, 0.95F, 1.0F, 1, 0.5F, 1, 0.5F);
		mScaleUpAnim.setFillAfter(true);
		mScaleUpAnim.setDuration(200L);

		mScaleUpAnim.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationEnd(Animation paramAnonymousAnimation) {
				performClick();
			}

			public void onAnimationRepeat(Animation paramAnonymousAnimation) {
			}

			public void onAnimationStart(Animation paramAnonymousAnimation) {
			}
		});
	}

	private boolean isTouchInsideBox(MotionEvent event) {
		int axisX = (int) event.getX();
		int axisY = (int) event.getY();
		
		// TIPS 触摸点是否再大小范围内
		return (axisX >= -this.m10DIP) && (axisY >= -this.m10DIP) && (axisX < this.m10DIP + getWidth())
				&& (axisY < this.m10DIP + getHeight());
	}

	private void performTouchDown() {
		invalidate();
		startAnimation(this.mScaleDownAnim);
	}

	private void performTouchUp() {
		invalidate();
		startAnimation(this.mScaleUpAnim);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			mIsScaleDown = true;
			performTouchDown();
			
			return true;

		case MotionEvent.ACTION_UP:
			
			if (isTouchInsideBox(event) || !mIsScaleDown) {
				mIsScaleDown = false;
				performTouchUp();
			}
				

			return true;

		case MotionEvent.ACTION_MOVE:

			if (!isTouchInsideBox(event))
				performTouchUp();

			return true;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}

}
