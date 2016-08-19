package cn.huangx.common.inputfliter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 限制整数，小数长度
 * Created by huangx on 2016/8/9.
 */
public class DecimalInputFilter implements InputFilter {

    private int integerLength = -1;//整数位数(负数表示不限制)
    private int fractionalLength = -1;//小数位数(负数表示不限制)

    public DecimalInputFilter(int integerLength, int fractionalLength) {
        this.integerLength = integerLength;
        this.fractionalLength = fractionalLength;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        CharSequence destCsq = dest.subSequence(dstart, dend);
        String sourceStr = source.subSequence(start, end).toString();
        String destHeadStr = dest.subSequence(0, dstart).toString();
        String destTailStr = dest.subSequence(dend, dest.length()).toString();
        String mergedStr = destHeadStr + sourceStr + destTailStr;
        boolean haveDot = false;
        for (int i = 0; i < mergedStr.length(); i++) {
            if (mergedStr.charAt(i) == '.') {
                if (!haveDot) {
                    haveDot = true;
                } else {
                    return destCsq;//多于一个点
                }
            }
        }
        String[] splitArray = mergedStr.split("\\.");
        if (splitArray == null || splitArray.length <= 0) {
            return null;
        }
        String integerStr = splitArray[0];
        if (integerLength == 0) {//整数部份长度为0，表示只有0才符合
            if (integerStr.length() != 1 || !integerStr.equals("0")) {
                return destCsq;
            }
        } else if (integerLength > 0) {
            if (integerStr.length() > integerLength) {
                return destCsq;
            }
            if (integerStr.length() > 1 && integerStr.startsWith("0")) {
                return destCsq;
            }
        }
        if (haveDot) {//有小数点
            if (fractionalLength == 0) {//小数长度为0表示只能是一个整数
                return destCsq;
            } else {
                if (splitArray.length == 2) {//小数有值
                    String fractionalStr = splitArray[1];
                    if (fractionalLength > 0 && fractionalStr.length() > fractionalLength) {
                        return destCsq;
                    }
                }
            }
        }
        return null;
    }
}
