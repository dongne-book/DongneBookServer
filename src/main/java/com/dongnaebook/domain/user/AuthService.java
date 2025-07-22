package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserLoginRequestDTO;
import com.dongnaebook.domain.user.DTO.UserLoginResponseDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dongnaebook.common.exception.DuplicateUserException;

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
        User  user = User.builder()
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
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
                .orElseThrow(()->new RuntimeException("존재하지 않는 이메일입니다."));

        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        //기능만 확인하고 이메일하고 비밀번호 둘 다 안내메시지 같은걸로 묶기

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return UserLoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
