package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.domain.user.vo.BirthDate;
import com.dongnaebook.domain.user.vo.Email;
import com.dongnaebook.domain.user.vo.Nickname;
import com.dongnaebook.domain.user.vo.Password;

import java.time.LocalDate;

public class UserMapper {
    public static User toEntity(UserRequestDTO userRequestDto, String encodedPassword) {
        return User.builder()
                .email(new Email(userRequestDto.getEmail()))
                .nickname(new Nickname(userRequestDto.getNickname()))
                .password(new Password(encodedPassword))
                .birthdate(new BirthDate(userRequestDto.getBirthdate()))
                .name(userRequestDto.getName())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .adminLevel(1)
                .build();
    }

    public static User toEntityByAdmin(UserRequestDTO userRequestDto, String encodedPassword) {
        return User.builder()
                .email(new Email(userRequestDto.getEmail()))
                .nickname(new Nickname(userRequestDto.getNickname()))
                .password(new Password(encodedPassword))
                .birthdate(new BirthDate(userRequestDto.getBirthdate()))
                .name(userRequestDto.getName())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .adminLevel(2)
                .build();
    }

   public static UserResponseDTO toResponseDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail().toString())
                .nickname(user.getNickname().toString())
                .birthdate(user.getBirthdate().toString())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
   }
}
