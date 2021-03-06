package com.hx.template.http;

import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;

/**
 * Created by huangxiang on 15/10/20.
 */
public class HttpReturn {
    /**
     * 所有返回数据基对象
     */
    public static class BaseReturn {

        public static final int STATUS_SUCCESS = 1;
        public static final int STATUS_FAIL = 0;
        /**
         * 状态
         */
        private int status;

        private ErrorCode code;
        private String msg;

        public BaseReturn() {
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ErrorCode getCode() {
            return code;
        }

        public void setCode(ErrorCode code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

    /**
     * 注册返回
     *
     * @author huangxiang
     */
    public static class RegisterReturn extends BaseReturn {
        private User data;

        public RegisterReturn() {
        }

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }

    /**
     * 登录返回
     */
    public static class LoginReturn extends BaseReturn {
        private User data;

        public LoginReturn() {
            super();
        }

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }

    /**
     * 修改密码返回
     *
     * @author huangxiang
     */
    public static class ModifyPwdReturn extends BaseReturn {
        private User data;

        public ModifyPwdReturn() {
            super();
        }

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }
}
