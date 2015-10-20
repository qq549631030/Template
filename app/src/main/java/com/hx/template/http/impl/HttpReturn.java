package com.hx.template.http.impl;

import com.hx.template.entity.enums.ErrorCode;

/**
 * Created by huangxiang on 15/10/20.
 */
public class HttpReturn {
    /**
     * 所有返回数据基对象
     */
    public class BaseReturn {
        /**
         * 状态
         */
        private boolean status;

        private ErrorCode code;
        private String msg;

        public BaseReturn() {
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
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


}
