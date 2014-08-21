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

	/*** ��ʼ�����пؼ� */
	private void initViews() {
		// Ϊ�ؼ���Id
		btnBack = findViewById(R.id.btn_back);
		btnFav = findViewById(R.id.btn_fav);
		tvSearch = findViewById(R.id.tv_search);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_choose_company);
		rootView = findViewById(R.id.rootView);
		btnComplete = findViewById(R.id.btn_complete);
		// ���ÿؼ�
		btnFav.setOnClickListener(clickListener);
		btnBack.setOnClickListener(clickListener);
		setAdapter();
		expandableListView.setOnChildClickListener(onChildClickListener);
		tvSearch.setOnClickListener(clickListener);
		btnComplete.setOnClickListener(clickListener);
		initPop();
	}

	/** ��ʼ��PopupWindow **/
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
		// ʵ�֣� ���pop�κ�һ��λ�� ��ʧ
		contentView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pop.dismiss();
			}
		});
		// �ҵ�PopupWindow����Ŀؼ�
		EditText etCompany = (EditText) contentView
				.findViewById(R.id.et_express_pop_search);
		// ����EditText�����ݱ仯
		etCompany.addTextChangedListener(watcher);
		listView = (ListView) contentView
				.findViewById(R.id.listView_express_pop_search);
		listView.setOnItemClickListener(itemClickListener);
	}

	/** ΪPopupWindow�����ListView���õ�item���������� **/
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			pop.dismiss();
			// ��ȡ��AdapterView��adapter
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
	/** ΪPopupWindow�����EditText���õļ����� **/
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
	/** ΪExpandableListView���õļ��������������ӵ�item�����¼� **/
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

	/** ΪExpandableListView����Adapter **/
	private void setAdapter() {
		List<String> letters = new ArrayList<String>();
		HashMap<String, List<Company>> companys = new HashMap<String, List<Company>>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(ExpressDbHelper.TABLE_COMPANY_NAME, null,
				null, null, null, null, null);
		/** ����Cursor����ȡ��������ĸ������letters�������� **/
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
		// Ϊletters��������
		Collections.sort(letters, comparator);

		cursor.moveToFirst();
		// ����Cursor����ÿ��������Ϊһ��������� companys����ĸ��Ӧ��List��������
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

			// �ж��Ƿ�Ӧ����ӵ����õ��Ǹ�����
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
		// ��ȫ������չ��
		for (int i = 0; i < letters.size(); i++) {
			expandableListView.expandGroup(i);
		}
	}

	/** ΪList<String> ������������ **/
	Comparator<String> comparator = new Comparator<String>() {

		@Override
		public int compare(String s1, String s2) {
			char c1 = s1.charAt(0);
			char c2 = s2.charAt(0);
			return c1 - c2;
		}
	};
	/** �����¼��ļ����� **/
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
				// �޸����ݿ������
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
