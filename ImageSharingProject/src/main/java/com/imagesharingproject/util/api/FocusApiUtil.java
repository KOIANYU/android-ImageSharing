package com.imagesharingproject.util.api;

public class FocusApiUtil {
    private static final String API = "https://api-store.openguet.cn/api/member/photo/focus";

    public static String FocusGetUrl(Long userId) {
        return API + "?userId=" + userId;
    }
    public static String FocusAddUrl(Long focusUserId, Long userId) {
        return API + "?focusUserId=" + focusUserId + "&userId=" + userId;
    }
    public static String FocusCancelUrl(Long focusUserId, Long userId) {
        return API + "/cancel?focusUserId=" + focusUserId + "&userId=" + userId;
    }
}
