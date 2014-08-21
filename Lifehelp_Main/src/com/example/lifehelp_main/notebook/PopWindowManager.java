package com.example.lifehelp_main.notebook;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class PopWindowManager {

	public static PopupWindow  getPopupWindow(View view){
		final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setOutsideTouchable(true);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
		return pop;
	}
}
