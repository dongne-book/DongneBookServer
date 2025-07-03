package com.dongnaebook.domain.user.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String email;
    private String nickname;
    private String password;
}
