package com.hx.template.ui.activity;

import android.widget.EditText;

import com.hx.template.BuildConfig;
import com.hx.template.CustomAnswer;
import com.hx.template.R;
import com.hx.template.TestApplication;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * 功能说明：com.hx.template.ui.activity
 * 作者：huangx on 2016/8/25 15:38
 * 邮箱：huangx@pycredit.cn
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApplication.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class LoginActivityTest {
//
//    @Rule
//    public CustomRule customRule = new CustomRule();

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    UserModel userModel;

    @Before
    public void setUp() throws Exception {
        userModel = mock(UserModel.class);
        doAnswer(new CustomAnswer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                super.answer(invocation);
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
    public void testOnCreatePresenter() throws Exception {
        LoginPresenter presenter = mock(LoginPresenter.class);
        PowerMockito.whenNew(LoginPresenter.class).withAnyArguments().thenReturn(presenter);
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText username = (EditText) loginActivity.findViewById(R.id.username);
        EditText password = (EditText) loginActivity.findViewById(R.id.password);
        username.setText("huangxiang");
        password.setText("123456");
        assertEquals("huangxiang", loginActivity.getUserName());
        assertEquals("123456", loginActivity.getPassword());
        PowerMockito.mockStatic(FastClickUtils.class);
        Mockito.when(FastClickUtils.isTimeToProcess(Mockito.anyInt())).thenReturn(true);
        loginActivity.findViewById(R.id.login).performClick();
        verify(presenter).login();
//        verify(userModel).login(anyString(), anyString(), any(Callback.class));
    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

    }

    @Test
    public void testOnClick() throws Exception {

    }
}