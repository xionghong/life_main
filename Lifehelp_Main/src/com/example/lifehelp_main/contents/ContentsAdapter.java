package com.example.lifehelp_main.contents;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ContentsAdapter extends BaseExpandableListAdapter {

	public String[] groups;
	private List<List<Entity>> lists;
	private Context context;

	public ContentsAdapter(Context context, String[] groups, List<List<Entity>> lists) {
		super();
		this.context = context;
		this.groups = groups;
		this.lists = lists;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return lists.get(groupPosition).size();
	}

	@Override
	public String getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups[groupPosition];
	}

	@Override
	public Entity getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return lists.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		// textView.setBackgroundColor(0XFFB9D3EE);
		textView.setTextColor(0XFF607B8B);
		textView.setTextSize(18);
		return textView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition).getName());
		textView.setTextSize(15);
		return textView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public TextView getGenericView() {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 64);

		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(50, 0, 0, 0);
		return textView;
	}

}
