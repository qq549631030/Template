package com.hx.template.model.impl.huanxin;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.hx.mvp.Callback;
import com.hx.template.model.IMModel;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 功能说明：环信IMModel
 * 作者：huangx on 2016/12/20 14:46
 * 邮箱：huangx@pycredit.cn
 */

public class HXIMModel implements IMModel {

    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    @Override
    public void register(final String username, final String password, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, password);//同步方法
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(String.valueOf(e.getErrorCode()), e.getDescription());
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    @Override
    public void login(String username, String password, final Callback callback) {
        EMClient.getInstance().login(username, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                Log.d("main", "登录聊天服务器失败！");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(String.valueOf(code), message);
                    }
                });
            }
        });
    }

    /**
     * 登出
     *
     * @param callback
     */
    @Override
    public void logout(final Callback callback) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(String.valueOf(code), message);
                    }
                });
            }
        });
    }

    /**
     * 添加联系人
     *
     * @param username  用户名
     * @param inviteMsg 申请信息
     * @param callback  回调监听
     */
    @Override
    public void addContact(final String username, final String inviteMsg, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(username, inviteMsg);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(String.valueOf(e.getErrorCode()), e.getDescription());
                        }
                    });
                }
            }
        }).start();
    }


    /**
     * 取消操作
     *
     * @param args
     * @return
     */
    @Override
    public boolean cancel(Object... args) {
        return false;
    }
}
