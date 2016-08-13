package com.hx.template.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.model.UserModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.ModifyPwdMvpView;
import com.hx.template.presenter.impl.ModifyPwdPresenter;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPwdActivity extends BaseActivity implements ModifyPwdMvpView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.old_pwd)
    EditText oldPwd;
    @Bind(R.id.new_pwd)
    EditText newPwd;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;

    ProgressDialog mProgressDialog;

    ModifyPwdPresenter presenter;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("修改密码");
        mProgressDialog = new ProgressDialog(this);
        userModel = new BmobUserImpl();
        presenter = new ModifyPwdPresenter(userModel);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.confirm)
    public void onClick() {
        if (checkInput()) {

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
        if (getNewPwd().equals(confirmPassword.getText().toString().trim())) {
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
     * 退出
     */
    @Override
    public void exit() {
        ToastUtils.showToast(this, "修改成功");
        finish();
    }

    @Override
    public void showLoadingProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        mProgressDialog.dismiss();
    }

    /**
     * 显示错误信息
     *
     * @param errorMsg
     */
    @Override
    public void showFailedError(String errorMsg) {
        ToastUtils.showToast(getApplicationContext(), "修改密码失败：" + errorMsg);

    }
}
