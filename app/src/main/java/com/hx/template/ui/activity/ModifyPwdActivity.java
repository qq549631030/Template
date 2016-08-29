package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.ModifyPwdContract;
import com.hx.template.mvp.presenter.ModifyPwdPresenter;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPwdActivity extends BaseActivity<ModifyPwdPresenter, ModifyPwdContract.View> implements ModifyPwdContract.View {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.old_pwd)
    EditText oldPwd;
    @Bind(R.id.new_pwd)
    EditText newPwd;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("修改密码");
    }

    @Override
    public Loader<ModifyPwdPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory() {
            @Override
            public Presenter create() {
                return new ModifyPwdPresenter(ModelManager.provideUserModel());
            }
        });
    }

    @OnClick(R.id.confirm)
    public void onClick() {
        if (checkInput()) {
            presenter.modifyPwd();
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(getOldPwd())) {
            ToastUtils.showToast(this, "旧密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(getNewPwd())) {
            ToastUtils.showToast(this, "新密码不能为空");
            return false;
        }
        if (!getNewPwd().equals(confirmPassword.getText().toString().trim())) {
            ToastUtils.showToast(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }

    @Override
    public String getOldPwd() {
        return oldPwd.getText().toString().trim();
    }

    @Override
    public String getNewPwd() {
        return newPwd.getText().toString().trim();
    }

    /**
     * 获取新密码
     *
     * @return
     */
    @Override
    public String getConfirmPwd() {
        return confirmPassword.getText().toString().trim();
    }

    /**
     * 修改成功
     */
    @Override
    public void modifySuccess() {
        ToastUtils.showToast(this, "修改成功");
        finish();
    }

    /**
     * 修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void modifyFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }
}
