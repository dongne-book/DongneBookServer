package com.dongnaebook.domain.user;

import com.dongnaebook.domain.admin.AdminLevel;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String nickname;

    @Setter
    @Column(nullable=true)
    private String password;

    @Column(unique=true)
    private String kakaoId;

    @Column(unique = true)
    private String googleId;

    @Column(nullable = false)
    private Integer adminLevel = 1;

    public void setProfile(UserRequestDTO userRequestDTO) {
        this.email = userRequestDTO.getEmail();
        this.nickname = userRequestDTO.getNickname();
    }
}