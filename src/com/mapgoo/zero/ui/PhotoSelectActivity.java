package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.huaan.icare.fws.R;
import com.mapgoo.zero.ui.widget.CommonAdapter;
import com.mapgoo.zero.ui.widget.NativeImageLoader;
import com.mapgoo.zero.ui.widget.NativeImageLoader.NativeImageCallBack;
import com.mapgoo.zero.ui.widget.ViewHolder;
import com.mapgoo.zero.utils.DimenUtils;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class PhotoSelectActivity extends BaseActivity implements OnItemClickListener {

	ArrayList<String> mImageList = new ArrayList<String>();
	PhotoAdapter mPhotoAdapter; 
	GridView mGridView;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_avatar_crop_photo_select);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

		if (savedInstanceState != null) {

		} else {

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	@Override
	public void initViews() {
		super.setupActionBar(getText(R.string.photo_select).toString(), 1, R.drawable.ic_back_arrow_white, -1,
				R.drawable.home_actionbar_bgd, -1);
		
		mGridView = (GridView)findViewById(R.id.child_grid);
		mPhotoAdapter = new PhotoAdapter(mContext, mImageList);
		mGridView.setAdapter(mPhotoAdapter);
		mGridView.setOnItemClickListener(this);
		getRecentImages();
	}

	@Override
	public void handleData() {
		
	}
	ProgressDialog aProgressDialog;
	private final static int SCAN_OK = 1;
	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case SCAN_OK:
					aProgressDialog.dismiss();
					mPhotoAdapter.notifyDataSetChanged();
					break;
			}
			return true;
		}
	});
	
	private void getRecentImages() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}

		// 显示进度条
		aProgressDialog = ProgressDialog.show(this, null, "");

		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = mContext.getContentResolver();

				// 条件
				String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?";
				// 条件值
				String[] selectionArgs = new String[] { "image/jpeg", "image/png" };
				// 排序
				String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC";

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null, selection, selectionArgs, sortOrder); // 降序查询，查出最新的100张照片/图片

				// 先获取最近一百张
				int i = 0;
				while (mCursor.moveToNext() && i < 100) {

					// 获取图片的路径
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
					mImageList.add(path);

					i++;
				}

				mCursor.close();

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);

			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ab_left_btn:
			finish();
			break;
		default:
			break;
		}
	}

	class PhotoAdapter extends BaseAdapter{
		ArrayList<String> mDataList;
		Context aContext;
		private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
		
		public PhotoAdapter(Context context, ArrayList<String> mDatas) {
			aContext = context;
			mDataList = mDatas;
		}

		public int getCount() {
			return mDataList.size();
		}

		public String getItem(int position) {
			return mDataList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			String path = getItem(position);
			ImageView mImageView;
			
			if(convertView == null){
				convertView = View.inflate(aContext, R.layout.grid_child_item, null);
				mPoint.set(DimenUtils.dip2px(mContext, 114), DimenUtils.dip2px(mContext, 114));
			}
			
			mImageView = (ImageView)convertView.findViewById(R.id.child_image);
			mImageView.setTag(path);
			
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageCallBack() {
				public void onImageLoader(Bitmap bitmap, String path) {
					ImageView view = (ImageView) mGridView.findViewWithTag(path);
					if (bitmap != null && view != null) {
						view.setImageBitmap(bitmap);
					}
				}
			});
			if (bitmap != null) {
				mImageView.setImageBitmap(bitmap);
			} else {
				mImageView.setImageResource(R.drawable.img_holder);
			}
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		setResult(RESULT_OK, new Intent().putExtra("photo", mImageList.get(arg2)));
		finish();
	}
	
}
