package com.example.linkagescroll.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by shuyu on 2016/8/11.
 */

public class ListViewPager extends ViewPager {

    private int touchPadding;

    public ListViewPager(Context context) {
        super(context);
    }

    public ListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float mDownX, mDownY;
    private boolean mIsTop = true;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }


    /**
     * 通过手势判断，设置焦点的切换和刷新的是否可用，根据不同手势势能刷新
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getY() < touchPadding)
            return false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                swipeRefreshLayout.setEnabled(false);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    enableSwipeRefresh();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                enableSwipeRefresh();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void enableSwipeRefresh() {
        if (swipeRefreshLayout != null && mIsTop) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    public void setTouchPadding(int touchPadding) {
        this.touchPadding = touchPadding;
    }

    public void setIsTop(boolean isTop) {
        this.mIsTop = isTop;
    }
}
