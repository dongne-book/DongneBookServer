package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO request) {
        UserResponseDTO userResponseDto = authService.signup(request);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<UserResponseDTO> signupAdmin(@RequestBody UserRequestDTO request) {
        UserResponseDTO userResponseDto = authService.signupAdmin(request);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDto) {
        UserLoginResponseDTO userLoginResponseDto = authService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok("로그아웃 하셨습니다.");
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserLoginResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO request) {
        UserLoginResponseDTO response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
