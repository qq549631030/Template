package com.hx.template.presenter.impl;

import com.hx.template.CustomRule;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.mvp.contract.LoginContract;
import com.hx.template.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by huangxiang on 16/8/20.
 */
public class LoginPresenterTest {

    @Rule
    public CustomRule customRule = new CustomRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    LoginContract.View loginMvpView;
    @Mock
    UserModel userModel;
    @InjectMocks
    LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                String username = (String) arguments[0];
                String password = (String) arguments[1];
                Callback callback = (Callback) arguments[2];
                if ("huangxiang".equals(username) && "123456".equals(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    callback.onSuccess(TaskManager.TASK_ID_LOGIN, user);
                } else {
                    callback.onFailure(TaskManager.TASK_ID_LOGIN, "10001", "用户名或密码错误");
                }
                return null;
            }
        }).when(userModel).login(anyString(), anyString(), any(Callback.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserModelNull() throws Exception {
        presenter = new LoginPresenter(null);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void testViewNotAttach() throws Exception {
        presenter.login();
    }

    @Test
    public void testLogin_Normal() throws Exception {
        presenter.attachView(loginMvpView);
        when(loginMvpView.getUserName()).thenReturn("huangxiang");
        when(loginMvpView.getPassword()).thenReturn("123456");
        presenter.login();
        verify(loginMvpView).loginSuccess(any(User.class));
    }

    @Test
    public void testLogin_incorrect_username() throws Exception {
        presenter.attachView(loginMvpView);
        when(loginMvpView.getUserName()).thenReturn("huangxiang1");
        when(loginMvpView.getPassword()).thenReturn("123456");
        presenter.login();
        verify(loginMvpView).loginFail("10001", "用户名或密码错误");
    }

    @Test
    public void testLogin_incorrect_password() throws Exception {
        presenter.attachView(loginMvpView);
        when(loginMvpView.getUserName()).thenReturn("huangxiang");
        when(loginMvpView.getPassword()).thenReturn("1234567");
        presenter.login();
        verify(loginMvpView).loginFail("10001", "用户名或密码错误");
    }


    @Test
    public void testLogin_username_empty() throws Exception {
        presenter.attachView(loginMvpView);
        when(loginMvpView.getUserName()).thenReturn("");
        when(loginMvpView.getPassword()).thenReturn("123456");
        presenter.login();
        verify(loginMvpView, never()).showLoadingProgress(anyString());
        verify(userModel, never()).login(anyString(), anyString(), any(Callback.class));
    }

    @Test
    public void testLogin_password_empty() throws Exception {
        presenter.attachView(loginMvpView);
        when(loginMvpView.getUserName()).thenReturn("huangxiang");
        when(loginMvpView.getPassword()).thenReturn("");
        presenter.login();
        verify(loginMvpView, never()).showLoadingProgress(anyString());
        verify(userModel, never()).login(anyString(), anyString(), any(Callback.class));
    }
}