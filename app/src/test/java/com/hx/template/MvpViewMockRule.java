package com.hx.template;

import com.hx.template.mvpview.MvpView;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

/**
 * Created by huangxiang on 16/8/21.
 */
public class MvpViewMockRule implements MethodRule {

    /**
     * Modifies the method-running {@link Statement} to implement an additional
     * test-running rule.
     *
     * @param base   The {@link Statement} to be modified
     * @param method The method to be run
     * @param target The object on which the method will be run.
     * @return a new statement, which may be the same as {@code base},
     * a wrapper around {@code base}, or a completely new Statement.
     */
    @Override
    public Statement apply(final Statement base, FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Class<?> testClass = target.getClass();
                Field[] fields = testClass.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        Mock mockAnnotation = field.getAnnotation(Mock.class);
                        if (mockAnnotation == null) {
                            continue;
                        }
                        Class<?> type = field.getType();
                        if (!MvpView.class.isAssignableFrom(type)) {
                            continue;
                        }
                    }
                }
                base.evaluate();
            }
        };
    }
}
