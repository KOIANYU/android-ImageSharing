package com.imagesharing.response;

import com.google.gson.annotations.SerializedName;
import com.imagesharing.bean.Data;

public class ApiResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String message;
    @SerializedName("data")
    private Data data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}

