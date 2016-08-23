package com.hx.template.model;

import com.hx.template.model.impl.bmob.BmobFileModel;
import com.hx.template.model.impl.bmob.BmobSMSModel;
import com.hx.template.model.impl.bmob.BmobUserImpl;
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

    /**
     * 实例化默认UserModel
     *
     * @return
     */
    public static UserModel newUserModel() {
        return newUserModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现UserModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static UserModel newUserModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new FakeUserModel();
            case MODEL_TYPE_RETROFIT:
                return new FakeUserModel();
            case MODEL_TYPE_BMOB:
                return new FakeUserModel();
            default:
                return new FakeUserModel();
        }
    }

    /**
     * 实例化默认SMSModel
     *
     * @return
     */
    public static SMSModel newSMSModel() {
        return newSMSModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现SMSModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static SMSModel newSMSModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new FakeSMSModel();
            case MODEL_TYPE_RETROFIT:
                return new FakeSMSModel();
            case MODEL_TYPE_BMOB:
                return new FakeSMSModel();
            default:
                return new FakeSMSModel();
        }
    }

    /**
     * 实例化默认FileModel
     *
     * @return
     */
    public static FileModel newFileModel() {
        return newFileModel(DEFAULT_MODEL_TYPE);
    }

    /**
     * 实例化指定实现FileModel
     *
     * @param modelType 实现类型
     * @return
     */
    public static FileModel newFileModel(int modelType) {
        switch (modelType) {
            case MODEL_TYPE_VOLLEY:
                return new FakeFileModel();
            case MODEL_TYPE_RETROFIT:
                return new FakeFileModel();
            case MODEL_TYPE_BMOB:
                return new FakeFileModel();
            default:
                return new FakeFileModel();
        }
    }
}
