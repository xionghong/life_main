package com.example.lifehelp_main.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {
	private Paint paint;

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0x44444444);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(), paint);
		canvas.drawLine(getWidth() * 2 / 3, 0, getWidth() * 2 / 3, getHeight(),
				paint);

		for (int i = 1; i < 8; i++) {
			canvas.drawLine(0, getHeight() / 8 * i, getWidth(), getHeight() / 8
					* i, paint);
		}
	}
}
