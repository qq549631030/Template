package com.hx.template.presenter;

import com.hx.template.mvpview.MvpView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * Created by huangxiang on 16/8/20.
 */
public class BasePresenterTest {
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
        assertNull(presenter.getMvpView());
    }

    @Test
    public void testIsViewAttached() throws Exception {
        assertFalse(presenter.isViewAttached());
        presenter.attachView(mvpView);
        assertTrue(presenter.isViewAttached());
    }

    @Test
    public void testGetMvpView() throws Exception {
        assertNull(presenter.getMvpView());
        presenter.attachView(mvpView);
        assertSame(mvpView, presenter.getMvpView());
    }

    @Test
    public void testCheckViewAttached() throws Exception {

    }
}