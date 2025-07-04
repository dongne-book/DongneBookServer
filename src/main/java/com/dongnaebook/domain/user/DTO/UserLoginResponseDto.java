package com.dongnaebook.domain.user.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginResponseDto {
    private String token;
    private String email;
    private String nickname;
}
