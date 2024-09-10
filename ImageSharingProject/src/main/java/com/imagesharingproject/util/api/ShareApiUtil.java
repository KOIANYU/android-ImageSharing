package com.imagesharingproject.util.api;

import com.imagesharingproject.pojo.Share;

public class ShareApiUtil {
    private static final String API = "https://api-store.openguet.cn/api/member/photo/share";

    public static String ShareGetAllUrl(Long userId) {
        return API + "?userId=" + userId;
    }
    public static String ShareAddUrl() {
        return API + "/add";
    }
    public static String ShareChangeUrl() {
        return API + "/change";
    }
    public static String ShareDeleteUrl(Long shareId, Long userId) {
        return API + "/delete?shareId=" + shareId + "&userId=" + userId;
    }
    public static String ShareGetDetailUrl(Long shareId, Long userId) {
        return API + "/detail?shareId=" + shareId + "&userId=" + userId;
    }
    public static String ShareGetMyselfUrl(Long userId) {
        return API + "/myself?userId=" + userId;
    }

    public static String ShareGetSaveUrl(Long userId) {
        return API + "/save?userId=" + userId;
    }
    public static String ShareSaveUrl() {
        return API + "/save";
    }
}
