package com.hx.template.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hx.template.CustomSDKHelper;
import com.hx.template.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.Hashtable;
import java.util.Map;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/22 14:17
 * 邮箱：huangx@pycredit.cn
 */

public class ContactListFragment extends EaseContactListFragment {


    protected String getFragmentTitle() {
        return "联系人";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(getFragmentTitle());
    }

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_add:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refresh() {
        Map<String, EaseUser> m = CustomSDKHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (!hidden) {
            getActivity().setTitle(getFragmentTitle());
        }
        super.onHiddenChanged(hidden);
    }


}