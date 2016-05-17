package com.hx.template.global;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import com.hx.template.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huangx on 2016/5/17.
 */
public class GlobalActivityManager {

    private static List<Activity> mList;

    /**
     * 记录一个Activity
     *
     * @param activity 要记录的Activity
     */
    public static void push(Activity activity) {
        synchronized (GlobalActivityManager.class) {
            if (activity == null) {
                return;
            }
            if (mList == null) {
                mList = new ArrayList<Activity>();
            }
            if (mList.contains(activity)) {
                return;
            }
            mList.add(activity);
        }
    }

    /**
     * 清除一个Activity记录
     *
     * @param activity 要清除记录的的Activity
     * @return
     */
    public static boolean remove(Activity activity) {
        synchronized (GlobalActivityManager.class) {
            if (activity == null || mList == null || !mList.contains(activity)) {
                return false;
            }
            boolean result = mList.remove(activity);
            return result;
        }
    }

    /**
     * 判断指定类型的Activity是否已经存在
     *
     * @param clz 指定Activity类
     * @return
     */
    public static boolean isActivityExist(Class<? extends Activity> clz) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return false;
            }
            List<Activity> list = new ArrayList<Activity>();
            list.addAll(mList);
            for (int i = list.size() - 1; i >= 0; i--) {
                Activity activity = list.get(i);
                if (activity == null || activity.isFinishing()) {
                    continue;
                }
                if (activity.getClass().equals(clz)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 获取顶部Activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return null;
            }
            List<Activity> list = new ArrayList<Activity>();
            list.addAll(mList);
            for (int i = list.size() - 1; i >= 0; i--) {
                Activity activity = list.get(i);
                if (activity.isFinishing()) {
                    continue;
                }
                return activity;
            }
            return list.get(list.size() - 1);
        }
    }

    /**
     * 获取顶部BaseActivity
     *
     * @return
     */
    public static Activity getTopBaseActivity() {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return null;
            }
            List<Activity> list = new ArrayList<Activity>();
            list.addAll(mList);
            for (int i = list.size() - 1; i >= 0; i--) {
                Activity activity = list.get(i);
                if (activity.isFinishing()) {
                    continue;
                }
                if (!(activity instanceof BaseActivity)) {
                    continue;
                }
                return activity;
            }
            return list.get(list.size() - 1);
        }
    }

    /**
     * 获取顶部Activity,指定类型除外
     *
     * @param clzs 例外的类型
     * @return
     */
    public static Activity getTopActivityAndExclude(Class<? extends Activity>... clzs) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return null;
            }
            if (clzs != null && clzs.length > 0) {
                List<Activity> lists = new ArrayList<Activity>();
                lists.addAll(mList);
                List<Class<? extends Activity>> list = Arrays.asList(clzs);
                for (int i = lists.size() - 1; i >= 0; i--) {
                    Activity activity = lists.get(i);
                    if (!activity.isFinishing() && !list.contains(activity.getClass())) {
                        return lists.get(i);
                    }
                }
            }
            return getTopActivity();
        }
    }

    /**
     * 获取顶部BaseActivity,指定类型除外
     *
     * @param clzs 例外的类型
     * @return
     */
    public static Activity getTopBaseActivityAndExclude(Class<? extends Activity>... clzs) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return null;
            }
            if (clzs != null && clzs.length > 0) {
                List<Activity> lists = new ArrayList<Activity>();
                lists.addAll(mList);
                List<Class<? extends Activity>> list = Arrays.asList(clzs);
                for (int i = lists.size() - 1; i >= 0; i--) {
                    Activity activity = lists.get(i);
                    if (!(activity instanceof BaseActivity)) {
                        continue;
                    }
                    if (!activity.isFinishing() && !list.contains(activity.getClass())) {
                        return lists.get(i);
                    }
                }
            }
            return getTopBaseActivity();
        }
    }

    /**
     * 关闭所有Activity
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean finishAll() {
        synchronized (GlobalActivityManager.class) {
            return finishOther();
        }
    }

    /**
     * 关闭指定类型的所有Activity
     *
     * @param clzs 指定的类型
     * @return
     */
    public static boolean finishAll(Class<? extends Activity> clzs) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return false;
            }
            List<Activity> tmpList = new ArrayList<Activity>();
            tmpList.addAll(mList);
            mList.clear();
            for (int i = 0; i < tmpList.size(); i++) {
                Activity activity = tmpList.get(i);
                if (activity.isFinishing()) {
                    continue;
                }
                if (activity.getClass().equals(clzs)) {
                    if (activity instanceof BaseActivity) {
                        ((BaseActivity) activity).finishForever();
                    } else {
                        activity.finish();
                    }
                    continue;
                }
                mList.add(activity);
            }
            tmpList.clear();
            return true;
        }
    }

    /**
     * 关闭除指定类型之外的Activity
     *
     * @param excludeClzs 例外的类型
     * @return
     */
    public static boolean finishOther(Class<? extends Activity>... excludeClzs) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return false;
            }
            List<Class<? extends Activity>> excludeList = null;
            if (excludeClzs != null && excludeClzs.length > 0) {
                excludeList = Arrays.asList(excludeClzs);
            }
            List<Activity> tmpList = new ArrayList<Activity>();
            List<Activity> removeList = new ArrayList<Activity>();
            tmpList.addAll(mList);
            mList.clear();
            if (excludeList != null && excludeList.size() > 0) {
                for (Activity activity : tmpList) {
                    if (excludeList.contains(activity.getClass())) {
                        removeList.add(activity);
                        mList.add(activity);
                    }
                }
            }
            tmpList.removeAll(removeList);
            for (Activity activity : tmpList) {
                if (activity instanceof BaseActivity) {
                    ((BaseActivity) activity).finishForever();
                } else {
                    activity.finish();
                }
            }
            tmpList.clear();
            return true;
        }
    }

    /**
     * 关闭除指定类型之外的Activity
     *
     * @param exclude 例外的类型
     * @return
     */
    public static boolean finishOther(Activity exclude) {
        synchronized (GlobalActivityManager.class) {
            if (mList == null || mList.size() == 0) {
                return false;
            }
            List<Activity> tmpList = new ArrayList<Activity>();
            tmpList.addAll(mList);
            mList.clear();
            if (exclude != null && tmpList.contains(exclude)) {
                tmpList.remove(exclude);
                mList.add(exclude);
            }
            for (Activity activity : tmpList) {
                if (activity instanceof BaseActivity) {
                    ((BaseActivity) activity).finishForever();
                } else {
                    activity.finish();
                }
            }
            tmpList.clear();
            return true;
        }
    }

    /**
     * 获取meta值
     *
     * @param context
     * @param cn
     * @param meta
     * @return
     */
    public static String getMetaData(Context context, ComponentName cn, String meta) {
        try {
            ActivityInfo info = context.getPackageManager().getActivityInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(meta);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        return null;
    }

    /**
     * 获取meta值
     *
     * @param context
     * @param cls
     * @param meta
     * @return
     */
    public static String getMetaData(Context context, Class<?> cls, String meta) {
        ComponentName cn = new ComponentName(context, cls);
        return getMetaData(context, cn, meta);
    }
}
