package com.hx.template.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by huangx on 2016/6/14.
 */
public class DecimalUtils {

    /**
     * 判断是否是双精度浮点型，不是返回0
     * @param value
     * @return
     */
    public static double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }
    
    /**
     * 判断是否是浮点型，不是返回0
     *
     * @param value 需要转换浮点型的字符串
     * @return 浮点型
     */
    public static float getFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (IllegalArgumentException e) {
            return 0f;
        }
    }

    /**
     * 判断是否是整型，不是返回0
     *
     * @param value 需要转换浮点型的字符串
     * @return 整型
     */
    public static int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    /**
     * 转换千位符
     *
     * @param b  true 无后面两位小数   false 有后面两位小数
     * @param bd BigDecimal bd=new BigDecimal(123456789);  金额
     * @return 无后面两位小数:123,456,789   有后面两位小数:123,456,789.00
     */
    public static String parseMoney(Boolean b, BigDecimal bd) {
        String pattern;
        if (b) {
            //",###,###" 无后面两位小数 
            pattern = ",###,###";
        } else {
            //",###,##0.00" 有后面两位小数
            pattern = ",###,##0.00";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }
}
