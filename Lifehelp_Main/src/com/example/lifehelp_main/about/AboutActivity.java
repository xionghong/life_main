package com.example.lifehelp_main.about;


import com.example.lifehelp_main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class AboutActivity extends Activity{
	
	private Button back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_main);
		
		back = (Button) findViewById(R.id.about_back);
	}

}
