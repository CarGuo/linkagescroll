package com.example.linkagescroll.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.linkagescroll.fragment.ListFragment;

import java.util.List;

/**
 * Created by shuyu on 2016/8/11.
 */
public class ListFragmentPagerAdapter extends FragmentPagerAdapter  {

    private List<ListFragment> fragmentList;
    private List<String> titleList;

    public ListFragmentPagerAdapter(FragmentManager fm, List<ListFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}