package com.hx.template.qrcode.decode.zbar;

import android.os.Looper;
import android.os.Message;

import com.hx.template.R;
import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.qrcode.decode.DecodeHandler;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;


/**
 * huangx
 * Zbar解码
 */
final class ZbarDecodeHandler extends DecodeHandler {

    ImageScanner scanner = null;

    public ZbarDecodeHandler(CaptureActivity activity) {
        super(activity);
        this.scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.decode:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case R.id.quit:
                Looper.myLooper().quit();
                break;
        }
    }

    public void decode(byte[] data, int width, int height) {
        // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;
        String result = null;
        Image barcode = new Image(width, height, "Y800");
        barcode.setData(rotatedData);
        if (activity != null) {
            barcode.setCrop(activity.getCropRect().left, activity.getCropRect().top, activity.getCropRect().width(), activity.getCropRect().height());
        }
        int resultCode = scanner.scanImage(barcode);
        if (resultCode != 0) {
            SymbolSet syms = scanner.getResults();
            result = "";
            for (Symbol sym : syms) {
                result = "" + sym.getData();
            }
            if (result != null) {
                if (null != activity.getHandler()) {
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = R.id.decode_succeeded;
                    activity.getHandler().sendMessage(msg);
                }
            }
        } else {
            if (null != activity.getHandler()) {
                activity.getHandler().sendEmptyMessage(R.id.decode_failed);
            }
        }
    }

}
