package com.example.linkagescroll.itemDecoration;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by moon.zhong on 2015/2/11.
 */

/**
 * Created by no1 on 2016/7/14.
 */


public class AlphaDividerItemDecoration extends RecyclerView.ItemDecoration {

    public final static int GRID = 0;
    public final static int LIST = 1;

    private int direct = GRID;

    private int space;

    public AlphaDividerItemDecoration(int space) {
        this.space = space;
    }

    public AlphaDividerItemDecoration(int space, int direct) {
        this.space = space;
        this.direct = direct;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (direct == GRID) {
            outRect.left = space;
            outRect.bottom = space * 2;
            outRect.right = space;
            if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
                outRect.top = space;
            }
        } else {
            outRect.left = space;
            outRect.bottom = space * 2;
            outRect.right = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}