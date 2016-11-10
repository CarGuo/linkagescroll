package com.example.linkagescroll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.linkagescroll.adapter.ListFragmentPagerAdapter;
import com.example.linkagescroll.adapter.ViewPagerAdapter;
import com.example.linkagescroll.fragment.ListFragment;
import com.example.linkagescroll.listener.DoubleClickListener;
import com.example.linkagescroll.utils.ScreenUtils;
import com.example.linkagescroll.widget.BannerViewPager;
import com.example.linkagescroll.widget.ListViewPager;
import com.example.linkagescroll.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.scroll_Viewpager)
    ListViewPager scrollViewpager;
    @BindView(R.id.scroll_imageBanner)
    BannerViewPager scrollImageBanner;
    @BindView(R.id.scroll_imageBanner_layout)
    RelativeLayout scrollImageBannerLayout;
    @BindView(R.id.scroll_toolbar)
    Toolbar scrollToolbar;
    @BindView(R.id.scroll_pagerSlidingTabStrip)
    PagerSlidingTabStrip scrollPagerSlidingTabStrip;
    @BindView(R.id.scroll_collapsingToolbarLayout)
    CollapsingToolbarLayout scrollCollapsingToolbarLayout;
    @BindView(R.id.scroll_appbar)
    AppBarLayout scrollAppbar;
    @BindView(R.id.scroll_coordinatorLayout)
    CoordinatorLayout scrollCoordinatorLayout;
    @BindView(R.id.scroll_swipeRefreshLayout)
    SwipeRefreshLayout scrollSwipeRefreshLayout;

    private ListFragment fragment1;
    private ListFragment fragment2;

    private List<ListFragment> fragmentList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private ListFragmentPagerAdapter listStripFragmentPagerAdapter;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private int devider = 0;
    private int curTab = 0;
    private int tabScroll;
    private int scrollOffset = 4444;
    private int scrollOffsetSquare = 0;
    private int scrollOffsetAttention = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        devider = ScreenUtils.dip2px(this, 5);

        scrollCollapsingToolbarLayout.setEnabled(false);

        resolveBanner();

        resolveViewPager();

        setListener();


        /**这是tab的高度，用来设置此高度后tab可以停靠*/
        int height = (int) getResources().getDimension(R.dimen.banner_strip_height);

        setViewHeight(scrollToolbar, ViewGroup.LayoutParams.MATCH_PARENT, height);

        tabScroll = ScreenUtils.getScreenWidth(this) / 5;

        scrollViewpager.setTouchPadding(height);
    }

    /**
     * 这是banner数据
     */
    private void resolveBanner() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add("test");
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list);
        scrollImageBanner.setAdapter(viewPagerAdapter);
        /**
         * viewpager和父布局都设置了 android:clipChildren="false"
         * 这里设置了类似画廊效果
         * */
        scrollImageBanner.setPageMargin(ScreenUtils.dip2px(this, 20));
        scrollImageBanner.setSwipeRefreshLayout(scrollSwipeRefreshLayout);
        scrollImageBanner.setOffscreenPageLimit(4);
        scrollImageBanner.setCurrentItem(1);
    }


    private void resolveViewPager() {
        /**设置了tab的样式*/
        scrollPagerSlidingTabStrip.setShouldExpand(true);
        scrollPagerSlidingTabStrip.setDividerColor(Color.WHITE);
        scrollPagerSlidingTabStrip.setIndicatorColor(Color.TRANSPARENT);
        scrollPagerSlidingTabStrip.setUnderlineColor(Color.TRANSPARENT);
        scrollPagerSlidingTabStrip.setTabTextSelectColor(getResources().getColor(R.color.style_color_main));

        fragment1 = new ListFragment();
        fragment2 = new ListFragment();
        fragment1.setDevider(devider);
        fragment2.setDevider(devider);
        fragment1.setSwipeRefreshLayout(scrollSwipeRefreshLayout);
        fragment2.setSwipeRefreshLayout(scrollSwipeRefreshLayout);

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        tabList.add("TAB1");
        tabList.add("TAB2");

        scrollViewpager.setAdapter(fragmentPagerAdapter);
        scrollViewpager.setOffscreenPageLimit(2);
        scrollViewpager.setSwipeRefreshLayout(scrollSwipeRefreshLayout);

        listStripFragmentPagerAdapter = new ListFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, tabList);
        initFragmentPager();

    }

    public void initFragmentPager() {
        scrollViewpager.setAdapter(listStripFragmentPagerAdapter);
        scrollPagerSlidingTabStrip.setViewPager(scrollViewpager);
        scrollPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                /**不同的recyclerView的列表对应的位置不同，需要处理对应的顶部banner是否隐藏*/
                curTab = i;
                scrollAppbar.removeOnOffsetChangedListener(MainActivity.this);
                int scrollHeight = (i == 0) ? scrollOffsetSquare : scrollOffsetAttention;
                if (scrollHeight == -scrollAppbar.getTotalScrollRange()) {
                    CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
                            scrollAppbar.getLayoutParams()).getBehavior();
                    behavior.onNestedPreScroll(scrollCoordinatorLayout, scrollAppbar,
                            fragmentList.get(i).getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});
                    onOffsetChanged(scrollAppbar, scrollAppbar.getTotalScrollRange());
                }
                scrollSwipeRefreshLayout.setEnabled(false);
                scrollAppbar.addOnOffsetChangedListener(MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        scrollPagerSlidingTabStrip.setDoubleClickListener(new DoubleClickListener() {
            @Override
            public void onClick(int position) {
                curTab = position;
                resolveToTop();
            }
        });

        scrollViewpager.setCurrentItem(0);
    }

    /**
     * 根据appber的返回，判断此时的tab和banner是位置和显示效果
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i != scrollOffset) {
            if (curTab == 0) {
                scrollOffsetSquare = i;
            } else {
                scrollOffsetAttention = i;
            }
            scrollOffset = i;
            scrollSwipeRefreshLayout.setEnabled(i == 0);
            scrollViewpager.setIsTop(i == 0);
            float alpha = 1 - (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange() * 1.0f;
            scrollImageBannerLayout.setAlpha(alpha);
            resolveStripTabStyle(alpha, (int) ((1 - alpha) * tabScroll));
        }
    }


    private void setListener() {

        scrollAppbar.addOnOffsetChangedListener(this);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragmentList.get(arg0);
            }
        };

        scrollSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (curTab == 0) {
                    fragment1.refresh();
                } else {
                    fragment2.refresh();
                }
            }
        });
    }

    /**
     * 双击回到顶部
     */
    public void resolveToTop() {
        if (curTab == 0) {
            refreshToTop1();
        } else {
            refreshToTop2();
        }
    }

    private void refreshToTop2() {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
                scrollAppbar.getLayoutParams()).getBehavior();
        behavior.onNestedPreScroll(scrollCoordinatorLayout, scrollAppbar,
                fragmentList.get(1).getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});
        onOffsetChanged(scrollAppbar, scrollAppbar.getTotalScrollRange());
        fragmentList.get(1).changeToTop();
    }

    private void refreshToTop1() {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)
                scrollAppbar.getLayoutParams()).getBehavior();
        behavior.onNestedPreScroll(scrollCoordinatorLayout, scrollAppbar,
                fragmentList.get(0).getScrollableView(), 0, scrollAppbar.getTotalScrollRange(), new int[]{0, 0});
        onOffsetChanged(scrollAppbar, scrollAppbar.getTotalScrollRange());
        fragmentList.get(0).changeToTop();
    }


    private void resolveStripTabStyle(float alpha, int margin) {
        scrollPagerSlidingTabStrip.setDividerColor(Color.argb((int) (alpha * 255), 217, 217, 217));
        scrollPagerSlidingTabStrip.setIndicatorColor(Color.argb((int) ((1 - alpha) * 255), 251, 151, 63));
        LinearLayout linearLayout = scrollPagerSlidingTabStrip.getTabsContainer();
        linearLayout.setPadding(margin, linearLayout.getPaddingTop(), margin, linearLayout.getPaddingBottom());
        linearLayout.requestLayout();
    }


    private void setViewHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (null == layoutParams)
            return;
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

}
