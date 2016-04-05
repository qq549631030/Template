package com.hx.template.qrcode.utils;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;

import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.util.Arrays;
import java.util.Hashtable;

public class ImageScanUtil {
    /**
     * 用ZBar解析图片中二维码
     *
     * @param scanBitmap
     * @return
     */
    public static String decodeByZbar(Bitmap scanBitmap) {
        final int width = scanBitmap.getWidth();
        final int height = scanBitmap.getHeight();
        // 转成RGB
        final int[] pixels = new int[width * height];
        scanBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        // 将RGB转为灰度数据。
        byte[] greyData = new byte[width * height];
        for (int i = 0; i < greyData.length; i++) {
            greyData[i] = (byte) ((((pixels[i] & 0x00ff0000) >> 16) * 19595 + ((pixels[i] & 0x0000ff00) >> 8) * 38469
                    + ((pixels[i] & 0x000000ff)) * 7472) >> 16);
        }

        Image barcode = new Image(width, width, "GREY");
        barcode.setData(greyData);
        ImageScanner scanner = new ImageScanner();
        int ret = scanner.scanImage(barcode);
        String resultString = "";
        if (ret != 0) {
            SymbolSet syms = scanner.getResults();
            resultString = "";
            for (Symbol sym : syms) {
                resultString = "" + sym.getData();
            }
        }
        return resultString;
    }

    /**
     * 用ZXing解析图片中二维码
     *
     * @param scanBitmap
     * @return
     */
    public static String decodeByZXing(Bitmap scanBitmap) {
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();

        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(getYUV420sp(scanBitmap.getWidth(), scanBitmap.getHeight(), scanBitmap),
                scanBitmap.getWidth(),
                scanBitmap.getHeight(),
                0, 0,
                scanBitmap.getWidth(),
                scanBitmap.getHeight(),
                false);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        try {
            Result result = multiFormatReader.decode(binaryBitmap, hints);
            return result.getText();
        } catch (Exception e) {
        } finally {
            multiFormatReader.reset();
        }
        return null;
    }

    /**
     * 根据Bitmap的ARGB值生成YUV420SP数据。
     *
     * @param inputWidth  image width
     * @param inputHeight image height
     * @param scaled      bmp
     * @return YUV420SP数组
     */
    private static byte[] getYUV420sp(int inputWidth, int inputHeight, Bitmap scaled) {
        int[] argb = new int[inputWidth * inputHeight];
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        /**
         * 需要转换成偶数的像素点，否则编码YUV420的时候有可能导致分配的空间大小不够而溢出。
         */
        int requiredWidth = inputWidth % 2 == 0 ? inputWidth : inputWidth + 1;
        int requiredHeight = inputHeight % 2 == 0 ? inputHeight : inputHeight + 1;
        byte[] yuvs = new byte[1];
        int byteLength = requiredWidth * requiredHeight * 3 / 2;
        if (yuvs == null || yuvs.length < byteLength) {
            yuvs = new byte[byteLength];
        } else {
            Arrays.fill(yuvs, (byte) 0);
        }
        encodeYUV420SP(yuvs, argb, inputWidth, inputHeight);
//        scaled.recycle();
        return yuvs;
    }

    /**
     * RGB转YUV420sp
     *
     * @param yuv420sp inputWidth * inputHeight * 3 / 2
     * @param argb     inputWidth * inputHeight
     * @param width    image width
     * @param height   image height
     */
    private static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        // 帧图片的像素大小
        final int frameSize = width * height;
        // ---YUV数据---
        int Y, U, V;
        // Y的index从0开始
        int yIndex = 0;
        // UV的index从frameSize开始
        int uvIndex = frameSize;

        // ---颜色数据---
        int R, G, B;
        int rgbIndex = 0;

        // ---循环所有像素点，RGB转YUV---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                R = (argb[rgbIndex] & 0xff0000) >> 16;
                G = (argb[rgbIndex] & 0xff00) >> 8;
                B = (argb[rgbIndex] & 0xff);
                //
                rgbIndex++;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                Y = Math.max(0, Math.min(Y, 255));
                U = Math.max(0, Math.min(U, 255));
                V = Math.max(0, Math.min(V, 255));

                // NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U. Note the sampling is every other
                // pixel AND every other scan line.
                // ---Y---
                yuv420sp[yIndex++] = (byte) Y;
                // ---UV---
                if ((j % 2 == 0) && (i % 2 == 0)) {
                    //
                    yuv420sp[uvIndex++] = (byte) V;
                    //
                    yuv420sp[uvIndex++] = (byte) U;
                }
            }
        }
    }
}
