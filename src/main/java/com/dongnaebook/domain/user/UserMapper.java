package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;

public class UserMapper {
    public static User toEntity(UserRequestDTO userRequestDto, String encodedPassword) {
        return User.builder()
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .password(encodedPassword)
                .build();
    }

   public static UserResponseDTO toResponseDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
   }

    public static User toEntityByResponse(UserResponseDTO userResponseDTO) {
        return User.builder()
                .email(userResponseDTO.getEmail())
                .nickname(userResponseDTO.getNickname())
                .build();
    }
}
