package com.hx.template.model;

import com.hx.template.model.impl.bmob.BmobFileModel;
import com.hx.template.model.impl.bmob.BmobSMSModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.model.impl.huanxin.HXIMModel;
import com.hx.template.model.impl.retrofit.RetrofitUserModel;
import com.hx.template.model.impl.volley.VolleyUserModel;

/**
 * Created by huangxiang on 16/8/20.
 */
public class ModelManager {
    public static final int MODEL_TYPE_VOLLEY = 1;//Velloy实现
    public static final int MODEL_TYPE_RETROFIT = 2;//Retrofit实现
    public static final int MODEL_TYPE_BMOB = 3;//Bmob实现

    private static final int DEFAULT_MODEL_TYPE = MODEL_TYPE_BMOB;

    public static final int IM_MODEL_TYPE_HUANGXIN = 1;

    public static final int DEFAULT_IM_MODEL_TYPE = IM_MODEL_TYPE_HUANGXIN;

    /**
     * 实例化默认UserModel
     *
     * @return
     */
    public static UserModel provideUserModel() {
        return provideUserModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现UserModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static UserModel provideUserModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new VolleyUserModel();
            case MODEL_TYPE_RETROFIT:
                return new RetrofitUserModel();
            case MODEL_TYPE_BMOB:
                return new BmobUserImpl();
            default:
                return new BmobUserImpl();
        }
    }

    /**
     * 实例化默认SMSModel
     *
     * @return
     */
    public static SMSModel provideSMSModel() {
        return provideSMSModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现SMSModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static SMSModel provideSMSModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new BmobSMSModel();
            case MODEL_TYPE_RETROFIT:
                return new BmobSMSModel();
            case MODEL_TYPE_BMOB:
                return new BmobSMSModel();
            default:
                return new BmobSMSModel();
        }
    }

    /**
     * 实例化默认FileModel
     *
     * @return
     */
    public static FileModel provideFileModel() {
        return provideFileModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现FileModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static FileModel provideFileModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new BmobFileModel();
            case MODEL_TYPE_RETROFIT:
                return new BmobFileModel();
            case MODEL_TYPE_BMOB:
                return new BmobFileModel();
            default:
                return new BmobFileModel();
        }
    }

    /**
     * 实例化默认IMModel
     *
     * @return
     */
    public static IMModel provideIMModel() {
        return provideIMModel(DEFAULT_IM_MODEL_TYPE);
    }

    /**
     * 实例化指定实现IMModel
     *
     * @param imModelType 实现类型
     * @return
     */
    public static IMModel provideIMModel(int imModelType) {
        switch (imModelType) {
            case IM_MODEL_TYPE_HUANGXIN:
                return new HXIMModel();
            default:
                return new HXIMModel();
        }
    }
}
