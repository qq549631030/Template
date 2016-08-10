package com.hx.template.imageloader.uil;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义本地缓存文件名称生成器
 * Created by huangx on 2016/7/15.
 */
public class CustomMd5FileNameGenerator extends Md5FileNameGenerator {
    /**
     * 指定某些url的对应本地缓存文件名称 格式（key为url，value为本地文件名）
     */
    public static Map<String, String> fixNameMap = new HashMap<String, String>();

    @Override
    public String generate(String imageUri) {
        if (fixNameMap.containsKey(imageUri)) {
            return fixNameMap.get(imageUri);
        }
        return super.generate(imageUri);
    }
}
