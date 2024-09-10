package com.imagesharingproject.util.api;

public class UserApiUtil {
    private static final String API = "https://api-store.openguet.cn/api/member/photo/user";

    public static String UserLoginUrl(String password, String username) {
        return API + "/login?" + "password=" + password + "&" + "username=" + username;
    }

    public static String UserRegisterUrl() {
        return API + "/register";
    }

    public static String UserGetUrl(String username) {
        return API + "/getUserByName?" + "username=" + username;
    }

    public static String UserUpdateUrl() {
        return API + "/update";
    }

}
