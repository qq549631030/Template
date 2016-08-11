package cn.huangx.common.inputfliter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 第一位不能是"0"
 * Created by huangx on 2016/8/9.
 */
public class FirstNotZeroInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if ("".equals(source)) return null;
        try {
            String dValuePre = dest.subSequence(0, dstart).toString();
            if (TextUtils.isEmpty(dValuePre) && source.subSequence(start, end).toString().startsWith("0")) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
        return null;
    }
}
