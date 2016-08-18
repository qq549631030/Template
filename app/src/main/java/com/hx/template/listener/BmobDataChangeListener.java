package com.hx.template.listener;

import org.json.JSONObject;

/**
 * Created by huangx on 2016/8/18.
 */
public abstract class BmobDataChangeListener {
    private String tableName;
    private String objectId;
    private String action;
    private boolean started;

    public BmobDataChangeListener(String tableName, String objectId, String action) {
        this.tableName = tableName;
        this.objectId = objectId;
        this.action = action;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public abstract void onDataChange(JSONObject jsonObject);
}
