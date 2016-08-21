package com.hx.template;

import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;

import java.lang.reflect.Method;

/**
 * Created by huangxiang on 16/8/21.
 */
public class CustomAnswer extends ReturnsEmptyValues {
    @Override
    public Object answer(InvocationOnMock invocation) {
        System.out.println(invocation);
        return super.answer(invocation);
    }
}
