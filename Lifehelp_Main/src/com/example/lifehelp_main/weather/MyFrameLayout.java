package com.example.lifehelp_main.weather;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class MyFrameLayout extends FrameLayout{
	private Scroller scroller;
	public MyFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(getContext());
	}
	//View在刷新的时候会调用一次这个方法
	@Override
	public void computeScroll() {
		//如果返回true,代表scroll滚动没结束，并且他会继续计算下一个滚动位置
		if(scroller.computeScrollOffset()){
			//获取到scroller中计算出来的x滚动位置
			int x = scroller.getCurrX();
			//获取到scroller中计算出来的y滚动位置
			int y = scroller.getCurrY();
			System.out.println("y"+y);
			//直接滚动到(x,y)
			scrollTo(x, y);
			invalidate();
			
		}
	}
	public void smoothScrollTo(int scrollY){
		scroller.startScroll(getScrollX(), getScrollY(), 0, scrollY-getScrollY(), 500);
		invalidate();
	}
	
}
