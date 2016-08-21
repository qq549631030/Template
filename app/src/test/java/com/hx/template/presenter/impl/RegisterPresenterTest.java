package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.RegisterMvpView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by huangxiang on 16/8/20.
 */
public class RegisterPresenterTest {
    @Mock
    RegisterMvpView registerMvpView;
    @Mock
    UserModel userModel;

    RegisterPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new RegisterPresenter(userModel);
    }

    @Test
    public void testOnDestroyed() throws Exception {

    }

    @Test
    public void testRegister() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang1");
        when(registerMvpView.getPassword()).thenReturn("123456");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                String username = (String) arguments[0];
                String password = (String) arguments[1];
                Callback callback = (Callback) arguments[2];
                if (!"huangxiang".equals(username)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    callback.onSuccess(TaskManager.TASK_ID_LOGIN, user);
                } else {
                    callback.onFailure(TaskManager.TASK_ID_LOGIN, "10002", "用户名已存在");
                }
                return null;
            }
        }).when(userModel).register(anyString(), anyString(), any(Callback.class));
        presenter.register();

    }
}