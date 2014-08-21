package com.example.lifehelp_main;

import com.example.lifehelp_main.about.AboutActivity;
import com.example.lifehelp_main.calculator.CalculatorActivity;
import com.example.lifehelp_main.calendar.CalendarActivity;
import com.example.lifehelp_main.contents.ContentsActivity;
import com.example.lifehelp_main.express.ExpressActivity;
import com.example.lifehelp_main.notebook.NotebookActivity;
import com.example.lifehelp_main.tools.CopyFile;
import com.example.lifehelp_main.untl.Item;
import com.example.lifehelp_main.weather.WeatherActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private int[] viewIds = { R.id.view1, R.id.view2, R.id.view3, R.id.view4,
			R.id.view5, R.id.view6, R.id.view7 };
	private MyView[] views = new MyView[viewIds.length];

	private String[] titles = { "天气查询", "记事本", "快递查询", "通讯录", "计算器", "日历", "关于" };
	private int[] states = { 1, 1, 1, 1, 1, 1, 2 };
	private int[] colors = { 0xff0e8dab, 0xff1BC366, 0xff498CFF, 0xff01CAD4,
			0xffF48221, 0xff22BB19, 0xff1BC366 };
	private Class<?>[] classes = { WeatherActivity.class,
			NotebookActivity.class, ExpressActivity.class,
			ContentsActivity.class, CalculatorActivity.class,
			CalendarActivity.class, AboutActivity.class };
	private int[] drawableIds = { R.drawable.homepage_logo_weather,
			R.drawable.homepage_logo_notebook, R.drawable.homepage_logo_search,
			R.drawable.homepage_logo_contents,
			R.drawable.homepage_logo_calculator,
			R.drawable.homepage_logo_calendar, R.drawable.homepage_logo_about };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		for (int i = 0; i < viewIds.length; i++) {
			views[i] = (MyView) findViewById(viewIds[i]);
			Item item = new Item(titles[i], drawableIds[i], colors[i],
					classes[i], states[i]);
			views[i].setData(item);
		}
	}

	private void copyDb() {
		CopyFile.copyFileFromResToPhone("/data/data/" + getPackageName()
				+ "/databases", "phonenumber.db", getResources()
				.openRawResource(R.raw.phonenumber));
		CopyFile.copyFileFromResToPhone("/data/data/" + getPackageName()
				+ "/databases", "express.db",
				getResources().openRawResource(R.raw.express));
		CopyFile.copyFileFromResToPhone("/data/data/" + getPackageName()
				+ "/databases", "kuaidi.db",
				getResources().openRawResource(R.raw.kuaidi));

	}

}
