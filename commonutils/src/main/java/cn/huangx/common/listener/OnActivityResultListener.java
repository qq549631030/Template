package cn.huangx.common.listener;

import android.content.Intent;

/**
 * 监听onActivityResult，传递方向：根结点-->子结点
 * <p>
 * Created by huangx on 2016/7/1.
 */
public interface OnActivityResultListener {
    void onActivityResultDelegate(int requestCode, int resultCode, Intent data);
}
