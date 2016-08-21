package org.mockito.configuration;

import com.hx.template.CustomAnswer;

import org.mockito.ReturnValues;
import org.mockito.internal.configuration.InjectingAnnotationEngine;
import org.mockito.stubbing.Answer;

/**
 * Created by huangxiang on 16/8/21.
 */
public class MockitoConfiguration implements IMockitoConfiguration {
    /**
     * @deprecated <b>Please use {@link IMockitoConfiguration#getDefaultAnswer()}</b>
     * <p/>
     * Steps:
     * <p/>
     * 1. Leave the implementation of getReturnValues() method empty - it's not going to be used anyway.
     * <p/>
     * 2. Implement getDefaultAnswer() instead.
     * <p/>
     * In rare cases your code might not compile with recent deprecation & changes.
     * Very sorry for inconvenience but it had to be done in order to keep framework consistent.
     * <p/>
     * See javadoc {@link ReturnValues} for info why this method was deprecated
     * <p/>
     * Allows configuring the default return values of unstubbed invocations
     * <p/>
     * See javadoc for {@link IMockitoConfiguration}
     */
    @Override
    public ReturnValues getReturnValues() {
        return null;
    }

    /**
     * Allows configuring the default answers of unstubbed invocations
     * <p/>
     * See javadoc for {@link IMockitoConfiguration}
     */
    @Override
    public Answer<Object> getDefaultAnswer() {
        return new CustomAnswer();
    }

    /**
     * Configures annotations for mocks
     * <p/>
     * See javadoc for {@link IMockitoConfiguration}
     */
    @Override
    public AnnotationEngine getAnnotationEngine() {
        return new InjectingAnnotationEngine();
    }

    /**
     * This should be turned on unless you're a Mockito developer and you wish
     * to have verbose (read: messy) stack traces that only few understand (eg:
     * Mockito developers)
     * <p/>
     * See javadoc for {@link IMockitoConfiguration}
     *
     * @return if Mockito should clean stack traces
     */
    @Override
    public boolean cleansStackTrace() {
        return true;
    }

    /**
     * Allow objenesis to cache classes. If you're in an environment where classes
     * are dynamically reloaded, you can disable this to avoid classcast exceptions.
     */
    @Override
    public boolean enableClassCache() {
        return true;
    }
}
