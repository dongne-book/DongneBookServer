package com.dongnaebook.domain.social;

import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import com.dongnaebook.domain.user.vo.Email;
import com.dongnaebook.domain.user.vo.Nickname;
import com.dongnaebook.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import javax.print.attribute.standard.Media;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class GoogleAuthService {
    private final RestTemplate restTemplate = new RestTemplate();

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;;
    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    @Value("${GOOGLE_REDIRECT_URL}")
    private String googleRedirectUrl;

    public Object googleLogin(@RequestParam String code) {
        //Token URL
        String tokenUrl = "https://oauth2.googleapis.com/token";
        //parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUrl);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        //Response Token
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        String accessToken = (String) response.getBody().get("access_token");

        //Response User Info
        String userInfoUrl ="https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userRequest, Map.class);
        Map<String, Object> userInfo = userInfoResponse.getBody();

        GoogleUserInfo googleUserInfo = new GoogleUserInfo(userInfo);
        if(googleUserInfo.getEmail() == null){
            throw new RuntimeException("구글 계정 이메일이 없습니다.");
        }
        User user = userRepository.findByEmail(new Email(googleUserInfo.getEmail()))
                .orElseGet(() -> {
                    return userRepository.save(
                            User.builder()
                                    .email(new Email(googleUserInfo.getEmail()))
                                    .nickname(googleUserInfo.getNickname() != null ? new Nickname(googleUserInfo.getNickname()) : new Nickname(googleUserInfo.getEmail()))
                                    .googleId(googleUserInfo.getGoogleId())
                                    .adminLevel(1)
                                    .password(null)
                                    .build()
                    );
                });
        List<String> roles = List.of("ROLE_USER");
        String jwt = jwtTokenProvider.generateToken(user.getEmail().toString(), roles);
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
