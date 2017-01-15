package com.hx.template.acra;

import android.content.Context;
import android.support.annotation.NonNull;

import org.acra.config.ACRAConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;

import cn.huangx.common.utils.ToastUtils;

/**
 * Created by huangxiang on 2017/1/14.
 */

public class MainReportSenderFactory implements ReportSenderFactory {
    @NonNull
    @Override
    public ReportSender create(@NonNull Context context, @NonNull ACRAConfiguration config) {
        return new MainReportSender();
    }
}
