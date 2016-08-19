package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.ModelManager;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.BindEmailMvpView;
import com.hx.template.presenter.Presenter;
import com.hx.template.presenter.PresenterFactory;
import com.hx.template.presenter.PresenterLoader;
import com.hx.template.presenter.impl.BindEmailPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindEmailFragment extends BaseStepFragment<BindEmailPresenter, BindEmailMvpView> implements BindEmailMvpView {


    @Bind(R.id.email)
    EditText email;

    public BindEmailFragment() {
    }

    @Override
    protected String getFragmentTitle() {
        return "绑定邮箱";
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new BindEmailPresenter(ModelManager.newUserModel());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.bind)
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.bind:
                if (checkInput()) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在发送验证邮件");
                    }
                    presenter.resetEmail();
                }
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
        return email.getText().toString().trim();
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
