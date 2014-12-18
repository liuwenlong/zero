package com.mapgoo.zero.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mapgoo.zero.api.MyVolley;

public class ViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}
	
	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, Spanned spanned) {
		TextView view = getView(viewId);
		view.setText(spanned);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片-网络图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url, int imgHoldDrawId, int imgErrDrawId) {

		// 图片，加载图片
		MyVolley.getImageLoader().get(url, 
				ImageLoader.getImageListener((ImageView) getView(viewId), imgHoldDrawId, imgErrDrawId));

		return this;
	}

	public int getPosition() {
		return mPosition;
	}

	/**
	 * 概述: 设置背景，用于位每个条目设置不同的背景
	 * 
	 * @auther yao
	 * @param rlItemContainer
	 * @param selectorItemCardMiddleBg
	 */
	public void setBackground(int viewId, int bgDrawableResId) {
		getView(viewId).setBackgroundResource(bgDrawableResId);
	}
	
	/**
	 * 概述: 设置点击事件监听
	 *
	 * @auther yao
	 * @param viewId
	 * @param clickListener
	 */
	public void setOnClickListner(int viewId, OnClickListener clickListener) {

		getView(viewId).setOnClickListener(clickListener);
	}

	/**
	 * 概述: 设置布局留白
	 * 
	 * @auther yao
	 * @param viewId
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setPadding(int viewId, int left, int top, int right, int bottom) {
		getView(viewId).setPadding(left, top, right, bottom);
	}

	public void setVisibility(int viewId, int visibility) {
		getView(viewId).setVisibility(visibility);
	}

	public void setCompoundDrawablesWithIntrinsicBounds(int viewId, Drawable left, Drawable top, Drawable right, Drawable bottom) {

		TextView textView = getView(viewId);
		textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
	}

	public void setLayoutParams(int viewId, ViewGroup.LayoutParams layoutParams) {
		getView(viewId).setLayoutParams(layoutParams);
	}
}
