package cn.huangx.common.inputfliter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 通用输入过滤
 * Created by huangx on 2016/7/25.
 */
public class CommonInputFilter implements InputFilter {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String DECIMAL = ".0123456789";
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private char[] acceptChars;

    public CommonInputFilter(String acceptStr) {
        if (!TextUtils.isEmpty(acceptStr)) {
            this.acceptChars = acceptStr.toCharArray();
        }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (acceptChars != null && acceptChars.length > 0) {
            for (int i = start; i < end; i++) {
                if (!ok(acceptChars, source.charAt(i))) {
                    return "";
                }
            }
        }
        return null;
    }

    protected static boolean ok(char[] accept, char c) {
        for (int i = accept.length - 1; i >= 0; i--) {
            if (accept[i] == c) {
                return true;
            }
        }
        return false;
    }

}
