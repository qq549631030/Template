package com.hx.template;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Created by huangxiang on 16/8/21.
 */
public class CustomRule implements MethodRule {

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("===" + method.getName() + " start" + "===");
                base.evaluate();
                System.out.println("===" + method.getName() + " end" + "===\n");
            }
        };
    }
}
