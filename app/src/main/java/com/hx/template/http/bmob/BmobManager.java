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

    interface BmobRealTimeDataConnectListener {
        void onConnectCompleted(Exception e);
    }

    private static List<BmobDataChangeListener> listeners = new ArrayList<BmobDataChangeListener>();

    private static BmobRealTimeData bmobRealTimeData;

    public static void init(Context context) {
        Bmob.initialize(context, APP_KEY);
    }

    public static void startRealTimeListener(final BmobRealTimeDataConnectListener listener) {
        if (bmobRealTimeData == null) {
            bmobRealTimeData = new BmobRealTimeData();
        }
        if (!bmobRealTimeData.isConnected()) {
            bmobRealTimeData.start(new ValueEventListener() {
                @Override
                public void onConnectCompleted(Exception e) {
                    if (listener != null) {
                        listener.onConnectCompleted(e);
                    }
                }

                @Override
                public void onDataChange(JSONObject jsonObject) {
                    for (BmobDataChangeListener listener : listeners) {
                        listener.onDataChange(jsonObject);
                    }
                }
            });
        } else {
            if (listener != null) {
                listener.onConnectCompleted(null);
            }
        }
    }


    public static void subRowUpdate(final String tableName, final String objectId, final BmobDataChangeListener listener) {
        startRealTimeListener(new BmobRealTimeDataConnectListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if (e == null) {
                    if (bmobRealTimeData != null && bmobRealTimeData.isConnected()) {
                        if (!listeners.contains(listener)) {
                            listeners.add(listener);
                        }
                        bmobRealTimeData.subRowUpdate(tableName, objectId);
                    }
                }
            }
        });
    }

    public static void unSubRowUpdate(String tableName, String objectId, BmobDataChangeListener listener) {
        if (bmobRealTimeData != null && bmobRealTimeData.isConnected()) {
            bmobRealTimeData.unsubRowUpdate(tableName, objectId);
            listeners.remove(listener);
        }
    }
}
