package com.hx.template.mvp.contract;

/**
 * Created by huangx on 2016/8/10.
 */
public interface ISelectImagePresenter {
    void takeFromCamera(String outPath);

    void takeFromGallery();
}
