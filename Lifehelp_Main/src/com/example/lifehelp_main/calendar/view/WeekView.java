package com.example.lifehelp_main.calendar.view;



import com.example.lifehelp_main.calendar.Constant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.FontMetricsInt;
import android.util.AttributeSet;
import android.view.View;

public class WeekView extends View {
	private int itemWidth = Constant.itemWidth;
	private int viewHight = Constant.weekViewHight;
	private String[] weeks = { "日", "一", "二", "三", "四", "五", "六" };
	private Paint paint;

	private RectF mRectF;
	/** 画文字的基准线 **/
	private int textBaseLine;

	public WeekView(Context context) {
		super(context);
		init();
	}

	public WeekView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setTextSize(15);
		paint.setAntiAlias(true);

		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		mRectF = new RectF(0, 0, Constant.screenWidth, viewHight);
		textBaseLine = (int) (mRectF.top
				+ (mRectF.bottom - mRectF.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < weeks.length; i++) {
			if (i == 0 || i == 6) {
				paint.setColor(0xffff0000);
			} else {
				paint.setColor(0x88000000);
			}
			float measureText = paint.measureText(weeks[i]);
			canvas.drawText(weeks[i], i * itemWidth + (itemWidth - measureText)
					/ 2, textBaseLine, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, viewHight);
	}
}
