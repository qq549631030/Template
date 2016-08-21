package com.hx.template.model.impl.bmob;

import com.hx.template.model.Callback;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by huangxiang on 16/8/20.
 */
public class BmobSMSModelTest {
    BmobSMSModel smsModel;

    @Before
    public void setUp() throws Exception {
        smsModel = Mockito.mock(BmobSMSModel.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRequestSMSCode() throws Exception {
        Callback callback = Mockito.mock(Callback.class);
        smsModel.requestSMSCode("15870679047", "BIND_PHONE", callback);
        Mockito.verify(smsModel).requestSMSCode("15870679047", "BIND_PHONE", callback);
    }

    @Test
    public void testVerifySmsCode() throws Exception {

    }
}