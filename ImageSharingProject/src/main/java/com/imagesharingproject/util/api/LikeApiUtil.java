package com.imagesharingproject.util.api;

public class LikeApiUtil {
    private static final String API = "https://api-store.openguet.cn/api/member/photo/like";

    public static String LikeGetUrl(Long userId) {
        return API + "?userId=" + userId;
    }
    public static String LikeAddUrl(Long shareId, Long userId) {
        return API + "?shareId=" + shareId + "&userId=" + userId;
    }
    public static String LikeCancelUrl(Long likeId) {
        return API + "/cancel?likeId=" + likeId;
    }
}
