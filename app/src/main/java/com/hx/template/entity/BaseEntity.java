package com.hx.template.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

/**
 * Created by huangx on 2016/8/23.
 */
public class BaseEntity implements RealmModel {
    @PrimaryKey
    private String objectId;
    private String createdAt;
    private String updatedAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
