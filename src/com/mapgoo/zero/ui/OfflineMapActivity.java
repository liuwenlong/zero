package com.mapgoo.zero.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.huaan.icare.family.R;
/*
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKOLSearchRecord;
import com.baidu.mapapi.map.MKOLUpdateElement;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MKOfflineMapListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.icar4s.posonline4s.baidu.R;*/
import com.umeng.analytics.MobclickAgent;

public class OfflineMapActivity extends BaseActivity implements OnClickListener, MKOfflineMapListener {
	private Context mContext;
	private MKOfflineMap mOffline = null;
	// private TextView cidView;
	int cityid = -1;// 城市ID
	private TextView stateView;
	private EditText cityNameView;
	/**
	 * 已下载的离线地图信息列表
	 */
	private ArrayList<MKOLUpdateElement> localMapList = null;
	private LocalMapAdapter lAdapter = null;

	private Button clButton;
	private Button localButton;
	private ImageView iv_start;
	private ArrayList<HashMap<String, Object>> arraylist;
	private ArrayList<ArrayList<HashMap<String, Object>>> arrayChildList;
	ExpandableListView allCityList;
	ListView localMapListView;

	private static ArrayList<HashMap<Integer, Integer>> cGroupListPostion;

@Override
	protected void initData(Bundle savedInstanceState) {
		mContext = this;
		mOffline = new MKOfflineMap();
		mOffline.init(this);
		cGroupListPostion = new ArrayList<HashMap<Integer, Integer>>();
		if (savedInstanceState != null) {
		} else {
		}
	}

	@Override
	protected void initViews(){
		super.setupActionBar(getText(R.string.settings_offline_map).toString(), 1,R.drawable.ic_back_arrow_white, -1, -1, -1);
		
		clButton = (Button) this.findViewById(R.id.clButton);
		localButton = (Button) this.findViewById(R.id.localButton);
		iv_start = (ImageView) this.findViewById(R.id.start);
		stateView = (TextView) findViewById(R.id.state);

		// 获取已下过的离线地图信息
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}

		localMapListView = (ListView) findViewById(R.id.localmaplist);
		lAdapter = new LocalMapAdapter();
		localMapListView.setAdapter(lAdapter);

