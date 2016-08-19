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
    public static final int MODEL_TYPE_VOLLEY = 1;
    public static final int MODEL_TYPE_RETROFIT = 2;
    public static final int MODEL_TYPE_BMOB = 3;

    private static final int DEFAULT_MODEL_TYPE = MODEL_TYPE_BMOB;

    public static UserModel newUserModel() {
        return newUserModel(DEFAULT_MODEL_TYPE);
    }

    public static UserModel newUserModel(int modelType) {
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

    public static SMSModel newSMSModel() {
        return newSMSModel(DEFAULT_MODEL_TYPE);
    }

    public static SMSModel newSMSModel(int modelType) {
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

    public static FileModel newFileModel() {
        return newFileModel(DEFAULT_MODEL_TYPE);
    }

    public static FileModel newFileModel(int modelType) {
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
}
