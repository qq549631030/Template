package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.ResetPwdByEmailContract;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.mvp.presenter.ResetPwdByEmailPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 邮箱重置密码
 */
public class ResetPwdByEmailFragment extends BaseStepFragment<ResetPwdByEmailPresenter, ResetPwdByEmailContract.View> implements ResetPwdByEmailContract.View {

    @Bind(R.id.email)
    EditText email;

    public ResetPwdByEmailFragment() {
    }

    @Override
    protected String getFragmentTitle() {
        return super.getFragmentTitle();
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public Loader<ResetPwdByEmailPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new ResetPwdByEmailPresenter(ModelManager.newUserModel());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pwd_by_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.confirm, R.id.reset_by_phone})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.confirm:
                presenter.resetPasswordByEmail();
                break;
            case R.id.reset_by_phone:
                setNextTarget(ResetPwdByPhoneFragment.class);
                nextStepAction(new Bundle());
                break;
        }
    }

    /**
     * 获取邮箱
     *
     * @return
     */
    @Override
    public String getEmail() {
        return email.getText().toString().trim();
    }

    /**
     * 邮件发送成功
     */
    @Override
    public void sendSuccess() {
        ToastUtils.showToast(getContext(), "请求验证邮件成功，请到" + getEmail() + "邮箱中进行激活。");
        finish();
    }

    /**
     * 邮件发送失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void sendFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
