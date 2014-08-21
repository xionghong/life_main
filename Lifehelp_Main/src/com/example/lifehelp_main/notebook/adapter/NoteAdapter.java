package com.example.lifehelp_main.notebook.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.notebook.NotebookActivity;
import com.example.lifehelp_main.notebook.db.NoteDBHelper;
import com.example.lifehelp_main.notebook.entity.NoteEntity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class NoteAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private NotebookActivity rootAty;
	private List<NoteEntity> notes;
	public List<Integer> deleteIds;
	private NoteDBHelper helper;
	private String tagShowStyle;
	public NoteAdapter(Activity context,String tagShowStyle) {
		rootAty = (NotebookActivity) context;
		inflater = LayoutInflater.from(context);
		deleteIds = new ArrayList<Integer>();
		rootAty.deleteLayout.setEnabled(false);
		helper = new NoteDBHelper(context);
		notes = helper.getNotes();
		this.tagShowStyle = tagShowStyle;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notes.size();
	}

	@Override
	public NoteEntity getItem(int position) {
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			if(tagShowStyle.equals(NotebookActivity.TAG_LIST)){
				convertView = inflater.inflate(R.layout.note_listview_item, null);
				holder = new ViewHolder();
				holder.setTitleTv((TextView) convertView.findViewById(R.id.titleTv_note_item_listview));
				holder.setTimeTv((TextView) convertView.findViewById(R.id.timeTv_note_item_listview));
				holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkbox_note_item_listview));
				holder.setDeleteBtn((RelativeLayout) convertView.findViewById(R.id.deleteBtn_note_listview_item));
				convertView.setTag(holder);
			}else{
				convertView = inflater.inflate(R.layout.note_gridview_item, null);
				holder = new ViewHolder();
				holder.setTitleTv((TextView) convertView.findViewById(R.id.titleTv_note_item_gridview));
				holder.setTimeTv((TextView) convertView.findViewById(R.id.timeTv_note_item_gridview));
				holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkbox_note_item_gridview));
				holder.setDeleteBtn((RelativeLayout) convertView.findViewById(R.id.deleteBtn_note_gridview_item));
				convertView.setTag(holder);
			}
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final NoteEntity note = notes.get(position);
		holder.getTitleTv().setText(note.getTitle());
		if(tagShowStyle.equals(NotebookActivity.TAG_LIST)){
			holder.getTimeTv().setText(getMyTimeForList(note.getTime()));
		}else{
			holder.getTimeTv().setText(getMyTimeForGrid(note.getTime()));
		}
		final CheckBox checkBox = holder.getCheckBox();
		RelativeLayout deleteBtn = holder.getDeleteBtn();
		if(rootAty.tag_delete.equals(NotebookActivity.TAG_DELETEING)){
			deleteBtn.setVisibility(View.VISIBLE);
			checkBox.setChecked(note.isChecked());

			deleteBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(checkBox.isChecked()){
						checkBox.setChecked(false);
						note.setChecked(false);
						deleteIds.remove(Integer.valueOf(note.getId()));
					}else{
						checkBox.setChecked(true);
						note.setChecked(true);
						deleteIds.add(Integer.valueOf(note.getId()));
					}
					if(deleteIds.size()>0){
						rootAty.deleteLayout.changeDelete();
						if(deleteIds.size()==notes.size()){
							rootAty.selectAllBtn.setText("取消全选");
						}else{
							rootAty.selectAllBtn.setText("全部选择");
						}
					}else{
						rootAty.deleteLayout.changeNoDelete();
						rootAty.selectAllBtn.setText("全部选择");
					}
					rootAty.selectDeleteNumTv.setText(deleteIds.size()+"");
				}
			});
			//			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//				
			//				@Override
			//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//					note.setChecked(isChecked);
			//					System.out.println("将note设置为"+isChecked);
			//				}
			//			});
		}else{
			deleteBtn.setVisibility(View.GONE);
		}
		return convertView;
	}
	private String getMyTimeForList(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm,E",Locale.CHINA);
		String myTime = sdf.format(Long.parseLong(time));
		return myTime;
	}
	private String getMyTimeForGrid(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd, HH:mm,E",Locale.CHINA);
		String myTime = sdf.format(Long.parseLong(time));
		return myTime;
	}
	public void updateAllSelect(){
		for (NoteEntity note : notes) {
			note.setChecked(true);
			if(!deleteIds.contains(Integer.valueOf(note.getId()))){
				deleteIds.add(Integer.valueOf(note.getId()));
			}
		}
		rootAty.deleteLayout.changeDelete();
		this.notifyDataSetChanged();
		rootAty.selectDeleteNumTv.setText(notes.size()+"");
	}
	public void updateUnAllSelect(){
		for (NoteEntity note : notes) {
			note.setChecked(false);
			deleteIds.remove(Integer.valueOf(note.getId()));
		}
		rootAty.deleteLayout.changeNoDelete();
		this.notifyDataSetChanged();
		rootAty.selectDeleteNumTv.setText("0");
	}
	public void update(){
		deleteIds.clear();
		for (NoteEntity note : notes) {
			note.setChecked(false);
		}
		this.notifyDataSetChanged();
	}

	public void resetNotes(){
		notes = helper.getNotes();
		notifyDataSetChanged();
	}
	
	class ViewHolder{
		private TextView titleTv,timeTv;
		private CheckBox checkBox;
		private RelativeLayout deleteBtn;

		public RelativeLayout getDeleteBtn() {
			return deleteBtn;
		}

		public void setDeleteBtn(RelativeLayout deleteBtn) {
			this.deleteBtn = deleteBtn;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public void setCheckBox(CheckBox checkBox) {
			this.checkBox = checkBox;
		}

		public TextView getTitleTv() {
			return titleTv;
		}

		public void setTitleTv(TextView titleTv) {
			this.titleTv = titleTv;
		}

		public TextView getTimeTv() {
			return timeTv;
		}

		public void setTimeTv(TextView timeTv) {
			this.timeTv = timeTv;
		}

	}

}
