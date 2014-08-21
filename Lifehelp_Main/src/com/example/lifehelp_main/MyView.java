package com.example.lifehelp_main;

import com.example.lifehelp_main.R;
import com.example.lifehelp_main.untl.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyView extends FrameLayout {
	public static final String TAG = "MyView";
	private TextView tv;
	private ImageView iv;
	private ScaleAnimation scaleIn;
	private ScaleAnimation scaleOut;

	public MyView(Context context) {
		super(context);
		init();
	}

	// 如果view要在xml中使用的话，就必须有这个构造方法
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		tv = new TextView(getContext());
		tv.setTextColor(0xffffffff);
		tv.setTextSize(19);
		tv.setGravity(Gravity.CENTER);
		// 将图片资源转成Drawable对象
		Drawable topDrawable = getResources().getDrawable(
				R.drawable.homepage_logo_weather);
		// 在TextView左、上、右、下设置一张图片 对应xml中TextView android:drawableTop 属性
		// tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, topDrawable,
		// null, null);
		// 布局
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		iv = new ImageView(getContext());
		iv.setImageResource(R.drawable.fingerprint);
		// 添加控件
		addView(tv, params);
		addView(iv);
		// 首先让手印图片隐藏
		iv.setVisibility(View.GONE);

		initAnim();
	}

	/**
	 * 初始化2个缩放动画
	 */
	private void initAnim() {
		scaleIn = new ScaleAnimation(1, 0.95f, 1, 0.95f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleIn.setDuration(200);
		scaleIn.setFillAfter(true);
		scaleOut = new ScaleAnimation(0.95f, 1, 0.95f, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleOut.setDuration(200);
		scaleOut.setFillAfter(true);
	}

	/** 控件的宽 **/
	private int viewWidth;
	/** 控件的高 **/
	private int viewHeight;
	/** 触摸事件是否出了边界 **/
	private boolean isOut = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, event.getX() + "," + event.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 让手印图片显示
			iv.setVisibility(View.VISIBLE);
			this.startAnimation(scaleIn);
			viewWidth = getWidth();
			viewHeight = getHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			if (isOut) {
				break;
			}
			// 做边界处理
			if (event.getY() < 0 || event.getX() < 0
					|| event.getY() > viewHeight || event.getX() > viewWidth) {
				iv.setVisibility(View.GONE);
				this.startAnimation(scaleOut);
				isOut = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (!isOut) {
				iv.setVisibility(View.GONE);
				this.startAnimation(scaleOut);
				// 实现点击
				getContext().startActivity(new Intent(getContext(), c));
			}
			isOut = false;
			break;
		}
		return true;
	}

	@SuppressLint("NewApi")
	public void setData(Item item) {
		tv.setText(item.getText());
		Drawable drawable = getResources().getDrawable(item.getDrawableId());
		if (item.getState() == 1) {
			tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable,
					null, null);
		} else {
			tv.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null,
					null, null);
		}
		setBackgroundColor(item.getColor());
		this.c = item.getC();
	}

	private Class<?> c;
}
