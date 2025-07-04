package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserLoginRequestDto;
import com.dongnaebook.domain.user.DTO.UserLoginResponseDto;
import com.dongnaebook.domain.user.DTO.UserRequestDto;
import com.dongnaebook.domain.user.DTO.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto request) {
        UserResponseDto userResponseDto = userService.signup(request);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }
}
