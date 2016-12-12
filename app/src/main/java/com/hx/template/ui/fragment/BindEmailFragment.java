package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.mvp.contract.BindEmailContract;
import com.hx.template.mvp.presenter.BindEmailPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;



import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindEmailFragment extends BaseStepFragment<BindEmailPresenter, BindEmailContract.View> implements BindEmailContract.View {



    EditText email;

    public BindEmailFragment() {
    }

    @Override
    protected String getFragmentTitle() {
        return "绑定邮箱";
    }

    @Override
    protected BindEmailPresenter onCreatePresenter() {
        return ComponentHolder.getAppComponent().bindEmailPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_email, container, false);
        email = (EditText) view.findViewById(R.id.email);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.bind)
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.bind:
                User currentUser = User.getCurrentUser(User.class);
                if (currentUser != null) {
                    presenter.resetEmail(currentUser.getObjectId());
                }
                break;
        }
    }

    /**
     * 获取请求的Email
     *
     * @return
     */
    @Override
    public String getEmail() {
        return email.getText().toString().trim();
    }

    /**
     * 请求成功
     */
    @Override
    public void onRequestSuccess() {
        ToastUtils.showToast(getContext(), "邮件发送成功，请前往验证");
        EventBus.getDefault().post(new UserInfoUpdateEvent());
        finish();
    }

    /**
     * 请求失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void onRequestFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
