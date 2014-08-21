package com.example.lifehelp_main.express;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import com.example.lifehelp_main.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class ChooseCompanyAty extends Activity {

	private View btnBack, btnFav, tvSearch, btnComplete;
	private ExpandableListView expandableListView;
	private ExpressDbHelper helper;
	private PopupWindow pop;
	private View rootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_company);
		helper = new ExpressDbHelper(this);
		initViews();
	}

	/*** 初始化所有控件 */
	private void initViews() {
		// 为控件找Id
		btnBack = findViewById(R.id.btn_back);
		btnFav = findViewById(R.id.btn_fav);
		tvSearch = findViewById(R.id.tv_search);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_choose_company);
		rootView = findViewById(R.id.rootView);
		btnComplete = findViewById(R.id.btn_complete);
		// 设置控件
		btnFav.setOnClickListener(clickListener);
		btnBack.setOnClickListener(clickListener);
		setAdapter();
		expandableListView.setOnChildClickListener(onChildClickListener);
		tvSearch.setOnClickListener(clickListener);
		btnComplete.setOnClickListener(clickListener);
		initPop();
	}

	/** 初始化PopupWindow **/
	private void initPop() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.pop_search, null);
		pop = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		//
		Bitmap bitmap = null;
		pop.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
		pop.setFocusable(true);
		pop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				tvSearch.setVisibility(View.VISIBLE);
			}
		});
		// 实现： 点击pop任何一个位置 消失
		contentView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pop.dismiss();
			}
		});
		// 找到PopupWindow里面的控件
		EditText etCompany = (EditText) contentView
				.findViewById(R.id.et_express_pop_search);
		// 监听EditText的内容变化
		etCompany.addTextChangedListener(watcher);
		listView = (ListView) contentView
				.findViewById(R.id.listView_express_pop_search);
		listView.setOnItemClickListener(itemClickListener);
	}

	/** 为PopupWindow里面的ListView设置的item单击监听器 **/
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			pop.dismiss();
			// 获取到AdapterView的adapter
			SimpleCursorAdapter adapter = (SimpleCursorAdapter) arg0
					.getAdapter();
			Cursor cursor = (Cursor) adapter.getItem(arg2);
			String companyName = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_NAME));
			Intent intent = new Intent();
			intent.putExtra("companyName", companyName);
			setResult(RESULT_OK, intent);
			finish();
		}
	};
	/** 为PopupWindow里面的EditText设置的监听器 **/
	private TextWatcher watcher = new TextWatcher() {
		@SuppressLint("NewApi")
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			if (TextUtils.isEmpty(arg0)) {
				listView.setAlpha(0);
			} else {
				listView.setAlpha(1);
				Cursor cursor = helper.getCursor(arg0.toString());
				String[] from = { ExpressDbHelper.TABLE_COMPANY_COMPANY_NAME };
				int[] to = { R.id.tv_item_child };
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(
						getApplicationContext(), R.layout.item_child, cursor,
						from, to, 1);
				listView.setAdapter(adapter);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable arg0) {
		}
	};
	/** 为ExpandableListView设置的监听器，监听孩子的item单击事件 **/
	private OnChildClickListener onChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			switch (expandableListAdapter.getState()) {
			case ExpanableListViewAdapter.STATE_NORMAL:
				String companyName = expandableListAdapter.getChild(
						groupPosition, childPosition).getName();
				Intent intent = new Intent();
				intent.putExtra("companyName", companyName);
				setResult(RESULT_OK, intent);
				finish();
				break;
			case ExpanableListViewAdapter.STATE_SET_FAV:
				String code = expandableListAdapter.getChild(groupPosition,
						childPosition).getCode();
				expandableListAdapter.changeItem(code);
				break;
			}

			return false;
		}
	};

	/** 为ExpandableListView设置Adapter **/
	private void setAdapter() {
		List<String> letters = new ArrayList<String>();
		HashMap<String, List<Company>> companys = new HashMap<String, List<Company>>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(ExpressDbHelper.TABLE_COMPANY_NAME, null,
				null, null, null, null, null);
		/** 遍历Cursor，获取到所有字母，存在letters集合里面 **/
		while (cursor.moveToNext()) {
			String letter = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_INITIAL));
			if (!letters.contains(letter)) {
				letters.add(letter);
			}
			int commomState = cursor.getInt(cursor
					.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMMON));
			if (commomState == 1 && !letters.contains("#")) {
				letters.add("#");
			}
		}
		// 为letters集合排序
		Collections.sort(letters, comparator);

		cursor.moveToFirst();
		// 遍历Cursor，将每条数据作为一个对象存在 companys中字母对应的List集合里面
		while (cursor.moveToNext()) {
			String letter = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_INITIAL));
			if (!companys.containsKey(letter)) {
				companys.put(letter, new ArrayList<Company>());
			}
			String name = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_NAME));
			String code = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_CODE));
			int favState = cursor.getInt(cursor
					.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMMON));
			Company company = new Company(name, code, favState);
			companys.get(letter).add(company);

			// 判断是否应该添加到常用的那个分组
			int commomState = cursor.getInt(cursor
					.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMMON));
			if (commomState == 1) {
				if (!companys.containsKey("#")) {
					companys.put("#", new ArrayList<Company>());
				}
				companys.get("#").add(company);
			}
		}

		expandableListAdapter = new ExpanableListViewAdapter(this, letters,
				companys);
		expandableListView.setAdapter(expandableListAdapter);
		// 将全部分组展开
		for (int i = 0; i < letters.size(); i++) {
			expandableListView.expandGroup(i);
		}
	}

	/** 为List<String> 排序的排序规则 **/
	Comparator<String> comparator = new Comparator<String>() {

		@Override
		public int compare(String s1, String s2) {
			char c1 = s1.charAt(0);
			char c2 = s2.charAt(0);
			return c1 - c2;
		}
	};
	/** 单击事件的监听器 **/
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				finish();
				break;
			case R.id.btn_fav:
				tvSearch.setVisibility(View.GONE);
				expandableListAdapter.changeState();
				v.setVisibility(View.INVISIBLE);
				btnComplete.setVisibility(View.VISIBLE);
				break;
			case R.id.tv_search:
				pop.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
				tvSearch.setVisibility(View.GONE);
				break;
			case R.id.btn_complete:
				List<String> tagSrcFavs = expandableListAdapter.getTagSrcFavs();
				List<String> tagFinallyFavs = expandableListAdapter
						.getTagFinallyFavs();
				// 修改数据库的数据
				helper.updateFavs(tagSrcFavs, tagFinallyFavs);
				v.setVisibility(View.INVISIBLE);
				btnFav.setVisibility(View.VISIBLE);
				tvSearch.setVisibility(View.VISIBLE);
				setAdapter();
				break;
			}
		}
	};
	private ExpanableListViewAdapter expandableListAdapter;
	private ListView listView;
}
