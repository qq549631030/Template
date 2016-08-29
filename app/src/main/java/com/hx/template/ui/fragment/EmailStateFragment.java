package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.EmailStateContract;
import com.hx.template.mvp.presenter.EmailStatePresenter;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailStateFragment extends BaseStepFragment<EmailStatePresenter, EmailStateContract.View> implements EmailStateContract.View {


    @Bind(R.id.email_state)
    TextView emailState;
    @Bind(R.id.resend)
    Button resend;
    @Bind(R.id.rebind)
    Button rebind;

    public EmailStateFragment() {
    }

    @Override
    protected String getFragmentTitle() {
        return "绑定邮箱";
    }

    @Override
    public Loader<EmailStatePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new EmailStatePresenter(ModelManager.provideUserModel());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_state, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void refreshViews() {
        User user = User.getCurrentUser(User.class);
        if (user != null) {
            String email = user.getEmail();
            Boolean emailVerified = user.getEmailVerified();
            boolean verified = emailVerified == null ? false : emailVerified.booleanValue();
            if (!verified) {
                resend.setVisibility(View.VISIBLE);
                rebind.setVisibility(View.VISIBLE);
                emailState.setText("您已设置邮箱:" + email + "，还未验证，请登录邮箱验证");
            } else {
                resend.setVisibility(View.GONE);
                rebind.setVisibility(View.VISIBLE);
                emailState.setText("您已绑定邮箱：" + email);
            }
        }
    }

    @OnClick({R.id.resend, R.id.rebind})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.resend:
                presenter.requestEmailVerify();
                break;
            case R.id.rebind:
                setNextTarget(BindEmailFragment.class);
                nextStepAction(new Bundle());
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
        User user = User.getCurrentUser(User.class);
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    /**
     * 请求成功
     */
    @Override
    public void onRequestSuccess() {
        ToastUtils.showToast(getContext(), "邮件发送成功，请前往验证");
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
