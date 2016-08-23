package com.hx.template.presenter;

import com.hx.template.CustomRule;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.mvp.MvpView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * Created by huangxiang on 16/8/20.
 */
public class BasePresenterTest {

    @Rule
    public CustomRule customRule = new CustomRule();
    
    @Mock
    MvpView mvpView;

    BasePresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new BasePresenter();
    }

    @Test
    public void testAttachView() throws Exception {
        assertNull(presenter.getMvpView());
        presenter.attachView(mvpView);
        assertNotNull(presenter.getMvpView());
        assertSame(mvpView, presenter.getMvpView());
    }

    @Test
    public void testDetachView() throws Exception {
        presenter.attachView(mvpView);
        assertNotNull(presenter.getMvpView());
        assertSame(mvpView, presenter.getMvpView());
        presenter.detachView();
        assertNull(presenter.getMvpView());
    }

    @Test
    public void testOnDestroyed() throws Exception {
        presenter.onDestroyed();
    }

    @Test
    public void testIsViewAttached_Attached() throws Exception {
        assertFalse(presenter.isViewAttached());
        presenter.attachView(mvpView);
        assertTrue(presenter.isViewAttached());
    }

    @Test
    public void testIsViewAttached_Not_Attached() throws Exception {
        assertFalse(presenter.isViewAttached());
    }

    @Test
    public void testGetMvpView() throws Exception {
        assertNull(presenter.getMvpView());
        presenter.attachView(mvpView);
        assertNotNull(presenter.getMvpView());
        assertSame(mvpView, presenter.getMvpView());
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void testCheckViewAttached() throws Exception {
        assertNull(presenter.getMvpView());
        presenter.checkViewAttached();
    }

    @Test
    public void testCheckViewAttached_Attached() throws Exception {
        assertNull(presenter.getMvpView());
        presenter.attachView(mvpView);
        presenter.checkViewAttached();
    }
}