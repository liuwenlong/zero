package com.mapgoo.zero.utils;

import java.io.File;

import com.huaan.icare.pub.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class TurnToCamrea {
	public static final int REQUEST_PIC_FROM_CAMREA = 2;
	
	private String imgFilePath;
	private Activity mActivity;
	public TurnToCamrea(Activity context ){
		mActivity = context;
	}
	
	/**
	 * 概述：准备+进入相机
	 * 
	 * @author yqw
	 * @since 2014年5月15日
	 * 
	 */
	public void prepareAndTurnToCamrea() {

		Intent camreaIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			String imgDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mActivity.getString(R.string.app_name)
					+ "/camera/";

			File imgDirPathFile = new File(imgDirPath);
			if (!imgDirPathFile.exists()) {
				imgDirPathFile.mkdirs();
			}

			String imgName = "avater_" + Utils.getNow() + ".jpg";

			File imgFile = new File(imgDirPathFile, imgName);
			imgFilePath = imgFile.getAbsolutePath();

			Uri mOutPutImgFileUri = Uri.fromFile(imgFile);

			camreaIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutImgFileUri);// 指定系统相机拍照保存在imageFileUri所指的位置

		} else { // content provider?

			Toast.makeText(mActivity, "未检测到SD卡，相机不可用", Toast.LENGTH_SHORT).show();

		}

		mActivity.startActivityForResult(camreaIntent, REQUEST_PIC_FROM_CAMREA);
	}
	
	public String getImgFilePath(){
		return imgFilePath;
	}
	
}
