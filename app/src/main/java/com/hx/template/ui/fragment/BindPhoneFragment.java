package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.http.bmob.BmobSMSTemplate;
import com.hx.template.mvp.contract.BindPhoneContract;
import com.hx.template.mvp.presenter.BindPhonePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class BindPhoneFragment extends BaseStepFragment<BindPhonePresenter, BindPhoneContract.View> implements BindPhoneContract.View {


    EditText phone;

    EditText vcode;

    TextView getvcode;

    private CountDownTimer countDownTimer;


    public BindPhoneFragment() {
    }


    @Override
    protected BindPhonePresenter onCreatePresenter() {
        return ComponentHolder.getAppComponent().bindPhonePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getvcode != null) {
                    getvcode.setText(String.format(getString(R.string.verification_code_get_wait), millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (getvcode != null) {
                    getvcode.setText(R.string.verification_code_get_time_out);
                    getvcode.setEnabled(true);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_phone, container, false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    protected String getFragmentTitle() {
        return getString(R.string.bind_phone_title);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getvcode:
                presenter.requestSMSCode();
                break;
            case R.id.bind:
                User currentUser = User.getCurrentUser(User.class);
                if (currentUser != null) {
                    presenter.bindPhone(currentUser.getObjectId());
                }
                break;
        }
    }

    /**
     * 绑定成功
     */
    @Override
    public void bindSuccess() {
        ToastUtils.showToast(getContext(), getString(R.string.bind_success));
        EventBus.getDefault().post(new UserInfoUpdateEvent());
        finish();
    }

    /**
     * 绑定失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void bindFail(String errorCode, String errorMsg) {
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
        return BmobSMSTemplate.TEMPLATE_BIND_PHONE;
    }


    /**
     * 获取验证码成功
     *
     * @param data 返回信息
     */
    @Override
    public void onRequestSuccess(Object... data) {
        ToastUtils.showToast(getContext(), getString(R.string.verification_code_get_success));
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

    /**
     * 获取手机号码
     *
     * @return
     */
    @Override
    public String getVerifyPhoneNumber() {
        return phone.getText().toString().trim();
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
     * 验证成功
     *
     * @param data 返回信息
     */
    @Override
    public void onVerifySuccess(Object... data) {
        //nop
    }

    /**
     * 验证失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void onVerifyFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
