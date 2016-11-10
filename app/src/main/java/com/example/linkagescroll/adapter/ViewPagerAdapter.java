package com.example.linkagescroll.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.linkagescroll.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<?> list;

    public ViewPagerAdapter(Context context, List<?> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int i) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.xxx1);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }
}