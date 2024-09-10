package com.imagesharingproject.util.api;

public class CollectApiUtil {
    private static final String COLLECT_API = "https://api-store.openguet.cn/api/member/photo/collect";

    public static String CollectUserUrl(Long userId) {  // 获取当前登录用户收藏图文列表 接口
        return COLLECT_API + "?userId=" + userId;
    }

    public static String CollectShareUrl(Long shareId, Long userId) { // 用户对图文分享进行收藏 接口
        return COLLECT_API + "?shareId=" + shareId + "&userId=" + userId;
    }

    public static String CollectCancelUrl(Long collectId) { // 用户取消对图文分享的收藏 接口
        return COLLECT_API + "/cancel?collectId=" + collectId;
    }

}
