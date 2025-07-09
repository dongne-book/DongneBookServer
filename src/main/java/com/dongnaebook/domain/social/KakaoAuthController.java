package com.dongnaebook.domain.social;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth/kakao")
@CrossOrigin(origins ="http://localhost:3000",allowCredentials = "true")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;
    public KakaoAuthController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(kakaoAuthService.kakaoLogin(code));
    }
}
