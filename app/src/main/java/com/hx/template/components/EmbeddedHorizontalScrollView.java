package com.hx.template.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by huangx on 2016/7/30.
 */
public class EmbeddedHorizontalScrollView extends HorizontalScrollView {

    private GestureDetector mGestureDetector;

    public EmbeddedHorizontalScrollView(Context context) {
        this(context, null);
    }

    public EmbeddedHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmbeddedHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                return true;
            }
            return false;
        }
    }
}
