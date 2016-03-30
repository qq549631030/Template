/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hx.template.qrcode.decode.zxing;

import android.os.Handler;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.qrcode.decode.DecodeThread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * huangx
 * Zxing解码线程
 */
public class ZxingDecodeThread extends DecodeThread {

    public static final String BARCODE_BITMAP = "barcode_bitmap";

    public static final int BARCODE_MODE = 0X100;
    public static final int QRCODE_MODE = 0X200;
    public static final int ALL_MODE = 0X300;

    private final Map<DecodeHintType, Object> hints;

    public ZxingDecodeThread(CaptureActivity activity, int decodeMode) {
        super(activity);
        hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

        Collection<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
        decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));

        switch (decodeMode) {
            case BARCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                break;

            case QRCODE_MODE:
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            case ALL_MODE:
                decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
                decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
                break;

            default:
                break;
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
    }

    @Override
    protected Handler initHandler(CaptureActivity activity) {
        return new ZxingDecodeHandler(activity, hints);
    }
}
