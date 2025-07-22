package com.dongnaebook.domain.user;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.user.DTO.PasswordDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
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

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

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

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserMapper.toResponseDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO setUser(String email, UserRequestDTO userRequestDTO) {
        User user = getUser(email);
        user.setProfile(userRequestDTO);
        userRepository.save(user);
        return UserMapper.toResponseDto(user);
    }

    @Transactional
    public Boolean setPassword(String email, PasswordDTO passWordDTO) {
        User user = getUser(email);

        if(passwordEncoder.matches(passWordDTO.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passWordDTO.getConfirmPassword()));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }
}
