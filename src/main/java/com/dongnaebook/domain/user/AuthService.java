package com.dongnaebook.domain.user;

import com.dongnaebook.domain.admin.AdminLevel;
import com.dongnaebook.domain.user.DTO.UserLoginRequestDTO;
import com.dongnaebook.domain.user.DTO.UserLoginResponseDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dongnaebook.common.exception.DuplicateUserException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponseDTO signup(UserRequestDTO userRequestDto){
        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DuplicateUserException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .adminLevel(userRequestDto.getAdminLevel())
                .build();
        User saved = userRepository.save(user);
        return UserResponseDTO.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .nickname(saved.getNickname())
                .build();
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDto){
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElse(null);

        if(user == null || !passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        List<String> roles = getUserRoles(user);

        String token = jwtTokenProvider.generateToken(user.getEmail(), roles);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
        return UserLoginResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    public UserLoginResponseDTO refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 아닙니다.");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        List<String> roles = getUserRoles(user);
        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return UserLoginResponseDTO.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    private List<String> getUserRoles(User user) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        if(user.getAdminLevel() == 2){
            roles.add("ROLE_ADMIN");
        }
        return roles;
    }
}
