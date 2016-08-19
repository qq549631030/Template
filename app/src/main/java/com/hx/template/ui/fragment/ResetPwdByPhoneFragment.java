package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.global.FastClickUtils;
import com.hx.template.http.bmob.BmobSMSTemplate;
import com.hx.template.model.impl.bmob.BmobSMSModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.ResetPwdByPhoneMvpView;
import com.hx.template.presenter.Presenter;
import com.hx.template.presenter.PresenterFactory;
import com.hx.template.presenter.PresenterLoader;
import com.hx.template.presenter.impl.ResetPwdByPhonePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手机号码重置密码
 */
public class ResetPwdByPhoneFragment extends BaseStepFragment<ResetPwdByPhonePresenter, ResetPwdByPhoneMvpView> implements ResetPwdByPhoneMvpView {

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
                return new ResetPwdByPhonePresenter(new BmobSMSModel(), new BmobUserImpl());
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
                if (checkPhone()) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在获取短信验证码...");
                    }
                    presenter.requestSMSCode();
                }
                break;
            case R.id.confirm:
                if (checkInput()) {
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在重置...");
                    }
                    presenter.resetPasswordBySMSCode();
                }
                break;
            case R.id.reset_by_email:
                setNextTarget(ResetPwdByEmailFragment.class);
                nextStepAction(new Bundle());
                break;
        }
    }

    private boolean checkPhone() {
        if (TextUtils.isEmpty(getRequestPhoneNumber())) {
            ToastUtils.showToast(getContext(), "手机号码不能为空");
            return false;
        }
        if (!(Pattern.matches(Constant.phoneFormat, getRequestPhoneNumber()))) {
            ToastUtils.showToast(getContext(), "手机号码有误");
            return false;
        }
        return true;
    }

    private boolean checkInput() {
        if (!checkPhone()) {
            return false;
        }
        if (TextUtils.isEmpty(getSMSCode())) {
            ToastUtils.showToast(getContext(), "验证码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(getPassword())) {
            ToastUtils.showToast(getContext(), "密码不能为空");
            return false;
        }
        if (getPassword().equals(confirmPassword.getText().toString().trim())) {
            ToastUtils.showToast(getContext(), "两次输入的密码不一致");
            return false;
        }
        return true;
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
     * 重置成功
     */
    @Override
    public void resetSuccess() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
