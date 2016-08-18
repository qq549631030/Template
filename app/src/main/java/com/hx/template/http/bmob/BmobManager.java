package com.hx.template.http.bmob;

import android.content.Context;

import com.hx.template.entity.User;
import com.hx.template.listener.BmobDataChangeListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by huangx on 2016/8/18.
 */
public class BmobManager {

    public static final String APP_KEY = "0dffa5dd0fb6b49c5dbcd57971946e0b";

    private static List<BmobDataChangeListener> listeners = new ArrayList<BmobDataChangeListener>();

    private static BmobRealTimeData bmobRealTimeData;

    public static void init(Context context) {
        Bmob.initialize(context, APP_KEY);
    }

    public static void startRealTimeListener() {
        if (bmobRealTimeData == null) {
            bmobRealTimeData = new BmobRealTimeData();
        }
        if (!bmobRealTimeData.isConnected()) {
            bmobRealTimeData.start(new ValueEventListener() {
                @Override
                public void onConnectCompleted(Exception e) {
                    for (BmobDataChangeListener listener : listeners) {
                        if (!listener.isStarted()) {
                            subListener(listener);
                        }
                    }
                }

                @Override
                public void onDataChange(JSONObject jsonObject) {
                    for (BmobDataChangeListener listener : listeners) {
                        listener.onDataChange(jsonObject);
                    }
                }
            });
        }
    }

    private static void subListener(BmobDataChangeListener listener) {
        if (bmobRealTimeData.ACTION_UPDATETABLE.equals(listener.getAction())) {
            bmobRealTimeData.subTableUpdate(listener.getTableName());
        } else if (bmobRealTimeData.ACTION_UPDATEROW.equals(listener.getAction())) {
            bmobRealTimeData.subRowUpdate(listener.getTableName(), listener.getObjectId());
        } else if (bmobRealTimeData.ACTION_DELETETABLE.equals(listener.getAction())) {
            bmobRealTimeData.subTableDelete(listener.getTableName());
        } else if (bmobRealTimeData.ACTION_DELETEROW.equals(listener.getAction())) {
            bmobRealTimeData.subRowDelete(listener.getTableName(), listener.getObjectId());
        }
        listener.setStarted(true);
    }


    private static void unSubListener(BmobDataChangeListener listener) {
        if (bmobRealTimeData.ACTION_UPDATETABLE.equals(listener.getAction())) {
            bmobRealTimeData.unsubTableUpdate(listener.getTableName());
        } else if (bmobRealTimeData.ACTION_UPDATEROW.equals(listener.getAction())) {
            bmobRealTimeData.unsubRowUpdate(listener.getTableName(), listener.getObjectId());
        } else if (bmobRealTimeData.ACTION_DELETETABLE.equals(listener.getAction())) {
            bmobRealTimeData.unsubTableDelete(listener.getTableName());
        } else if (bmobRealTimeData.ACTION_DELETEROW.equals(listener.getAction())) {
            bmobRealTimeData.unsubRowDelete(listener.getTableName(), listener.getObjectId());
        }
        listener.setStarted(false);
    }

    /**
     * 监听数据变化
     *
     * @param listener
     */
    public static void subBmobDataChangeListener(BmobDataChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
        if (bmobRealTimeData == null || !bmobRealTimeData.isConnected()) {
            startRealTimeListener();
        } else {
            subListener(listener);
        }

    }

    /**
     * 取消监听数据变化
     *
     * @param listener
     */
    public static void unSubBmobDataChangeListener(BmobDataChangeListener listener) {
        listeners.remove(listener);
        if (bmobRealTimeData != null && bmobRealTimeData.isConnected()) {
            unSubListener(listener);
        }
    }
}
