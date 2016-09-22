package com.hx.template.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by huangxiang on 15/8/12.
 */
public class ImageUtils {

    /**
     * 取出按默认比例(800x480)压缩的图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        return getImage(srcPath, 800f, 480f);
    }

    /**
     * 取出按默认比例(800x480)压缩的图片
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getImage(Context context, Uri uri) {
        try {
            return getImage(context, uri, 800f, 480f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取出按比例压缩的图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath, float hh, float ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, (int) ww, (int) hh);//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * 取出按比例压缩的图片
     *
     * @param context
     * @param url
     * @param hh
     * @param ww
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap getImage(Context context, Uri url, float hh, float ww) throws FileNotFoundException {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(url), null, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, (int) ww, (int) hh);//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(url), null, newOpts);
        return bitmap;
    }

    /**
     * 计算图片压缩采样率
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int w = options.outWidth;
        int h = options.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int inSampleSize = 1;//be=1表示不缩放
        if (w > h && w > reqWidth) {//如果宽度大的话根据宽度固定大小缩放
            inSampleSize = options.outWidth / reqWidth;
        } else if (w < h && h > reqHeight) {//如果高度高的话根据宽度固定大小缩放
            inSampleSize = options.outHeight / reqHeight;
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        return inSampleSize;
    }

    /**
     * 将Bitmap转化为流
     *
     * @param bitmap
     * @return
     */
    public static InputStream imageToSteam(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 将Uri所指的图片转化为流
     *
     * @param context
     * @param uri
     * @return
     */
    public static InputStream imageUriCompressToSteam(Context context, Uri uri) {
        Bitmap bitmap = getImage(context, uri);
        if (bitmap != null) {
            return imageToSteam(bitmap);
        }
        return null;
    }

    /**
     * 将Bitmap保存到指定文件
     *
     * @param bitmap
     * @param file
     * @return
     */
    public static boolean saveImage(Bitmap bitmap, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80,
                    outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * View转换为Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 将图片转化为灰度图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap grayBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap greyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(greyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return greyBitmap;
    }
}
