package com.hx.template.acra;

import android.content.Context;
import android.support.annotation.NonNull;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.JSONReportBuilder;

import java.io.File;

import cn.huangx.common.utils.FileUtils;
import cn.huangx.common.utils.ToastUtils;

/**
 * Created by huangxiang on 2017/1/14.
 */

public class MainReportSender implements ReportSender {
    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {
        try {
            File file = new File(context.getExternalCacheDir(), "crash_report_" + System.currentTimeMillis() + ".json");
            FileUtils.writeFile(file.getPath(), errorContent.toJSON().toString());
        } catch (JSONReportBuilder.JSONReportException e) {
            e.printStackTrace();
        }
    }
}
