package com.hang.study.gzinfo.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hang on 16/7/22.
 */
public class MyTabViewPager extends ViewPager {
    public MyTabViewPager(Context context) {
        super(context);
    }

    public MyTabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       // getParent().requestDisallowInterceptTouchEvent(false);
        return super.onInterceptTouchEvent(ev);
    }
}
