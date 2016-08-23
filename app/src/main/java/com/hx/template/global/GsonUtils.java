package com.hx.template.global;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huangx on 2016/5/18.
 */
public class GsonUtils {
    public static String toJson(Object source) {
        Gson gson = new Gson();
        return gson.toJson(source);
    }

    public static JSONObject toJsonObj(Object source) {
        String jsonStr = toJson(source);
        try {
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
