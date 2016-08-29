package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseStepFragment;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.http.bmob.BmobSMSTemplate;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.BindPhoneContract;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.mvp.presenter.BindPhonePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BindPhoneFragment extends BaseStepFragment<BindPhonePresenter, BindPhoneContract.View> implements BindPhoneContract.View {


    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.vcode)
    EditText vcode;
    @Bind(R.id.getvcode)
    TextView getvcode;

    private CountDownTimer countDownTimer;


    public BindPhoneFragment() {
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
        View view = inflater.inflate(R.layout.fragment_bind_phone, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<BindPhonePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new BindPhonePresenter(ModelManager.provideSMSModel(), ModelManager.provideUserModel());
            }
        });
    }

    @Override
    protected String getFragmentTitle() {
        return "绑定手机";
    }

    @OnClick({R.id.getvcode, R.id.bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getvcode:
                presenter.requestSMSCode();
                break;
            case R.id.bind:
                presenter.verifySmsCode();
                break;
        }
    }

    /**
     * 绑定成功
     */
    @Override
    public void bindSuccess() {
        ToastUtils.showToast(getContext(), "绑定成功");
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
