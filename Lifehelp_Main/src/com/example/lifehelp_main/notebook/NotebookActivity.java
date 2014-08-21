package com.example.lifehelp_main.notebook;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.notebook.adapter.NoteAdapter;
import com.example.lifehelp_main.notebook.config.MyConfig;
import com.example.lifehelp_main.notebook.db.NoteDBHelper;
import com.example.lifehelp_main.notebook.entity.NoteEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotebookActivity extends Activity implements OnClickListener, OnItemLongClickListener, OnItemClickListener{

	
	public static final String TAG_LIST = "list";
	public static final String TAG_GRID = "grid";
	public static final String TAG_NORMAL = "normal";
	public static final String TAG_DELETEING = "deleteing";
	public String tag_delete = TAG_NORMAL;
	//记录笔记的显示方式
	private String tagShowStyle;
	private Button listStyleBtn,newNoteBtn;
	private PopupWindow pop;
	private View popView;
	private SharedPreferences preferences;
	private Button createNoteBtn;
	AdapterView<?> adapterView;
	private ListView listView;
	private GridView gridView;
	private NoteAdapter adapter;
	private NoteDBHelper helper;
	//定义普通跟删除时显示的两个title
	private RelativeLayout titleNormal,titleDelete;
	public  MyLinearLayout deleteLayout;
	public Button selectAllBtn;
	public TextView selectDeleteNumTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_main);

		helper = new NoteDBHelper(this);
		initViews();
		initNoteShowStlye();
	}
	/**
	 * 初始化笔记的显示方式（是列表显示，还是网格显示）
	 */
	private void initNoteShowStlye(){
		preferences = getSharedPreferences("note", MODE_PRIVATE);
		tagShowStyle = preferences.getString("note_show_style", TAG_LIST);
		if(TAG_LIST.equals(tagShowStyle)){
			showListView();
		}else{
			showGridView();
		}
	}
	/**
	 * 为ListView填充数据
	 */
	void showListView(){
		listView.setVisibility(View.VISIBLE);
		gridView.setVisibility(View.GONE);
		adapter = new NoteAdapter(this, TAG_LIST);
		listView.setAdapter(adapter);
		tagShowStyle = TAG_LIST;
	}
	/**
	 * 为GridView填充数据
	 */
	void showGridView(){
		gridView.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		adapter = new NoteAdapter(this,TAG_GRID);
		gridView.setAdapter(adapter);
		tagShowStyle = TAG_GRID;
	}
	/**
	 * 初始化控件，分别找Id
	 */
	private void initViews(){
		createNoteBtn = (Button) findViewById(R.id.createNoteBtn_note_empty);
		createNoteBtn.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView_note_main);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setEmptyView(findViewById(R.id.emptyView_note));
		gridView = (GridView) findViewById(R.id.gridView_note_main);
		gridView.setEmptyView(findViewById(R.id.emptyView_note));
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
		listStyleBtn = (Button) findViewById(R.id.listStyleBtn_note_main);
		listStyleBtn.setOnClickListener(this);
		newNoteBtn = (Button) findViewById(R.id.newNoteBtn_note_main);
		newNoteBtn.setOnClickListener(this);
		initPop();
		titleNormal = (RelativeLayout) findViewById(R.id.title_normal_note_main);
		titleDelete = (RelativeLayout) findViewById(R.id.title_delete_note_main);
		deleteLayout = (MyLinearLayout) findViewById(R.id.deleteLayout_note_main);
		deleteLayout.setOnClickListener(this);
		selectAllBtn = (Button) findViewById(R.id.selectAllBtn_note_main_title_delete);
		selectAllBtn.setOnClickListener(this);
		selectDeleteNumTv = (TextView) findViewById(R.id.selectDeleteNumTv_note_main_title_delete);
	}
	/**
	 * 初始化PopupWindow
	 */
	private void initPop(){
		popView = LayoutInflater.from(this).inflate(R.layout.note_pop, null);
		pop = PopWindowManager.getPopupWindow(popView);
		pop.setFocusable(true);
		Bitmap bitmap = null;
		pop.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap ));
		pop.setAnimationStyle(R.style.AnimationPreview);
		pop.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});
		TextView listShow = (TextView) popView.findViewById(R.id.listShow_pop);
		TextView gridShow = (TextView) popView.findViewById(R.id.gridShow_pop);
		listShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showListView();
				pop.dismiss();
			}
		});
		gridShow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showGridView();
				pop.dismiss();
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createNoteBtn_note_empty:
		case R.id.newNoteBtn_note_main:
			Intent intent = new Intent(this,NewNoteAty.class);
			intent.setFlags(MyConfig.REQUEST_CODE_NEW_NOTE);
			startActivityForResult(intent, MyConfig.REQUEST_CODE_NEW_NOTE);
			break;

		case R.id.listStyleBtn_note_main:
			if(pop.isShowing()){
				pop.dismiss();
			}else{
				pop.showAsDropDown(v);
				System.out.println(pop.isShowing());
			}
			break;
		case R.id.deleteLayout_note_main:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("删除提示");
			dialog.setMessage("确定要删除笔记？");
			dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(tagShowStyle.equals(TAG_LIST)){
						helper.deleteMore(adapter.deleteIds);
						showNormalTitle();
						showListView();
					}else{
						helper.deleteMore(adapter.deleteIds);
						showNormalTitle();
						showGridView();
					}
				}
			});
			dialog.setNegativeButton("取消", null);
			dialog.show();
			break;
		case R.id.selectAllBtn_note_main_title_delete:
			if(selectAllBtn.getText().equals("全部选择")){
				selectAllBtn.setText("取消全选");
				adapter.updateAllSelect();
			}else{
				selectAllBtn.setText("全部选择");
				adapter.updateUnAllSelect();
			}
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==MyConfig.RESULT_CODE_NEW_NOTE_SAVE){
			if(tagShowStyle.equals(TAG_LIST)){
				showListView();
			}else{
				showGridView();
			}
		}else{
			adapter.resetNotes();
		}
	}
	private void showNormalTitle(){
		titleNormal.setVisibility(View.VISIBLE);
		titleDelete.setVisibility(View.GONE);
		deleteLayout.setVisibility(View.GONE);
		tag_delete = TAG_NORMAL;
	}
	private void showDeleteTitle(){
		titleDelete.setVisibility(View.VISIBLE);
		titleNormal.setVisibility(View.GONE);
		deleteLayout.setVisibility(View.VISIBLE);
		selectDeleteNumTv.setText("0");
		selectAllBtn.setText("全部选择");
		tag_delete = TAG_DELETEING;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//将笔记的显示方式保存到SharedPreferences
		preferences.edit().putString("note_show_style", tagShowStyle).commit();
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if(tag_delete.equals(TAG_NORMAL)){
			showDeleteTitle();
			adapter.update();
		}
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		NoteEntity note = (NoteEntity) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this,NewNoteAty.class);
		intent.setFlags(MyConfig.REQUEST_CODE_SEE_NOTE);
		intent.putExtra("note", note);
		startActivityForResult(intent, MyConfig.REQUEST_CODE_SEE_NOTE);
	}
	@Override
	public void onBackPressed() {
		if(tag_delete.equals(TAG_DELETEING)){
			showNormalTitle();
		}else{
			super.onBackPressed();
		}
	}
}
