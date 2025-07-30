package com.dongnaebook.domain.user;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.user.DTO.PasswordDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.domain.user.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserMapper.toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserMapper.toResponseDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO setUser(String email, UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setProfile(userRequestDTO);
        userRepository.save(user);
        return UserMapper.toResponseDto(user);
    }

    @Transactional
    public Boolean setPassword(String email, PasswordDTO passWordDTO) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new NotFoundException("User not found"));

        if(user.getPassword().matches(passWordDTO.getPassword(), passwordEncoder)) {
            user.passwordChange(passwordEncoder.encode(passWordDTO.getConfirmPassword()));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }
}
