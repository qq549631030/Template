package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.global.FastClickUtils;
import com.hx.template.http.bmob.BmobSMSTemplate;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.ResetPwdByPhoneContract;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.mvp.presenter.ResetPwdByPhonePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机号码重置密码
 */
public class ResetPwdByPhoneFragment extends BaseStepFragment<ResetPwdByPhonePresenter, ResetPwdByPhoneContract.View> implements ResetPwdByPhoneContract.View {

    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.vcode)
    EditText vcode;
    @Bind(R.id.getvcode)
    TextView getvcode;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;

    private CountDownTimer countDownTimer;

    public ResetPwdByPhoneFragment() {
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    protected String getFragmentTitle() {
        return super.getFragmentTitle();
    }

    @Override
    public Loader<ResetPwdByPhonePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new ResetPwdByPhonePresenter(ModelManager.newSMSModel(), ModelManager.newUserModel());
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getvcode != null) {
                    getvcode.setText((millisUntilFinished / 1000) + "秒");
                }
            }

            @Override
            public void onFinish() {
                if (getvcode != null) {
                    getvcode.setText("重新发送");
                    getvcode.setEnabled(true);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pwd_by_phone, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.getvcode, R.id.confirm, R.id.reset_by_email})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.getvcode:
                presenter.requestSMSCode();
                break;
            case R.id.confirm:
                presenter.resetPasswordBySMSCode();
                break;
            case R.id.reset_by_email:
                setNextTarget(ResetPwdByEmailFragment.class);
                nextStepAction(new Bundle());
                break;
        }
    }


    /**
     * 获取验证码
     *
     * @return
     */
    @Override
    public String getSMSCode() {
        return vcode.getText().toString().trim();
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    /**
     * 获取再次确认密码
     *
     * @return
     */
    @Override
    public String getConfirmPassword() {
        return confirmPassword.getText().toString().trim();
    }

    /**
     * 重置成功
     */
    @Override
    public void resetSuccess() {
        ToastUtils.showToast(getContext(), "重置成功");
        finish();
    }

    /**
     * 重置失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void resetFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    @Override
    public String getRequestPhoneNumber() {
        return phone.getText().toString().trim();
    }

    /**
     * 获取短信验证模板
     *
     * @return
     */
    @Override
    public String getSMSTemplate() {
        return BmobSMSTemplate.TEMPLATE_RESET_PWD;
    }

    /**
     * 获取验证码成功
     *
     * @param data 返回信息
     */
    @Override
    public void onRequestSuccess(Object... data) {
        ToastUtils.showToast(getContext(), "验证码获取成功");
        getvcode.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 获取验证码失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void onRequestFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
