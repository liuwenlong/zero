package com.mapgoo.zero.ui.widget;

import com.huaan.icare.fws.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PhotoSelectPop implements OnTouchListener, OnClickListener {
	private PopupWindow popupWindow;
	private View popupView;
	private View popupMenuView;
	
	private TextView tv_from_camera;
	private TextView tv_from_local_album;
	private TextView tv_select_cancel;
	
	private Animation footerApperAnim; // 组件出现动画
	private Animation footerDisapperAnim; // 组件消失动画
	private OnClickListener mOnClickListener;
	private Context mContext;
	public PhotoSelectPop(Context context){
		mContext = context;
		popupView = View.inflate(context,R.layout.layout_shuoshuo_upload_way_select, null);
		popupView.setOnTouchListener(this);
		
		popupMenuView = popupView.findViewById(R.id.ll_shuoshuo_upload_menu);

		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		tv_from_camera = (TextView) popupView.findViewById(R.id.tv_from_camera);
		tv_from_local_album = (TextView) popupView.findViewById(R.id.tv_from_local_album);
		tv_select_cancel = (TextView) popupView.findViewById(R.id.tv_select_cancel);
		
		tv_from_camera.setOnClickListener(this);
		tv_from_local_album.setOnClickListener(this);
		tv_select_cancel.setOnClickListener(this);
		
		footerApperAnim = AnimationUtils.loadAnimation(mContext, R.anim.footer_appear);
		footerDisapperAnim = AnimationUtils.loadAnimation(mContext, R.anim.footer_disappear);
		footerDisapperAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation arg0) {
				dismiss();
			}
		});
	}
	
	public void setOnClickListener(OnClickListener l){
		mOnClickListener = l;
	}
	
	public void show(View view){
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		popupMenuView.startAnimation(footerApperAnim);
	}
	
	public void dismiss(){
		popupWindow.dismiss();
	}

	public boolean isShowing(){
		return popupWindow.isShowing();
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id != R.id.tv_from_camera || id != R.id.tv_from_local_album) {
			// 抽屉收回菜单
			popupMenuView.startAnimation(footerDisapperAnim);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mOnClickListener != null)
		 mOnClickListener.onClick(v);
		dismiss();
	}

}
