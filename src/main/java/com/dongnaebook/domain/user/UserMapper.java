package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserRequestDto;
import com.dongnaebook.domain.user.DTO.UserResponseDto;

public class UserMapper {
    public static User toEntity(UserRequestDto userRequestDto, String encodedPassword) {
        return User.builder()
                .email(userRequestDto.getEmail())
                .nickname(userRequestDto.getNickname())
                .password(encodedPassword)
                .build();
    }

   public static UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
   }
}
