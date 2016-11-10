package com.example.linkagescroll.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linkagescroll.R;
import com.example.linkagescroll.adapter.RecyclerBaseAdapter;
import com.example.linkagescroll.itemDecoration.AlphaDividerItemDecoration;
import com.example.linkagescroll.model.RecyclerDataModel;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shuyu on 2016/11/10.
 */

public class ListFragment extends Fragment {


    @BindView(R.id.list_item_recycler)
    XRecyclerView listItemRecycler;

    LinearLayoutManager linearLayoutManager;


    protected int devider;
    protected boolean isRefreshing;

    protected RecyclerBaseAdapter recyclerBaseAdapter;
    protected List<RecyclerDataModel> dataList = new ArrayList<>();
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public int getDevider() {
        return devider;
    }

    public void setDevider(int devider) {
        this.devider = devider;
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listItemRecycler.setLayoutManager(linearLayoutManager);
        listItemRecycler.addItemDecoration(new AlphaDividerItemDecoration(getDevider(), AlphaDividerItemDecoration.LIST));
        listItemRecycler.setPullRefreshEnabled(false);
        listItemRecycler.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);

        resolveData();

        recyclerBaseAdapter = new RecyclerBaseAdapter(getActivity(), dataList);
        listItemRecycler.setAdapter(recyclerBaseAdapter);
        listItemRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //使用的是外部全局刷新
            }

            @Override
            public void onLoadMore() {
                if (isRefreshing())
                    return;
                setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshing(false);
                        resolveData();
                        listItemRecycler.loadMoreComplete();
                    }
                }, 2000);
            }
        });


    }

    private void resolveData() {
        for (int i = 0; i < 19; i++) {
            RecyclerDataModel recyclerDataModel = new RecyclerDataModel();
            dataList.add(recyclerDataModel);
        }
        if (recyclerBaseAdapter != null)
            recyclerBaseAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新
     */
    public void refresh() {
        if (isRefreshing())
            return;
        setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(false);
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
                dataList.clear();
                resolveData();
            }
        }, 2000);
    }

    /**
     * 回到顶部
     */
    public void changeToTop() {
        listItemRecycler.stopNestedScroll();
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.scrollToPosition(0);
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public RecyclerView getScrollableView() {
        return listItemRecycler;
    }

}
