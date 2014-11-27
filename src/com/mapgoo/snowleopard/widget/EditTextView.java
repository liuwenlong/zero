package com.mapgoo.snowleopard.widget;

import com.mapgoo.snowleopard.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


public class EditTextView extends EditText implements TextWatcher, View.OnFocusChangeListener {

	private Drawable mClearBtnDrawable;
	private boolean mFocused;
	private TextWatcher mTxtWatcher;

	public EditTextView(Context context) {
		this(context, null);
	}

	public EditTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public EditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mClearBtnDrawable = getCompoundDrawables()[2];
		if (mClearBtnDrawable == null)
			mClearBtnDrawable = getResources().getDrawable(R.drawable.clear_button_normal);
		mClearBtnDrawable.setBounds(0, 0, this.mClearBtnDrawable.getIntrinsicWidth(), this.mClearBtnDrawable.getIntrinsicHeight());

		setClearIconVisible(false);
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	@Override
	public void afterTextChanged(Editable s) {
		if (mTxtWatcher != null)
			mTxtWatcher.afterTextChanged(s);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		if (mTxtWatcher != null)
			mTxtWatcher.beforeTextChanged(s, start, count, after);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		mFocused = hasFocus;
		if (hasFocus) {
			boolean bool = getText().length() > 0 ? true : false;
			setClearIconVisible(bool);

			return;
		}

		setClearIconVisible(false);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		super.onTextChanged(s, start, before, count);

		if (mFocused) {
			if (s.length() <= 0)
				setClearIconVisible(false);
			else
				setClearIconVisible(true);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if ((event.getAction() == MotionEvent.ACTION_UP) && (getCompoundDrawables()[2] != null))
			if ((event.getX() <= getWidth() - getTotalPaddingRight()) || (event.getX() >= getWidth() - getPaddingRight())) {

		
			} else {
				setText("");

				setClearIconVisible(false);
			}

		return super.onTouchEvent(event);
	}

	protected void setClearIconVisible(boolean hasFocus) {

		if (hasFocus)
			setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mClearBtnDrawable, getCompoundDrawables()[3]);
		else
			setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);

	}

	public void setTextWatcher(TextWatcher txtWatcher) {
		mTxtWatcher = txtWatcher;
	}
}
