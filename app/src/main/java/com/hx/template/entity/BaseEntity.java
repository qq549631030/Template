package com.hx.template.entity;

import java.io.Serializable;

/**
 * Created by huangxiang on 15/7/4.
 */
public class BaseEntity implements Serializable {
    private long id;
    private long createDate;
    private long modifyDate;
    private int delFlag;

    public BaseEntity() {
        super();
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }
}
