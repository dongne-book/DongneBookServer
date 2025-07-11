package com.dongnaebook.domain.social;

import com.dongnaebook.domain.user.User;
import lombok.Data;
import java.util.Map;

@Data
public class KakaoUserInfo {
    private final String kakaoId;
    private final String email;
    private final String nickname;

    public KakaoUserInfo(Map userInfo) {
        // kakaoId
        this.kakaoId = String.valueOf(userInfo.get("id"));

        // email, nickname
        String email = null, nickname = null;
        Map kakaoAccount = (Map) userInfo.get("kakao_account");
        if (kakaoAccount != null) {
            email = (String) kakaoAccount.get("email");
            Map profile = (Map) kakaoAccount.get("profile");
            if (profile != null) {
                nickname = (String) profile.get("nickname");
            }
        }
        this.email = email;
        this.nickname = nickname;
    }
}
