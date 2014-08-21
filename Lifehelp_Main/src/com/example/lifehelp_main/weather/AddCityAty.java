package com.example.lifehelp_main.weather;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.lifehelp_main.R;
import com.example.lifehelp_main.weather.adapter.GridViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AddCityAty extends Activity {
	// �������
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String address = location.getAddrStr();
			String city = location.getCity();
			System.out.println(city + "\n" + address);
			if (city.endsWith("��")) {
				city = city.substring(0, city.length() - 1);
			}
			Intent intent = getIntent();
			intent.putExtra("city_name", city);
			setResult(RESULT_OK, intent);
			finish();
		}
	};

	private String[] citys = { "��λ", "����", "�Ϻ�", "����", "����", "�麣", "��ɽ", "�Ͼ�",
			"����", "����", "����", "�ൺ", "֣��", "ʯ��ׯ", "����", "����", "�人", "��ɳ", "�ɶ�",
			"����", "̫ԭ", "����", "����", "����", "����", "��˳" };
	private GridView gridView;
	private GridViewAdapter adapter;
	/** ���ذ�ť **/
	private ImageButton btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_add_city);
		initLocation();

		gridView = (GridView) findViewById(R.id.gridView);
		gridView.getViewTreeObserver().addOnGlobalLayoutListener(
				globalLayoutListener);
		gridView.setOnItemClickListener(itemCLickListener);
		btnBack = (ImageButton) findViewById(R.id.btnBack_add_city);
		btnBack.setOnClickListener(clickListener);
	}

	/** ��ʼ����λ��ص� **/
	private void initLocation() {
		// ����LocationClient��
		mLocationClient = new LocationClient(getApplicationContext());
		// ����һ�������á�
		LocationClientOption option = new LocationClientOption();
		// ���ö�λ����
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		mLocationClient.setLocOption(option);
		// ע���������
		mLocationClient.registerLocationListener(myListener);
	}

	private OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			setResult(RESULT_OK);
			finish();
		}
	};
	/** ΪGridView���õ�item�������� **/
	OnItemClickListener itemCLickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 0) {
				// ��λ
				mLocationClient.start();
			} else {
				String cityName = adapter.getItem(position);
				Intent intent = getIntent();
				intent.putExtra("city_name", cityName);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	};

	private boolean isFirst = true;
	OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {

		@Override
		public void onGlobalLayout() {
			if (isFirst) {
				isFirst = false;
				int itemHeight = gridView.getHeight() / 8;
				adapter = new GridViewAdapter(citys, AddCityAty.this,
						itemHeight);
				gridView.setAdapter(adapter);
			}
		}
	};

	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	};
}
