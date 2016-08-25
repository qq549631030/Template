package com.hx.template.ui.activity;

import com.hx.template.BuildConfig;
import com.hx.template.CustomRule;
import com.hx.template.R;
import com.hx.template.TestApplication;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

/**
 * 功能说明：com.hx.template.ui.activity
 * 作者：huangx on 2016/8/25 15:38
 * 邮箱：huangx@pycredit.cn
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplication.class)
public class LoginActivityTest {

    @Rule
    public CustomRule customRule = new CustomRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UserModel userModel;

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

    @Test
    public void testOnCreateLoader() throws Exception {
        PowerMockito.whenNew(LoginPresenter.class).withAnyArguments().thenReturn(new LoginPresenter(userModel));
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        loginActivity.findViewById(R.id.login).performClick();
    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

    }

    @Test
    public void testOnClick() throws Exception {

    }
}