package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.PersonalInfoUpdateMvpView;
import com.hx.template.presenter.Presenter;
import com.hx.template.presenter.PresenterFactory;
import com.hx.template.presenter.PresenterLoader;
import com.hx.template.presenter.impl.PersonalInfoUpdatePresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalInfoUpdateActivity extends BaseActivity<PersonalInfoUpdatePresenter, PersonalInfoUpdateMvpView> implements PersonalInfoUpdateMvpView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText)
    EditText editText;

    int infoType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_update);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        infoType = getIntent().getIntExtra(User.INFO_TYPE, -1);
        refreshViews();
    }

    @Override
    public Loader<PersonalInfoUpdatePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory() {
            @Override
            public Presenter create() {
                return new PersonalInfoUpdatePresenter(new BmobUserImpl());
            }
        });
    }

    private void refreshViews() {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser == null) {
            return;
        }
        switch (infoType) {
            case User.INFO_TYPE_NICKNAME:
                setTitle("更改昵称");
                editText.setText(StringUtils.nullStrToEmpty(currentUser.getNickname()));
                editText.setSelection(editText.getText().length());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_personal_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                showLoadingProgress("正在修改...");
                presenter.updateInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getNewData() {
        return editText.getText().toString().trim();
    }

    @Override
    public int getDataType() {
        return infoType;
    }

    /**
     * 修改成功
     */
    @Override
    public void updateSuccess() {
        hideLoadingProgress();
        EventBus.getDefault().post(new UserInfoUpdateEvent());
        finish();
    }

    /**
     * 修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void updateFail(String errorCode, String errorMsg) {
        hideLoadingProgress();
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }
}
