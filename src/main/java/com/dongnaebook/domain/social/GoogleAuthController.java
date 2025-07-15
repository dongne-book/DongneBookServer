package com.dongnaebook.domain.social;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth/google")
@CrossOrigin(origins ="http://localhost:3000",allowCredentials = "true")
public class GoogleAuthController {
    private final GoogleAuthService googleAuthService;
    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }
    @GetMapping("/callback")
    public ResponseEntity<?> googleCallback(@RequestParam String code) {
        return ResponseEntity.ok(googleAuthService.googleLogin(code));
    }
}
