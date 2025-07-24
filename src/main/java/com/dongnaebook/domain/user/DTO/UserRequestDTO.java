package com.dongnaebook.domain.user.DTO;

import com.dongnaebook.domain.admin.AdminLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String nickname;
    private String password;
    private Integer adminLevel;
}
