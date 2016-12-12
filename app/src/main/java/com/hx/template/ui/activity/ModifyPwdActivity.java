package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.presenter.PresenterFactory;
import com.hx.mvp.presenter.PresenterLoader;
import com.hx.template.R;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.mvp.contract.ModifyPwdContract;
import com.hx.template.mvp.presenter.ModifyPwdPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;


public class ModifyPwdActivity extends BaseMvpActivity<ModifyPwdPresenter, ModifyPwdContract.View> implements ModifyPwdContract.View, View.OnClickListener {


    Toolbar toolbar;

    EditText oldPwd;

    EditText newPwd;

    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        oldPwd = (EditText) findViewById(R.id.old_pwd);
        newPwd = (EditText) findViewById(R.id.new_pwd);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        findViewById(R.id.confirm).setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.modify_pwd_title));
    }

    @Override
    public Loader<ModifyPwdPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory() {
            @Override
            public Presenter create() {
                return ComponentHolder.getAppComponent().modifyPwdPresenter();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (checkInput()) {
                    presenter.modifyPwd();
                }
                break;
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
