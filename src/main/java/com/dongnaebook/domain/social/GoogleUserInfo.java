package com.dongnaebook.domain.social;

import lombok.Data;
import java.util.Map;

@Data
public class GoogleUserInfo {
    private final String googleId;
    private final String email;
    private final String nickname;

    public GoogleUserInfo(Map<String, Object> userInfo) {
        this.googleId = String.valueOf(userInfo.get("id"));
        this.email = (String) userInfo.get("email");
        this.nickname = (String) userInfo.get("name");
    }

//    public GoogleUserInfo(Map userInfo) {
//        // googleId
//        this.googleId = String.valueOf(userInfo.get("id"));
//
//        // email, nickname
//        String email = null, nickname = null;
//        Map googleAccount = (Map) userInfo.get("google_account");
//        if (googleAccount != null) {
//            email = (String) googleAccount.get("email");
//            Map profile = (Map) googleAccount.get("profile");
//            if (profile != null) {
//                nickname = (String) profile.get("nickname");
//            }
//        }
//        this.email = email;
//        this.nickname = nickname;
//    }
}
