package cn.huangx.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片压缩工具
 * Created by huangxiang on 15/8/12.
 */
public class ImageCompressUtils {

    /**
     * 从文件中取出按默认比例(800x480)压缩的图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        return getImage(srcPath, 800f, 480f);
    }

    /**
     * 从图片数据中取出按默认比例(800x480)压缩的图片
     *
     * @param imageData
     * @return
     */
    public static Bitmap getImage(byte[] imageData) {
        return getImage(imageData, 800f, 480f);
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
     * 从文件中取出按比例压缩的图片
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
     * 从图片数据中取出按比例压缩的图片
     *
     * @param imageData
     * @param hh
     * @param ww
     * @return
     */
    public static Bitmap getImage(byte[] imageData, float hh, float ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = calculateInSampleSize(newOpts, (int) ww, (int) hh);//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, newOpts);
        return bitmap;
    }

    /**
     * 从Uri中取出按比例压缩的图片
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
    public static InputStream bitmapToSteam(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        baos.close();
        return isBm;
    }

    /**
     * 将Bitmap转化为字节数组
     *
     * @param bitmap
     * @return
     * @throws IOException
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

    /**
     * 将Uri所指的图片转化为流
     *
     * @param context
     * @param uri
     * @return
     */
    public static InputStream imageUriCompressToSteam(Context context, Uri uri) throws IOException {
        Bitmap bitmap = getImage(context, uri);
        if (bitmap != null) {
            return bitmapToSteam(bitmap);
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
        if (bitmap == null) {
            return false;
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(
                    file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static boolean isImage(String path){
        
        return true;
    }
}
