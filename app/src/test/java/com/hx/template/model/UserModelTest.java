package com.hx.template.model;

import com.hx.template.CustomAnswer;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by huangxiang on 16/8/21.
 */
public class UserModelTest {

    @Rule
    public MockitoRule daggerRule = MockitoJUnit.rule();
    @Mock
    UserModel userModel;
    @Mock
    Callback callback;

    @Test
    public void testRegister() throws Exception {
        doAnswer(new CustomAnswer() {
            @Override
            public Object answer(InvocationOnMock invocation) {

                return super.answer(invocation);
            }
        }).when(userModel).register(anyString(), anyString(), any(Callback.class));
        userModel.register("huangxiang", "123456", callback);
    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testModifyPwd() throws Exception {

    }

    @Test
    public void testGetUserInfo() throws Exception {

    }

    @Test
    public void testUpdateUserInfo() throws Exception {

    }

    @Test
    public void testRequestEmailVerify() throws Exception {

    }

    @Test
    public void testResetPasswordBySMSCode() throws Exception {

    }

    @Test
    public void testResetPasswordByEmail() throws Exception {

    }

    @Test
    public void testLogout() throws Exception {

    }
}