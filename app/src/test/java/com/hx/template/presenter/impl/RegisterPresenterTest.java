package com.hx.template.presenter.impl;

import com.hx.template.CustomAnswer;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.RegisterMvpView;

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
import static org.mockito.Mockito.when;

/**
 * Created by huangxiang on 16/8/20.
 */
public class RegisterPresenterTest {

    @Rule
    public MockitoRule daggerRule = MockitoJUnit.rule();
    @Mock
    RegisterMvpView registerMvpView;
    @Mock
    UserModel userModel;
    @InjectMocks
    RegisterPresenter presenter;

    @Test
    public void testOnDestroyed() throws Exception {

    }

    @Test
    public void testRegister() throws Exception {
        presenter.attachView(registerMvpView);
        when(registerMvpView.getUserName()).thenReturn("huangxiang1");
        when(registerMvpView.getPassword()).thenReturn("123456");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        doAnswer(new CustomAnswer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
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
                return super.answer(invocation);
            }
        }).when(userModel).register(anyString(), anyString(), any(Callback.class));
        presenter.register();
    }
}