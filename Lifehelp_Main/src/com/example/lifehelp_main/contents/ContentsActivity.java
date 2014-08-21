package com.example.lifehelp_main.contents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.tool.CopyFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;

public class ContentsActivity extends Activity implements OnChildClickListener,
		OnClickListener {

	private ExpandableListView expandableListView;
	private String[] groups = { "银行服务", "通讯服务", "出行服务", "政府机构", "售后服务", "保险",
			"社会团体", "大学", "订餐", "投诉举报" };
	private List<List<Entity>> lists;
	private ContentsNumberDBHelper helper;
	private ContentsAdapter adapter;
	private Button searchBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contents_mian);
		helper = new ContentsNumberDBHelper(this);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		setDatas();

		adapter = new ContentsAdapter(this, groups, lists);
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(this);
		searchBtn = (Button) findViewById(R.id.searchBtn_number_main);
		searchBtn.setOnClickListener(this);

	}

	private void setDatas() {
		// TODO Auto-generated method stub
		lists = new ArrayList<List<Entity>>();
		for (String groupName : groups) {
			copyDb();
			List<Entity> list = helper.getList(groupName);
			lists.add(list);

		}

	}

	private void copyDb() {
		// TODO Auto-generated method stub
		File dbFile = getDatabasePath("phonenumber.db");
		CopyFile.copyFileFromResToPhone(dbFile.getParent(), dbFile.getName(),
				getResources().openRawResource(R.raw.phonenumber));
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		Entity entity = adapter.getChild(groupPosition, childPosition);
		String name = entity.getName();
		final String tele = entity.getPhoneNum();
		dialog.setTitle(name);
		dialog.setMessage("电话： " + tele);
		dialog.setPositiveButton("拨打", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:" + tele));
				startActivity(intent);
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.show();
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!canCanle()) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 判断是否可退出（当有列表展开时，返回false且将列表全部收缩）
	 * 
	 * @return
	 */
	private boolean canCanle() {
		boolean canCancle = true;
		for (int i = 0; i < groups.length; i++) {
			if (expandableListView.collapseGroup(i)) {
				canCancle = false;
			}
		}
		return canCancle;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.searchBtn_number_main:
			showDialog();
			break;
		}
	}

	private ListView listView;

	void showDialog() {
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.number_dialog);
		dialog.setTitle("号码搜索");
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.dialogWindowAnim);
		dialog.show();
		final EditText searchEt = (EditText) dialog
				.findViewById(R.id.searchEt_number_dialog);
		Util.showSoftKeyboard(searchEt, ContentsActivity.this);
		listView = (ListView) dialog.findViewById(R.id.listView_number_dialog);
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Util.closeKeyboard(searchEt, ContentsActivity.this);
				return false;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						ContentsActivity.this);
				Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
				final String tele = cursor.getString(cursor
						.getColumnIndex(ContentsNumberDBHelper.TELE));
				String name = cursor.getString(cursor
						.getColumnIndex(ContentsNumberDBHelper.NAME));
				dialog.setTitle(name);
				dialog.setMessage("电话： " + tele);
				dialog.setPositiveButton("拨打",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.CALL");
								intent.setData(Uri.parse("tel:" + tele));
								startActivity(intent);
							}
						});
				dialog.setNegativeButton("取消", null);
				dialog.show();

			}
		});
		showListView("");
		searchEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				showListView(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	void showListView(String name) {
		Cursor cursor;
		if (TextUtils.isEmpty(name)) {
			cursor = helper.getCursorNormal();
		} else {
			cursor = helper.getCursonByName(name);
		}
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.number_listview_item, cursor,
				new String[] { ContentsNumberDBHelper.NAME },
				new int[] { R.id.tv_number_listview_item }, 0);
		listView.setAdapter(adapter);
	}
}
