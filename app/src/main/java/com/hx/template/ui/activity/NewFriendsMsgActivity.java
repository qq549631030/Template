package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.hx.easemob.db.InviteMessgeDao;
import com.hx.easemob.domain.InviteMessage;
import com.hx.template.R;
import com.hx.template.adapter.NewFriendsMsgAdapter;
import com.hx.template.base.BaseActivity;

import java.util.List;

public class NewFriendsMsgActivity extends BaseActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        list.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }
}
