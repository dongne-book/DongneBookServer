package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserRequestDto;
import com.dongnaebook.domain.user.DTO.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.e

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DuplicateUserException("이미 존재하는 이메일입니다.");
        }
    }
}
