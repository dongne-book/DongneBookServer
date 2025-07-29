package com.dongnaebook.domain.user.DTO;

import com.dongnaebook.domain.admin.AdminLevel;
import com.dongnaebook.domain.user.vo.BirthDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String nickname;
    private String password;
    private String name;
    private String phoneNumber;
    private LocalDate birthdate;
}
