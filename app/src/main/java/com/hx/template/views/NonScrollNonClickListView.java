package com.hx.template.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class NonScrollNonClickListView extends ListView {

	public NonScrollNonClickListView(Context context) {
		super(context);
	}

	public NonScrollNonClickListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NonScrollNonClickListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightSpec);
		getLayoutParams().height = getMeasuredHeight();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 不处理触摸事件
		return false;
	}
}
