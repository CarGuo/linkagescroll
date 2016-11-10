package com.example.linkagescroll.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by shuyu on 2016/11/10.
 */

public class BannerViewPager extends ViewPager {

    private boolean mLocked;

    private float mDownX, mDownY;
    private SwipeRefreshLayout swipeRefreshLayout;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setEnabled(false);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setEnabled(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setEnabled(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setEnabled(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !mLocked && super.onInterceptTouchEvent(ev);
    }

    public void setLocked(boolean locked) {
        this.mLocked = locked;
    }
}
