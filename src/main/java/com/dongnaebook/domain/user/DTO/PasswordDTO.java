package com.dongnaebook.domain.user.DTO;

import lombok.Data;

@Data
public class PasswordDTO {
    private String password;
    private String confirmPassword;
}
