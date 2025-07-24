package com.dongnaebook.domain.user.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private String token;
    private String refreshToken;
    private String email;
    private String nickname;
}
