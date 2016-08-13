package com.hx.template.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.components.CircleImageView;
import com.hx.template.global.FastClickUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends Fragment {

    @Bind(R.id.bg)
    ImageView bg;
    @Bind(R.id.avatar)
    CircleImageView avatar;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.settings)
    TextView settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.avatar, R.id.settings})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.avatar:

                break;
            case R.id.settings:
                startActivity(new Intent(getContext(), SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}
