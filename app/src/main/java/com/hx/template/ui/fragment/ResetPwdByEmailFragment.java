package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.global.FastClickUtils;
import com.hx.template.mvp.contract.ResetPwdByEmailContract;
import com.hx.template.mvp.presenter.ResetPwdByEmailPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;



import butterknife.OnClick;

/**
 * 邮箱重置密码
 */
public class ResetPwdByEmailFragment extends BaseStepFragment<ResetPwdByEmailPresenter, ResetPwdByEmailContract.View> implements ResetPwdByEmailContract.View {


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
    protected ResetPwdByEmailPresenter onCreatePresenter() {
        return ComponentHolder.getAppComponent().resetPwdByEmailPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pwd_by_email, container, false);
        email = (EditText) view.findViewById(R.id.email);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
