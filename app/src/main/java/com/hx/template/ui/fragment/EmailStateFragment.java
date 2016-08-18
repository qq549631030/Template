package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.base.BaseFragment;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.UserModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.BindEmailMvpView;
import com.hx.template.presenter.impl.BindEmailPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailStateFragment extends BaseStepFragment implements BindEmailMvpView {


    @Bind(R.id.email_state)
    TextView emailState;
    @Bind(R.id.resend)
    Button resend;
    @Bind(R.id.rebind)
    Button rebind;

    UserModel userModel;
    BindEmailPresenter presenter;

    public EmailStateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new BmobUserImpl();
        presenter = new BindEmailPresenter(userModel);
    }

    @Override
    protected String getFragmentTitle() {
        return "绑定邮箱";
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
        presenter.attachView(this);
        refreshViews();
    }

    private void refreshViews() {
        User user = User.getCurrentUser(User.class);
        if (user != null) {
            String email = user.getEmail();
            boolean emailVerified = user.getEmailVerified();
            if (!emailVerified) {
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

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.resend, R.id.rebind})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.resend:
                if (checkInput()) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在发送验证邮件");
                    }
                    presenter.requestEmailVerify();
                }
                break;
            case R.id.rebind:
                setNextTarget(BindEmailFragment.class);
                nextStepAction(new Bundle());
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(getEmail())) {
            ToastUtils.showToast(getContext(), "邮箱地址不能为空");
            return false;
        }
        if (!Pattern.compile(Constant.emailFormat).matcher(getEmail()).matches()) {
            ToastUtils.showToast(getContext(), "请输入正确的邮箱地址");
            return false;
        }
        return true;
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
