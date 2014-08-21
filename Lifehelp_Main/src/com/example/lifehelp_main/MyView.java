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

	// ���viewҪ��xml��ʹ�õĻ����ͱ�����������췽��
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
		// ��ͼƬ��Դת��Drawable����
		Drawable topDrawable = getResources().getDrawable(
				R.drawable.homepage_logo_weather);
		// ��TextView���ϡ��ҡ�������һ��ͼƬ ��Ӧxml��TextView android:drawableTop ����
		// tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, topDrawable,
		// null, null);
		// ����
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		iv = new ImageView(getContext());
		iv.setImageResource(R.drawable.fingerprint);
		// ��ӿؼ�
		addView(tv, params);
		addView(iv);
		// ��������ӡͼƬ����
		iv.setVisibility(View.GONE);

		initAnim();
	}

	/**
	 * ��ʼ��2�����Ŷ���
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

	/** �ؼ��Ŀ� **/
	private int viewWidth;
	/** �ؼ��ĸ� **/
	private int viewHeight;
	/** �����¼��Ƿ���˱߽� **/
	private boolean isOut = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, event.getX() + "," + event.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ����ӡͼƬ��ʾ
			iv.setVisibility(View.VISIBLE);
			this.startAnimation(scaleIn);
			viewWidth = getWidth();
			viewHeight = getHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			if (isOut) {
				break;
			}
			// ���߽紦��
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
				// ʵ�ֵ��
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
