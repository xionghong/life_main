package com.example.lifehelp_main.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.lifehelp_main.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpanableListViewAdapter extends BaseExpandableListAdapter {

	private List<String> letters;
	private HashMap<String, List<Company>> companys;
	private LayoutInflater inflater;

	private ExpressDbHelper helper;

	public ExpanableListViewAdapter(Context context, List<String> letters,
			HashMap<String, List<Company>> companys) {
		super();
		this.letters = letters;
		this.companys = companys;
		inflater = LayoutInflater.from(context);

		helper = new ExpressDbHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = { ExpressDbHelper.TABLE_COMPANY_COMPANY_CODE,
				ExpressDbHelper.TABLE_COMPANY_COMMON };
		String selection = ExpressDbHelper.TABLE_COMPANY_COMMON + " = ?";
		String[] selectionArgs = { "1" };
		Cursor cursor = db.query(ExpressDbHelper.TABLE_COMPANY_NAME, columns,
				selection, selectionArgs, null, null, null);
		while (cursor.moveToNext()) {
			String code = cursor
					.getString(cursor
							.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_CODE));
			favListSrc.add(code);
		}

		favListFinal.addAll(favListSrc);
	}

	@Override
	public int getGroupCount() {
		return letters.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return companys.get(letters.get(groupPosition)).size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return letters.get(groupPosition);
	}

	@Override
	public Company getChild(int groupPosition, int childPosition) {
		return companys.get(letters.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String letter = getGroup(groupPosition);
		if (letter.equals("#")) {
			View view = inflater.inflate(R.layout.item_group_commom, null);
			return view;
		} else {
			View view = inflater.inflate(R.layout.item_group, null);
			TextView tv = (TextView) view.findViewById(R.id.tv_item_group);
			tv.setText(getGroup(groupPosition));
			return view;
		}
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_child, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv_item_child);
			holder.iv = (ImageView) convertView
					.findViewById(R.id.iv_item_child);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Company company = getChild(groupPosition, childPosition);
		holder.tv.setText(company.getName());
		switch (state) {
		case STATE_NORMAL:
			holder.iv.setVisibility(View.GONE);
			break;
		case STATE_SET_FAV:
			holder.iv.setVisibility(View.VISIBLE);
			// TODO
			if (favListFinal.contains(company.getCode())) {
				holder.iv.setImageResource(R.drawable.child_item_favorite);
			} else {
				holder.iv.setImageResource(R.drawable.child_item_unfavorite);
			}
			break;
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv;
		ImageView iv;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public int getState() {
		return state;
	}

	private int state = STATE_NORMAL;

	/** 改变当前状态 **/
	public void changeState() {
		if (state == STATE_NORMAL) {
			state = STATE_SET_FAV;
		} else {
			state = STATE_NORMAL;
		}
		notifyDataSetChanged();
	}

	/** 普通状态 **/
	public static final int STATE_NORMAL = 1;
	/** 点击了收藏按钮后的状态 **/
	public static final int STATE_SET_FAV = 2;

	private List<String> favListSrc = new ArrayList<String>();
	private List<String> favListFinal = new ArrayList<String>();

	public List<String> getTagSrcFavs() {
		return favListSrc;
	}

	public List<String> getTagFinallyFavs() {
		return favListFinal;
	}

	public void changeItem(String code) {
		if (favListFinal.contains(code)) {
			favListFinal.remove(code);
		} else {
			favListFinal.add(code);
		}
		notifyDataSetChanged();
	}
}
