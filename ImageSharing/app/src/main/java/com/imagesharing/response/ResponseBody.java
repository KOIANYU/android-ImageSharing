package com.imagesharing.response;

import androidx.annotation.NonNull;

public class ResponseBody {
    /**
     * 业务响应码
     */
    private int code;
    /**
     * 响应提示信息
     */
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseBody{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
