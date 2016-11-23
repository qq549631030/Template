package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.template.R;

/**
 * 功能说明：
 * 作者：huangx on 2016/11/23 20:47
 * 邮箱：huangx@pycredit.cn
 */

public class BaseRecyclerViewFragment extends BaseStateFragment {

    protected RecyclerView recyclerView;

    @Override
    protected int getContentRes() {
        return R.layout.layout_base_recyclerview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(view);
        initView(view);
        if (recyclerView == null) {
            throw new IllegalArgumentException("layout must have a RecyclerView with id recyclerView");
        }
        return view;
    }

    /**
     * 初始化View
     *
     * @param parent
     */
    protected void initView(View parent) {

    }

    /**
     * 初始化 RecyclerView
     *
     * @param parent
     */
    protected void initRecyclerView(View parent) {
        recyclerView = (RecyclerView) parent.findViewById(R.id.recyclerView);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
