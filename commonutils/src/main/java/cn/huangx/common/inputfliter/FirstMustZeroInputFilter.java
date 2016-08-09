package cn.huangx.common.inputfliter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 第一位必须是"0"
 * Created by huangx on 2016/7/22.
 */
public class FirstMustZeroInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if ("".equals(source)) return null;
        try {
            String dValuePre = dest.subSequence(0, dstart).toString();
            if (TextUtils.isEmpty(dValuePre) && !source.subSequence(start, end).toString().startsWith("0")) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
        return null;
    }
}