		allCityList = (ExpandableListView) findViewById(R.id.allcitylist);
		// // 获取所有支持离线地图的城市
		ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
		arraylist = new ArrayList<HashMap<String, Object>>();
		arrayChildList = new ArrayList<ArrayList<HashMap<String, Object>>>();
		if (records2 != null) {
			for (MKOLSearchRecord r : records2) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("content", r.cityName);
				map.put("size", this.formatDataSize(r.size));
				map.put("cityID", r.cityID);
				map.put("cityName", r.cityName);
				map.put("isload", 0); // 是否下载
				map.put("loadcityid", -1); // 是否下载
				map.put("loadcityname", "");
				map.put("loadcityprogress", 0);
				int reatio = compareMapCityIsLoad(r.cityID);
				map.put("ratio", reatio);
				ArrayList<MKOLSearchRecord> childlist = r.childCities;
				ArrayList<HashMap<String, Object>> childMap = new ArrayList<HashMap<String, Object>>();
				if (childlist != null && childlist.size() > 0) {

					for (int i = 0; i < childlist.size(); i++) {
						HashMap<String, Object> items = new HashMap<String, Object>();
						MKOLSearchRecord child = childlist.get(i);
						items.put("content", child.cityName);
						items.put("cityID", child.cityID);
						items.put("size", this.formatDataSize(child.size));
						items.put("cityName", child.cityName);
						int creatio = compareMapCityIsLoad(child.cityID);
						items.put("ratio", creatio);
						items.put("isload", 0);// 是否下载
						items.put("loadcityid", -1); // 是否下载
						items.put("loadcityname", "");
						items.put("loadcityprogress", 0);
						childMap.add(items);
					}

				}
				arrayChildList.add(childMap);
				arraylist.add(map);
			}
		}

		adapter = new ExpandableAdapter();
		allCityList.setAdapter(adapter);

		LinearLayout cl = (LinearLayout) findViewById(R.id.citylist_layout);
		LinearLayout lm = (LinearLayout) findViewById(R.id.localmap_layout);

		lm.setVisibility(View.GONE);
		cl.setVisibility(View.VISIBLE);
		clButton.setBackgroundResource(R.drawable.tab_list_focus);
		localButton.setBackgroundResource(R.drawable.tab_list_normal);

		clButton = (Button) this.findViewById(R.id.clButton);
		localButton = (Button) this.findViewById(R.id.localButton);
		iv_start = (ImageView) this.findViewById(R.id.start);

		allCityList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

				return false;
			}
		});

		allCityList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				return false;
			}
		});

		localMapListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				try {
					MKOLUpdateElement e = (MKOLUpdateElement) localMapList.get(arg2);
					cityNameView.setText(e.cityName);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	private int compareMapCityIsLoad(int cityid) {
		for (int i = 0; i < localMapList.size(); i++) {
			MKOLUpdateElement element = localMapList.get(i);
			if (cityid == element.cityID) {
				return element.ratio;
			}
		}
		return 0;
	}

	/**
	 * 切换至城市列表
	 * 
	 * @param view
	 */
	public void clickCityListButton(View view) {
		LinearLayout cl = (LinearLayout) findViewById(R.id.citylist_layout);
		LinearLayout lm = (LinearLayout) findViewById(R.id.localmap_layout);
		lm.setVisibility(View.GONE);
		cl.setVisibility(View.VISIBLE);
		clButton.setBackgroundResource(R.drawable.tab_list_focus);
		localButton.setBackgroundResource(R.drawable.tab_list_normal);

	}

	/**
	 * 切换至下载管理列表
	 * 
	 * @param view
	 */
	public void clickLocalMapListButton(View view) {
		LinearLayout cl = (LinearLayout) findViewById(R.id.citylist_layout);
		LinearLayout lm = (LinearLayout) findViewById(R.id.localmap_layout);
		lm.setVisibility(View.VISIBLE);
		cl.setVisibility(View.GONE);
		clButton.setBackgroundResource(R.drawable.tab_list_normal);
		localButton.setBackgroundResource(R.drawable.tab_list_focus);
	}

	/**
	 * 搜索离线城市
	 * 
	 * @param view
	 */
	public void loadCity(View view) {
		ArrayList<MKOLSearchRecord> records = mOffline.searchCity(cityNameView.getText().toString());
		if (records == null || records.size() != 1) {
			Toast.makeText(this, "不存在该城市的离线地图", Toast.LENGTH_SHORT).show();
			isStarted = false;
			iv_start.setImageResource(R.drawable.start_bg);
			return;
		}
		cityid = records.get(0).cityID;
		mOffline.start(cityid);
		isStarted = true;
		iv_start.setImageResource(R.drawable.offline_stop_bg);
		// cidView.setText(String.valueOf(records.get(0).cityID));
	}

	boolean isStarted = false; // 是否已经开始下载

	/**
	 * 开始下载
	 * 
	 * @param view
	 */
	public void start(View view) {

		if (!isStarted) {
			if (cityid != -1) {
				mOffline.start(cityid);
				clickLocalMapListButton(null);
				isStarted = true;
				iv_start.setImageResource(R.drawable.offline_stop_bg);
			} else {
				Toast.makeText(this, "请选择城市后再进行下载", Toast.LENGTH_SHORT).show();
			}
		} else {
			if (cityid != -1) {
				isStarted = false;
				mOffline.pause(cityid);
				Toast.makeText(this, "已暂停下载离线地图", Toast.LENGTH_SHORT).show();
				iv_start.setImageResource(R.drawable.start_bg);
			} else {
				Toast.makeText(this, "没有正在下载的城市", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 暂停下载
	 * 
	 * @param view
	 */
	public void stop(View view) {
		if (cityid != -1) {
			mOffline.pause(cityid);
			Toast.makeText(this, "已暂停下载离线地图", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "没有正在下载的城市", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 删除离线地图
	 * 
	 * @param view
	 */
	public void remove(View view) {
		if (cityid != -1) {
			mOffline.remove(cityid);
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
			stateView.setText("已下载:--");
		} else {
			Toast.makeText(this, "没有指定要删除的城市", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 从SD卡导入离线地图安装包
	 * 
	 * @param view
	 */
	public void importFromSDCard(View view) {
		/*
		int num = mOffline.scan();
		String msg = "";
		if (num == 0) {
			msg = "没有导入离线包，这可能是离线包放置位置不正确，或离线包已经导入过";
		} else {
			msg = String.format("成功导入 %d 个离线包，可以在下载管理查看", num);
		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		*/
	}

	/**
	 * 更新状态显示
	 */
	public void updateView() {
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}
		lAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		// int cityid = Integer.parseInt(cidView.getText().toString());
		if (cityid != -1) {
			mOffline.pause(cityid);
			isStarted = false;
			iv_start.setImageResource(R.drawable.start_bg);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this);
		super.onResume();
	}

	public String formatDataSize(int size) {
		String ret = "";
		if (size < (1024 * 1024)) {
			ret = String.format("%dK", size / 1024);
		} else {
			ret = String.format("%.1fM", size / (1024 * 1024.0));
		}
		return ret;
	}

	@Override
	protected void onDestroy() {
		/**
		 * 退出时，销毁离线地图模块
		 */
		cGroupListPostion.clear();
		mOffline.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetOfflineMapState(int type, int state) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			MKOLUpdateElement update = mOffline.getUpdateInfo(state);
			// 处理下载进度更新提示
			if (update != null) {
				cityid = update.cityID;
				stateView.setText(String.format("%s : %d%%", update.cityName, update.ratio));
				int children = 0;
				if (update.ratio == 100) {
					isStarted = false;
					iv_start.setVisibility(View.GONE);
					cityid = -1;
					if (cGroupListPostion != null && cGroupListPostion.size() > 0) {
						HashMap map = cGroupListPostion.get(0);
						Iterator i = map.entrySet().iterator();
						while (i.hasNext()) {
							// 这样就可以遍历该HashMap的key值了。
							Entry entry = (Entry) i.next();
							int key = Integer.parseInt(entry.getKey().toString());
							int postion = Integer.parseInt(map.get(key).toString());
							if (key == -1) {
								arraylist.get(postion).put("loadcityid", cityid);
								arraylist.get(postion).put("loadcityname", update.cityName);
								arraylist.get(postion).put("loadcityprogress", update.ratio);
								arraylist.get(postion).put("ratio", update.ratio);
								arraylist.get(postion).put("isload", 0);
								cGroupListPostion.remove(0);
							} else {
								arrayChildList.get(key).get(postion).put("loadcityid", cityid);
								arrayChildList.get(key).get(postion).put("loadcityname", update.cityName);
								arrayChildList.get(key).get(postion).put("loadcityprogress", update.ratio);
								arrayChildList.get(key).get(postion).put("ratio", update.ratio);
								// if (postion == 0) {
								//
								// } else {
								arrayChildList.get(key).get(postion).put("isload", 0);
								cGroupListPostion.remove(0);
								// }
							}
						}

					}
				} else {
					isStarted = true;
					iv_start.setVisibility(View.VISIBLE);
					iv_start.setImageResource(R.drawable.offline_stop_bg);

					if (cGroupListPostion != null && cGroupListPostion.size() > 0) {
						HashMap map = cGroupListPostion.get(0);
						Iterator i = map.entrySet().iterator();
						while (i.hasNext()) {
							// 这样就可以遍历该HashMap的key值了。
							Entry entry = (Entry) i.next();
							int key = Integer.parseInt(entry.getKey().toString());
							int postion = Integer.parseInt(map.get(key).toString());
							if (key == -1) {
								arraylist.get(postion).put("loadcityid", cityid);
								arraylist.get(postion).put("loadcityname", update.cityName);
								arraylist.get(postion).put("loadcityprogress", update.ratio);
								arraylist.get(postion).put("ratio", update.ratio);
								if (allCityList.getVisibility() == View.VISIBLE) {
									allCityList.setSelectedGroup(postion);
								}
							} else {
								arrayChildList.get(key).get(postion).put("loadcityid", cityid);
								arrayChildList.get(key).get(postion).put("loadcityname", update.cityName);
								arrayChildList.get(key).get(postion).put("loadcityprogress", update.ratio);
								arrayChildList.get(key).get(postion).put("ratio", update.ratio);
								if (allCityList.getVisibility() == View.VISIBLE) {
									allCityList.setSelectedChild(key, postion, true);
								}
							}
						}
					}
				}
				adapter.notifyDataSetChanged();
				updateView();
			}
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 版本更新提示
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

			break;
		}

	}

	ExpandableAdapter adapter;

	private class ExpandableAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return arrayChildList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return arrayChildList.get(groupPosition).size();
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			ViewChild viewChild = null;
			if (convertView == null) {
				viewChild = new ViewChild();
				convertView = LayoutInflater.from(OfflineMapActivity.this).inflate(R.layout.layout_offline_list_item, null);
				viewChild.tv_childcontent = (TextView) convertView.findViewById(R.id.tv_childcontent);
				viewChild.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
				viewChild.iv_download = (ImageView) convertView.findViewById(R.id.iv_download);
				viewChild.ll_progress = (LinearLayout) convertView.findViewById(R.id.ll_progress);
				viewChild.tv_progress = (TextView) convertView.findViewById(R.id.tv_progress);
				viewChild.iv_item_start = (ImageView) convertView.findViewById(R.id.iv_item_start);
				viewChild.pb_progress = (ProgressBar) convertView.findViewById(R.id.pb_progress);
				convertView.setTag(viewChild);
			} else {
				viewChild = (ViewChild) convertView.getTag();
			}
			// viewChild.iv_download.setImageResource(R.drawable.c41);
			if (arrayChildList.size() > groupPosition && arrayChildList.get(groupPosition).size() > childPosition) {
				String content = arrayChildList.get(groupPosition).get(childPosition).get("ratio").toString();
				if (content.equals("0")) {
					content = arrayChildList.get(groupPosition).get(childPosition).get("content").toString();
					viewChild.iv_download.setVisibility(View.VISIBLE);
				} else if (content.equals("100")) {
					content = arrayChildList.get(groupPosition).get(childPosition).get("content").toString() + "  "
							+ getString(R.string.how_much_downloaded);
					viewChild.iv_download.setVisibility(View.INVISIBLE);
				} else {
					content = arrayChildList.get(groupPosition).get(childPosition).get("content").toString() + "  ("
							+ getString(R.string.unfinished) + ")";
					viewChild.iv_download.setVisibility(View.VISIBLE);
				}
				viewChild.tv_childcontent.setText(content);
				viewChild.tv_size.setText(arrayChildList.get(groupPosition).get(childPosition).get("size").toString());

				// 下载控制
				final HashMap<String, Object> map = arrayChildList.get(groupPosition).get(childPosition);
				int isload = Integer.parseInt(map.get("isload").toString());
				if (isload == 0) {
					viewChild.ll_progress.setVisibility(View.GONE);
				} else {
					viewChild.ll_progress.setVisibility(View.VISIBLE);
				}

				if (map.get("cityID").toString().equals(map.get("loadcityid").toString())) {
					viewChild.tv_progress.setText(String.format("%s : %s%%", map.get("loadcityname").toString(), map
							.get("loadcityprogress").toString()));
					viewChild.iv_item_start.setImageResource(R.drawable.offline_stop_bg);
					try {
						viewChild.pb_progress.setProgress(Integer.parseInt(map.get("loadcityprogress").toString()));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					viewChild.iv_item_start.setImageResource(R.drawable.start_bg);
					viewChild.tv_progress.setText(getString(R.string.wait_to_download));
					viewChild.pb_progress.setProgress(0);
				}
				// }

				viewChild.iv_download.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							cityid = Integer.parseInt(map.get("cityID").toString());
							if (mOffline.start(cityid)) {
								HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
								hashMap.put(groupPosition, childPosition);
								cGroupListPostion.add(hashMap);
								map.put("isload", 1);
								adapter.notifyDataSetChanged();
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				});

				viewChild.iv_item_start.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							cityid = Integer.parseInt(map.get("cityID").toString());
							if (mOffline.pause(cityid)) {
								((ImageView) v).setImageResource(R.drawable.offline_stop_bg);
							} else {
								mOffline.start(cityid);
								((ImageView) v).setImageResource(R.drawable.start_bg);
							}

						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				});

				return convertView;
			} else {
				viewChild.tv_childcontent.setText("");
				return null;
			}
		}

		class ViewChild {
			public TextView tv_size;
			public TextView tv_childcontent;
			public ImageView iv_download;
			public LinearLayout ll_progress;
			public TextView tv_progress;
			public ImageView iv_item_start;
			public ProgressBar pb_progress;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return arraylist.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return arraylist.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			GroupView groupView = null;
			if (convertView == null) {
				groupView = new GroupView();
				convertView = LayoutInflater.from(OfflineMapActivity.this).inflate(R.layout.layout_offline_osimple_list_items, null);
				groupView.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				groupView.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
				groupView.iv_open = (ImageView) convertView.findViewById(R.id.iv_open);
				groupView.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);

				groupView.ll_progress = (LinearLayout) convertView.findViewById(R.id.ll_progress);
				groupView.tv_progress = (TextView) convertView.findViewById(R.id.tv_progress);
				groupView.iv_item_start = (ImageView) convertView.findViewById(R.id.iv_item_start);
				groupView.pb_progress = (ProgressBar) convertView.findViewById(R.id.pb_progress);

				convertView.setTag(groupView);
			} else {
				groupView = (GroupView) convertView.getTag();
			}
			String content = arraylist.get(groupPosition).get("ratio").toString();
			if (content.equals("0")) {
				content = arraylist.get(groupPosition).get("content").toString();
				groupView.iv_open.setVisibility(View.VISIBLE);
			} else if (content.equals("100")) {
				content = arraylist.get(groupPosition).get("content").toString() + "  " + getString(R.string.how_much_downloaded);
				groupView.iv_open.setVisibility(View.INVISIBLE);
			} else {
				content = arraylist.get(groupPosition).get("content").toString() + "  (" + getString(R.string.unfinished) + ")";
				// content = arraylist.get(groupPosition).get("content")
				// .toString()
				// + "  (" + getString(R.string.xz) + ":" + content + "%)";
				groupView.iv_open.setVisibility(View.VISIBLE);
			}
			groupView.tv_content.setText(content);
			groupView.tv_size.setText(arraylist.get(groupPosition).get("size").toString());
			// stateView.setText(String.format("%s : %d%%", update.cityName,
			// update.ratio));
			if (arrayChildList.get(groupPosition) != null && arrayChildList.get(groupPosition).size() > 0) {
				// groupView.iv_open.setVisibility(View.VISIBLE);
				if (isExpanded) {
					groupView.iv_open.setImageResource(R.drawable.offline_expandlist_above);
					groupView.ll_content.setBackgroundColor(Color.rgb(205, 205, 205));
				} else {
					groupView.iv_open.setImageResource(R.drawable.offline_expandlist_down);
					groupView.ll_content.setBackgroundDrawable(null);
				}
				groupView.iv_open.setOnClickListener(null);
				groupView.ll_progress.setVisibility(View.GONE);
			} else {
				// groupView.iv_open.setVisibility(View.GONE);
				groupView.ll_content.setBackgroundDrawable(null);
				groupView.iv_open.setImageResource(R.drawable.offline_icon_download);

				// 只有一级的才打开下载进度
				int isload = Integer.parseInt(arraylist.get(groupPosition).get("isload").toString());
				if (isload == 0) {
					groupView.ll_progress.setVisibility(View.GONE);
				} else {
					groupView.ll_progress.setVisibility(View.VISIBLE);
				}

				if (arraylist.get(groupPosition).get("cityID").toString().equals(arraylist.get(groupPosition).get("loadcityid").toString())) {
					groupView.tv_progress.setText(String.format("%s : %s%%", arraylist.get(groupPosition).get("loadcityname").toString(),
							arraylist.get(groupPosition).get("loadcityprogress").toString()));
					groupView.iv_item_start.setImageResource(R.drawable.offline_stop_bg);
					try {
						groupView.pb_progress
								.setProgress(Integer.parseInt(arraylist.get(groupPosition).get("loadcityprogress").toString()));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					groupView.iv_item_start.setImageResource(R.drawable.start_bg);
					groupView.tv_progress.setText(getString(R.string.wait_to_download));
					groupView.pb_progress.setProgress(0);
				}

				groupView.iv_open.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							cityid = Integer.parseInt(arraylist.get(groupPosition).get("cityID").toString());
							if (mOffline.start(cityid)) {
								HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
								hashMap.put(-1, groupPosition);
								cGroupListPostion.add(hashMap);
								arraylist.get(groupPosition).put("isload", 1);
								adapter.notifyDataSetChanged();
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				});

				groupView.iv_item_start.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							cityid = Integer.parseInt(arraylist.get(groupPosition).get("cityID").toString());
							if (mOffline.start(cityid)) {
								mOffline.pause(cityid);
								((ImageView) v).setImageResource(R.drawable.offline_stop_bg);
							} else {
								mOffline.start(cityid);
								((ImageView) v).setImageResource(R.drawable.start_bg);
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				});
			}

			return convertView;
		}

		class GroupView {
			public LinearLayout ll_content;
			public TextView tv_content;
			public TextView tv_size;
			public ImageView iv_open;
			public LinearLayout ll_progress;
			public TextView tv_progress;
			public ImageView iv_item_start;
			public ProgressBar pb_progress;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	};

	/**
	 * 离线地图管理列表适配器
	 */
	public class LocalMapAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return localMapList.size();
		}

		@Override
		public Object getItem(int index) {
			return localMapList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup arg2) {
			MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
			view = View.inflate(OfflineMapActivity.this, R.layout.layout_offline_localmap_list, null);
			initViewItem(view, e);
			return view;
		}

		void initViewItem(View view, final MKOLUpdateElement e) {
			Button display = (Button) view.findViewById(R.id.display);
			Button remove = (Button) view.findViewById(R.id.remove);
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView update = (TextView) view.findViewById(R.id.update);
			TextView ratio = (TextView) view.findViewById(R.id.ratio);
			ratio.setText(e.ratio + "%");
			title.setText(e.cityName);

			if (e.ratio != 100) {
				// display.setEnabled(false);
				display.setText("继续");
				display.setTextColor(Color.RED);
			} else {
				if (e.update) {
					update.setText("可更新");
					display.setText("更新");
					display.setTextColor(Color.RED);
					update.setTextColor(Color.RED);
				} else {
					update.setText("最新");
					display.setText("查看");
					display.setTextColor(Color.BLACK);
					update.setTextColor(Color.BLUE);
				}
			}
			remove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new AlertDialog.Builder(OfflineMapActivity.this).setTitle("温馨提示").setMessage("是否删除" + e.cityName + "的离线地图?")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									mOffline.remove(e.cityID);
									updateView();
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub

								}
							}).create().show();

				}
			});

			display.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (e.ratio != 100) {
						mOffline.remove(e.cityID);
						updateView();
						mOffline.start(e.cityID);
					} else {
						if (e.update) {
							mOffline.remove(e.cityID);
							updateView();
							mOffline.start(e.cityID);
						} else {
							if (cityid != -1) {
								isLoadDialog(false, e);
							} else {
								
								/*Intent intent = new Intent();
								intent.putExtra("x", e.geoPt.getLongitudeE6());
								intent.putExtra("y", e.geoPt.getLatitudeE6());
								intent.setClass(OfflineMapActivity.this, BaseMapDemo.class);
								startActivity(intent);
								*/
							}
						}
					}

				}
			});
		}

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			if (cityid != -1) {
				isLoadDialog(true, null);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void isLoadDialog(final boolean isFinish, final MKOLUpdateElement e) {
		new AlertDialog.Builder(OfflineMapActivity.this).setTitle("操作提示").setMessage("有地图正在下载，离开界面将停止下载，是否离开？")
				.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (isFinish) {
							finish();
						} else {
							/*
							if (e != null) {
								Intent intent = new Intent();
								intent.putExtra("x", e.geoPt.getLongitudeE6());
								intent.putExtra("y", e.geoPt.getLatitudeE6());
								intent.setClass(OfflineMapActivity.this, BaseMapDemo.class);
								startActivity(intent);
							}*/
						}

					}
				}).setNegativeButton(getString(R.string.cancel), null).create().show();
	}

	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_offline);
	}

	@Override
	protected void handleData() {
		// TODO Auto-generated method stub
		
	}

}
