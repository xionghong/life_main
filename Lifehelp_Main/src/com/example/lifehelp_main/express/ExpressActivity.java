package com.example.lifehelp_main.express;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.R.id;
import com.example.lifehelp_main.R.layout;
import com.example.lifehelp_main.R.raw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpressActivity extends Activity {

	private TextView tvTipNum, tvTipCompany, tvCompanyName;
	private EditText etNum;
	private View chooseCompany;
	private ImageView ivClear;
	private Button btnSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.express_main);

		initViews();

		// �жϳ����Ƿ��ǵ�һ������
		if (isFirstRun()) {
			copyDb();
		}
	}

	/** ��raw�µ����ݿ⸴�Ƶ� /data/data/����/databases/ Ŀ¼���� **/
	private void copyDb() {
		String dir = "/data/data/" + getPackageName() + "/databases";
		String fileName = "express.db";
		InputStream is = getResources().openRawResource(R.raw.express);
		copyFile(dir, fileName, is);
	}

	/** �����ļ��ķ��� **/
	private void copyFile(String dir, String fileName, InputStream is) {
		// �жϸ�Ŀ¼�治���ڣ���������ھʹ�����Ŀ¼
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File file = new File(dirFile, fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int len;
			while ((len = is.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** ��ʼ���ؼ� **/
	private void initViews() {
		tvTipNum = (TextView) findViewById(R.id.tv_tip_num);
		tvTipCompany = (TextView) findViewById(R.id.tv_tip_company);
		etNum = (EditText) findViewById(R.id.et_num);
		chooseCompany = findViewById(R.id.layout_choose_company);
		ivClear = (ImageView) findViewById(R.id.iv_clear_company);
		tvCompanyName = (TextView) findViewById(R.id.tv_company_name);
		btnSearch = (Button) findViewById(R.id.btn_search);

		ivClear.setOnClickListener(onClickListener);
		etNum.addTextChangedListener(watcher);
		chooseCompany.setOnClickListener(onClickListener);
	}

	/*** ΪEditText���õļ����� **/
	private TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (TextUtils.isEmpty(s)) {
				tvTipNum.setVisibility(View.INVISIBLE);
				btnSearch.setEnabled(false);
			} else {
				tvTipNum.setVisibility(View.VISIBLE);
				if (!TextUtils.isEmpty(tvCompanyName.getText())) {
					btnSearch.setEnabled(true);
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	};
	/*** �ؼ��ĵ��������� **/
	private OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_clear_company:
				// ��TextView���
				tvCompanyName.setText("");
				v.setVisibility(View.GONE);
				tvTipCompany.setVisibility(View.INVISIBLE);
				btnSearch.setEnabled(false);
				break;
			case R.id.layout_choose_company:
				// ��ת��ѡ���ݹ�˾���棬�������и����ؽ��
				Intent intent = new Intent(getApplicationContext(),
						ChooseCompanyAty.class);
				startActivityForResult(intent, 1);
				break;
			}
		}
	};

	/** �жϳ����Ƿ��ǵ�һ������ **/
	private boolean isFirstRun() {
		SharedPreferences share = getSharedPreferences("shared", MODE_PRIVATE);
		boolean isFirst = share.getBoolean("isFirstRun", true);
		if (isFirst) {
			Editor editor = share.edit();
			editor.putBoolean("isFirstRun", false);
			editor.commit();
		}
		return isFirst;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			String companyName = data.getStringExtra("companyName");
			tvCompanyName.setText(companyName);
			// ����˾������ʾ������ʾ����
			tvTipCompany.setVisibility(View.VISIBLE);
			ivClear.setVisibility(View.VISIBLE);

			// �ж�������ť�ܷ����óɿɵ��
			if (!TextUtils.isEmpty(etNum.getText())) {
				btnSearch.setEnabled(true);
			}
		}
	}
}
