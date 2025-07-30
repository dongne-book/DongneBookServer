package com.dongnaebook.domain.user.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String birthdate;
}
