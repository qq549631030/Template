package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/6 9:19
 * 邮箱：huangx@pycredit.cn
 */
public class BaseListFragment extends BaseStateFragment {

    protected ListView listView;

    @Override
    protected int getContentRes() {
        return R.layout.layout_base_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initListView(view);
        initView(view);
        if (listView == null) {
            throw new IllegalArgumentException("layout must have a ListView with id listView");
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
     * 初始化ListView
     *
     * @param parent
     */
    protected void initListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
    }


    public ListView getListView() {
        return listView;
    }

}
