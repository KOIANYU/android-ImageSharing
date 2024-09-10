package com.imagesharingproject.util.api;

public class CommentApiUtil {
    public static final String API = "https://api-store.openguet.cn/api/member/photo/comment";

    public static String CommentGetFirstUrl(Long shareId) {
        return API + "/first?shareId=" + shareId;
    }
    public static String CommentAddFirstUrl() {
        return API + "/first";
    }
    public static String CommentGetSecondUrl(Long commentId, Long shareId) {
        return API + "/second?commentId=" + commentId + "&shareId=" + shareId;
    }
    public static String CommentAddSecondUrl() {
        return API + "/second";
    }
}
