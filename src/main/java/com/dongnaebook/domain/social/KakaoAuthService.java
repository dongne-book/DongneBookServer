package com.dongnaebook.domain.social;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import com.dongnaebook.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;;
    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-url}")
    private String kakaoRedirectUrl;

    public Object kakaoLogin(@RequestParam String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUrl);
        params.add("code", code);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        String accessToken = (String) response.getBody().get("access_token");
        System.out.println("카카오 accessToken: " + accessToken);

        String userInfoUrl ="https://kapi.kakao.com/v2/user/me";
        System.out.println("카카오 userInfoUrl: " + userInfoUrl);
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, Map.class);
        Map userInfo = userInfoResponse.getBody();
        System.out.println("카카오 userInfo: " + userInfo);

        Map kakaoAccount = (Map) userInfo.get("kakao_account");
        String kakaoEmail = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        if(kakaoEmail == null){
            throw new RuntimeException("kakao account is null");
        }
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(userInfo);
        if(kakaoUserInfo.getEmail() == null){
            throw new RuntimeException("카카오 계정 이메일이 없습니다.");
        }
        User user = userRepository.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet(() -> {
                    return userRepository.save(
                            User.builder()
                                    .email(kakaoUserInfo.getEmail())
                                    .nickname(kakaoUserInfo.getNickname() != null ? kakaoUserInfo.getNickname() : kakaoUserInfo.getEmail())
                                    .kakaoId(kakaoUserInfo.getKakaoId())
                                    .password(null)
                                    .build()
                    );
                });

        String jwt = jwtTokenProvider.generateToken(user.getEmail());
        return Map.of(
                "token", jwt,
                "user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "nickname", user.getNickname()
                )
        );
    }
}
