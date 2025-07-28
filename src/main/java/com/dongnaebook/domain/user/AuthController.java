package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserLoginRequestDTO;
import com.dongnaebook.domain.user.DTO.UserLoginResponseDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
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

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDto) {
        UserLoginResponseDTO userLoginResponseDto = authService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok("로그아웃 하셨습니다.");
    }

    @GetMapping("/email")
    public ResponseEntity<Boolean> email(@RequestParam String email) {
        return ResponseEntity.ok(authService.emailCheck(email));
    }
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> nickname(@RequestParam String nickname) {
        return ResponseEntity.ok(authService.nicknameCheck(nickname));
    }
}
