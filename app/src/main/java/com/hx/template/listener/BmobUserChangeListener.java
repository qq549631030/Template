package com.hx.template.listener;

import org.json.JSONObject;

/**
 * Created by huangx on 2016/8/18.
 */
public abstract class BmobUserChangeListener implements BmobDataChangeListener {

    private String objectId;

    public BmobUserChangeListener(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
