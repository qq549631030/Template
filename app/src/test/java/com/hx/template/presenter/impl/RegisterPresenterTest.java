package com.hx.template.presenter.impl;

import com.hx.template.CustomAnswer;
import com.hx.template.CustomRule;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.RegisterContract;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.mvp.BasePresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by huangxiang on 16/8/20.
 */
public class RegisterPresenterTest {
    @Rule
    public CustomRule customRule = new CustomRule();
    @Rule
    public MockitoRule daggerRule = MockitoJUnit.rule();
    @Mock
    RegisterContract.View registerMvpView;
    @Mock
    UserModel userModel;
    @InjectMocks
    RegisterPresenter presenter;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Before
    public void setUp() throws Exception {
        doAnswer(new CustomAnswer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object result = super.answer(invocation);
                Object[] arguments = invocation.getArguments();
                String username = (String) arguments[0];
                String password = (String) arguments[1];
                Callback callback = (Callback) arguments[2];
                if (!"huangxiang".equals(username)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    callback.onSuccess(TaskManager.TASK_ID_REGISTER, user);
                } else {
                    callback.onFailure(TaskManager.TASK_ID_REGISTER, "10002", "用户名已存在");
                }
                countDownLatch.countDown();
                return result;
            }
        }).when(userModel).register(anyString(), anyString(), any(Callback.class));
        doAnswer(new CustomAnswer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object result = super.answer(invocation);
                countDownLatch.countDown();
                return result;
            }
        }).when(registerMvpView).showError(anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserModelNull() throws Exception {
        presenter = new RegisterPresenter(null);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void testViewNotAttach() throws Exception {
        presenter.register();
    }


    @Test
    public void testOnDestroyed() throws Exception {
        presenter.onDestroyed();
    }

    @Test
    public void testRegister_Normal() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang1");
        when(registerMvpView.getPassword()).thenReturn("123456");
        when(registerMvpView.getConfirmPassword()).thenReturn("123456");
        countDownLatch = new CountDownLatch(1);
        presenter.register();
        countDownLatch.await();
        verify(registerMvpView).showLoadingProgress(anyString());
        verify(userModel).register(anyString(), anyString(), any(Callback.class));
        verify(registerMvpView).hideLoadingProgress();
        verify(registerMvpView).registerSuccess();
    }

    @Test
    public void testRegister_username_already_exist() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang");
        when(registerMvpView.getPassword()).thenReturn("123456");
        when(registerMvpView.getConfirmPassword()).thenReturn("123456");
        countDownLatch = new CountDownLatch(1);
        presenter.register();
        countDownLatch.await();
        verify(registerMvpView).showLoadingProgress(anyString());
        verify(userModel).register(anyString(), anyString(), any(Callback.class));
        verify(registerMvpView).hideLoadingProgress();
        verify(registerMvpView).registerFail("10002", "用户名已存在");
    }

    @Test
    public void testRegister_username_empty() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("");
        when(registerMvpView.getPassword()).thenReturn("123456");
        when(registerMvpView.getConfirmPassword()).thenReturn("123456");
        countDownLatch = new CountDownLatch(1);
        presenter.register();
        countDownLatch.await();
        verify(registerMvpView).showError(anyString());
        verify(registerMvpView, never()).showLoadingProgress(anyString());
        verify(userModel, never()).register(anyString(), anyString(), any(Callback.class));
    }

    @Test
    public void testRegister_password_empty() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang1");
        when(registerMvpView.getPassword()).thenReturn("");
        when(registerMvpView.getConfirmPassword()).thenReturn("123456");
        countDownLatch = new CountDownLatch(1);
        presenter.register();
        countDownLatch.await();
        verify(registerMvpView).showError(anyString());
        verify(registerMvpView, never()).showLoadingProgress(anyString());
        verify(userModel, never()).register(anyString(), anyString(), any(Callback.class));
    }

    @Test
    public void testRegister_password_not_same() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang1");
        when(registerMvpView.getPassword()).thenReturn("123456");
        when(registerMvpView.getConfirmPassword()).thenReturn("1234567");
        countDownLatch = new CountDownLatch(1);
        presenter.register();
        countDownLatch.await();
        verify(registerMvpView).showError(anyString());
        verify(registerMvpView, never()).showLoadingProgress(anyString());
        verify(userModel, never()).register(anyString(), anyString(), any(Callback.class));
    }
}