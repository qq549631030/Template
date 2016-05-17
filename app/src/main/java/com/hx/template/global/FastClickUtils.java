package com.hx.template.global;

import android.content.Context;
import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

/**
 * 防暴力点击工具
 * Created by huangx on 2016/5/17.
 */
public class FastClickUtils {

    private static final long PROCESS_INTERVAL = 500;

    private static Map<Integer, Long> map = new HashMap<Integer, Long>();
    ;

    /**
     * 是否到了处理事件时间
     *
     * @param viewId 要点击的View ID
     * @return
     */
    public static boolean isTimeToProcess(int viewId) {
        return isTimeToProcess(viewId, PROCESS_INTERVAL);
    }

    /**
     * 是否到了处理事件时间
     *
     * @param viewId          要点击的View ID
     * @param processInterval 两次点击时间间隔
     * @return
     */
    public static boolean isTimeToProcess(int viewId, long processInterval) {
//        if (map == null) {
//            map = new HashMap<Integer, Long>();
//        }
        long lastProcessTime = map.get(viewId) == null ? 0 : map.get(viewId).longValue();
        System.out.println("lastProcessTime = "+lastProcessTime);
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - lastProcessTime < processInterval) {
            return false;
        }
        map.put(viewId, currentTime);
        return true;
    }
}
