package cn.huangx.common.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：SD卡工具类
 * 作者：huangx on 2017/1/18 14:44
 * 邮箱：549631030@qq.com
 */

public class StorageUtils {

    public static String[] possibleStoragePaths = new String[]{
            "/storage/emulated/0",
            "/storage/emulated/legacy",
            "/mnt/sdcard",
            "/sdcard"
    };

    public static File getExternalStorageDirectory(Context context) {
        File apiExternalDirectory = Environment.getExternalStorageDirectory();
        //先看API提供的路径
        if (Environment.getExternalStorageState().equals("mounted") && apiExternalDirectory.exists()) {
            return apiExternalDirectory;
        } else {
            //遍历所有已挂载的存储介质
            String[] mountedVolumePaths = getMountedVolumePaths(context);
            for (String mountedVolumePath : mountedVolumePaths) {
                File file = new File(mountedVolumePath);
                if (file.exists() && file.canRead() && file.canWrite()) {
                    return file;
                }
            }
            //遍历所有有可能是sd卡的目录
            for (String possibleStoragePath : possibleStoragePaths) {
                File file = new File(possibleStoragePath);
                if (file.exists() && file.canRead() && file.canWrite()) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * 获取所有存储介质路径
     *
     * @param context
     * @return
     */
    public static String[] getVolumePaths(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class smClass = sm.getClass();
        try {
            Method getVolumePaths = smClass.getMethod("getVolumePaths", new Class[0]);
            return (String[]) ((String[]) getVolumePaths.invoke(sm, new Object[0]));
        } catch (NoSuchMethodException var5) {
            var5.printStackTrace();
        } catch (IllegalArgumentException var6) {
            var6.printStackTrace();
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        } catch (InvocationTargetException var8) {
            var8.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有已挂载的存储介质路径
     *
     * @param context
     * @return
     */
    public static String[] getMountedVolumePaths(Context context) {
        List<String> mountedVolumeList = new ArrayList<>();
        String[] volumePaths = getVolumePaths(context);
        if (volumePaths != null) {
            for (String volumePath : volumePaths) {
                if ("mounted".equals(getVolumeState(context, volumePath))) {
                    mountedVolumeList.add(volumePath);
                }
            }
        }
        return mountedVolumeList.toArray(new String[mountedVolumeList.size()]);
    }

    /**
     * 获取存储介质挂载状态
     *
     * @param context
     * @param path
     * @return
     */
    public static String getVolumeState(Context context, String path) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class smClass = sm.getClass();
        try {
            Method getVolumeState = smClass.getMethod("getVolumeState", new Class[]{String.class});
            return (String) getVolumeState.invoke(sm, new Object[]{path});
        } catch (NoSuchMethodException var6) {
            var6.printStackTrace();
        } catch (IllegalArgumentException var7) {
            var7.printStackTrace();
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
        } catch (InvocationTargetException var9) {
            var9.printStackTrace();
        }
        return "unmounted";
    }
}
