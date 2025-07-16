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
}
