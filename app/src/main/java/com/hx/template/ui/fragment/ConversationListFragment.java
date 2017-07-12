package com.hx.template.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hx.easemob.Constant;
import com.hx.template.R;
import com.hx.template.event.UnReadMsgChangeEvent;
import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.ui.activity.ChatActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.huangx.common.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/21 19:48
 * 邮箱：549631030@qq.com
 */

public class ConversationListFragment extends EaseConversationListFragment {

    public final static int REQUEST_CODE_SCAN = 1001;

    protected String getFragmentTitle() {
        return "会话";
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_scan:
                startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUEST_CODE_SCAN);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String result = bundle.getString(CaptureActivity.EXTRA_RESULT);
                ToastUtils.show(getContext(), result);
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UnReadMsgChangeEvent event) {
        refresh();
    }
}
