package com.mapgoo.zero.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.handmark.pulltorefresh.library.extras.PullToRefreshWebView2;
import com.huaan.icare.fws.R;
import com.mapgoo.zero.ui.widget.SimpleDialogBuilder;

/**
 * 概述: 模版
 * 
 * @Author yao
 */
public class WebActivity extends BaseActivity {

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_webview);
	}

	// 测试
	private String mAdTitle = "资讯";
	private String mAdURL = "http://www.baidu.com";

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

	private PullToRefreshWebView pull_refresh_webview;
	private WebView mWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void initViews() {
		super.setupActionBar(mAdTitle, 1, R.drawable.ic_back_arrow_white, -1, -1, -1);

		pull_refresh_webview = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);
		mWebView = pull_refresh_webview.getRefreshableView();

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());

		// 自适应屏幕
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setLoadWithOverviewMode(true);

		// 缩放支持
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setDisplayZoomControls(false);
		// 扩大比例的缩放
		mWebView.getSettings().setUseWideViewPort(true);

		if (mAdURL != null) {

			mWebView.loadUrl(mAdURL);
		} else {
			mToast.toastMsg(R.string.url_link_error);

			finish();
		}
		
		pull_refresh_webview.setOnRefreshListener(new OnRefreshListener<WebView>() {

			@Override
			public void onRefresh(PullToRefreshBase<WebView> refreshView) {
				refreshView.getRefreshableView().reload();
			}
		});

	}

	private class SampleWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, final String url) {
			// Return false so the WebView loads the url

			// 调用本地程序
			if (url.startsWith("mailto:") || url.startsWith("geo:")) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);

			} else if (url.startsWith("tel:")) {

				new SimpleDialogBuilder(mContext)
						.setMessage(String.format(getText(R.string.dialog_call_warning_msg).toString(), url.substring(4)))
						.setPositiveButton(R.string.dialog_call_btn_call, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
								startActivity(intent);
							}
						}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).create().show();

			} else {
				view.loadUrl(url);
			}

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			

			// If the PullToRefreshAttacher is refreshing, make it as complete
//			if (mPullToRefreshLayout.isRefreshing()) {
//				mPullToRefreshLayout.setRefreshComplete();
//			}
		}
	}

	@Override
	public void handleData() {

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

}
