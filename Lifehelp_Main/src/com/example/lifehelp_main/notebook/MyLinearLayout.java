package com.example.lifehelp_main.notebook;

import com.example.lifehelp_main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyLinearLayout extends LinearLayout{
	
	private ImageView mImageView;
	private TextView mTextView;
	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		addViews(context);
	}
	private void addViews(Context context) {
		mImageView = new ImageView(context);
		mTextView = new TextView(context);
		mImageView.setImageResource(R.drawable.btn_text_delete_pressed);
		mImageView.setFocusable(false);
		mTextView.setText("É¾³ý");
		mTextView.setTextSize(20);
		mTextView.setTextColor(0xFFB3B3B3);
		setOrientation(LinearLayout.HORIZONTAL); 
		setGravity(Gravity.CENTER);
		addView(mImageView);
		addView(mTextView);
	}
	public void changeNoDelete(){
		mImageView.setImageResource(R.drawable.btn_text_delete_pressed);
		mTextView.setTextColor(0xFFB3B3B3);
		this.setEnabled(false);
	}
	public void changeDelete(){
		mImageView.setImageResource(R.drawable.btn_text_delete_normal);
		mTextView.setTextColor(0xFFFFFFFF);
		this.setEnabled(true);
	}

}
