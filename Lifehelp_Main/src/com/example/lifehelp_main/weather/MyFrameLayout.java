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
	//View��ˢ�µ�ʱ������һ���������
	@Override
	public void computeScroll() {
		//�������true,����scroll����û�����������������������һ������λ��
		if(scroller.computeScrollOffset()){
			//��ȡ��scroller�м��������x����λ��
			int x = scroller.getCurrX();
			//��ȡ��scroller�м��������y����λ��
			int y = scroller.getCurrY();
			System.out.println("y"+y);
			//ֱ�ӹ�����(x,y)
			scrollTo(x, y);
			invalidate();
			
		}
	}
	public void smoothScrollTo(int scrollY){
		scroller.startScroll(getScrollX(), getScrollY(), 0, scrollY-getScrollY(), 500);
		invalidate();
	}
	
}
