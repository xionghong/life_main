package com.example.lifehelp_main.notebook;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.notebook.config.MyConfig;
import com.example.lifehelp_main.notebook.db.NoteDBHelper;
import com.example.lifehelp_main.notebook.entity.NoteEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NewNoteAty extends Activity implements OnClickListener, TextWatcher{

	
	private ImageView backBtn;
	private TextView dateTv,timeTv,weekdayTv;
	private Button saveBtn;
	private EditText et;
	private NoteDBHelper helper;
	private int flag;
	private int noteId;
	String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_create_new);
		flag = getIntent().getFlags();
		helper = new NoteDBHelper(this);
		initViews();
		if(flag == MyConfig.REQUEST_CODE_NEW_NOTE){
			initTextsForNewNote();
		}else{
			initTextsForSeeNote();
		}

	}

	void initViews(){
		backBtn = (ImageView) findViewById(R.id.back_note_create_new);
		backBtn.setOnClickListener(this);
		dateTv = (TextView) findViewById(R.id.dateTV_note_create_new);
		timeTv = (TextView) findViewById(R.id.timeTV_note_create_new);
		weekdayTv = (TextView) findViewById(R.id.weekday_note_create_new);
		et = (EditText) findViewById(R.id.et_note_create_new);
		saveBtn = (Button) findViewById(R.id.saveBtn_note_create_new);
		saveBtn.setOnClickListener(this);
	}

	private void initTextsForSeeNote() {
		NoteEntity note = (NoteEntity) getIntent().getSerializableExtra("note");
		SimpleDateFormat sdf = new SimpleDateFormat("MM 月 dd 日,HH:mm,E",Locale.CHINA);
		String time = note.getTime();
		String data = sdf.format(Long.parseLong(time));
		String mTime = data.split(",")[1];
		timeTv.setText(mTime);
		String weekday = data.split(",")[2];
		weekdayTv.setText(weekday);
		String date = data.split(",")[0];
		dateTv.setText(date);
		content = note.getTitle();
		et.setText(content);
		noteId = note.getId();
		saveBtn.setText("编辑");
		et.setFocusable(false);
	}

	private void initTextsForNewNote() {
		//yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf = new SimpleDateFormat("MM 月 dd 日,HH:mm,E",Locale.CHINA);
		String data = sdf.format(System.currentTimeMillis());
		String time = data.split(",")[1];
		timeTv.setText(time);
		String weekday = data.split(",")[2];
		weekdayTv.setText(weekday);
		String date = data.split(",")[0];
		dateTv.setText(date);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_note_create_new:
			dealSave();
			break;

		case R.id.saveBtn_note_create_new:
			if(saveBtn.getText().equals("编辑")){
				saveBtn.setText("删除");
				et.setFocusableInTouchMode(true);
				showSoftKeyboard(et);
				et.setSelection(et.getText().toString().length());
				et.addTextChangedListener(this);
				System.out.println(et.isFocusable());
				break;
			}else if(saveBtn.getText().equals("删除")){
				showDeleteDialog();
				break;
			}
			dealSave();
			break;
		}

	}
	/**
	 * 为EdieText设置弹出软键盘
	 * @param et
	 */
	private void showSoftKeyboard(EditText et){
		InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et,InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
	}
	private void dealSave() {
		if(flag == MyConfig.REQUEST_CODE_NEW_NOTE){
			String contentNew = et.getText().toString();
			if(!TextUtils.isEmpty(contentNew)){
				helper.insert(contentNew);
			}
			finish();
			closeKeyboard(et);
		}else{
			String contentNew = et.getText().toString();
			if(TextUtils.isEmpty(contentNew)){
				helper.deleteNoteById(noteId);
			}else{
				helper.updateNoteById(noteId, contentNew);
			}
			closeKeyboard(et);
			finish();
		}
	}
	private void showDeleteDialog() {
		closeKeyboard(et);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("删除提示");
		dialog.setMessage("确定要删除该条笔记？");
		dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				helper.deleteNoteById(noteId);
				setResult(MyConfig.RESULT_CODE_SEE_NOTE );
				finish();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.show();
	}
	//关闭软键盘  
	 private void closeKeyboard(EditText et) {  
	     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	     imm.hideSoftInputFromWindow(et.getWindowToken(), 0);  
	}  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			dealSave();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		saveBtn.setText("保存");
	}

	@Override
	public void afterTextChanged(Editable s) {
	}
}
