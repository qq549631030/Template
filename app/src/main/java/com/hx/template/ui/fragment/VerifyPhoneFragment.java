package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.http.bmob.BmobSMSTemplate;
import com.hx.template.model.ModelManager;
import com.hx.template.model.impl.bmob.BmobSMSModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.VerifyPhoneMvpView;
import com.hx.template.presenter.Presenter;
import com.hx.template.presenter.PresenterFactory;
import com.hx.template.presenter.PresenterLoader;
import com.hx.template.presenter.impl.BindPhonePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyPhoneFragment extends BaseStepFragment<BindPhonePresenter, VerifyPhoneMvpView> implements VerifyPhoneMvpView {

    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.vcode)
    EditText vcode;
    @Bind(R.id.getvcode)
    TextView getvcode;

    private CountDownTimer countDownTimer;

    public VerifyPhoneFragment() {
    }

    @Override
    protected String getFragmentTitle() {
        return "重新绑定手机";
    }

    @Override
    public Class<? extends BaseStepFragment> getNextTarget() {
        return BindPhoneFragment.class;
    }

    @Override
    public Loader<BindPhonePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new PresenterFactory() {
            @Override
            public Presenter create() {
                return new BindPhonePresenter(ModelManager.newSMSModel(), ModelManager.newUserModel());
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
        View view = inflater.inflate(R.layout.fragment_verify_phone, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone.setEnabled(false);
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            String mobile = currentUser.getMobilePhoneNumber();
            phone.setText(StringUtils.nullStrToEmpty(mobile));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.getvcode, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getvcode:
                if (checkPhone()) {
                    if (!FastClickUtils.isTimeToProcess(view.getId())) {
                        return;
                    }
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在获取短信验证码...");
                    }
                    presenter.requestSMSCode();
                }
                break;
            case R.id.confirm:
                if (checkInput()) {
                    if (!FastClickUtils.isTimeToProcess(view.getId())) {
                        return;
                    }
                    if (getActivity() instanceof BaseActivity) {
                        ((BaseActivity) getActivity()).showLoadingProgress("正在验证短信验证码...");
                    }
                    presenter.verifySmsCode();
                }
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
        return true;
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
        return BmobSMSTemplate.TEMPLATE_VER_PHONE;
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
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
        nextStepAction(new Bundle());
    }

    /**
     * 验证失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void onVerifyFail(String errorCode, String errorMsg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
        ToastUtils.showToast(getContext(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
