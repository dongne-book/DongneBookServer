package com.dongnaebook.domain.user;

import com.dongnaebook.domain.admin.AdminLevel;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.domain.user.vo.BirthDate;
import com.dongnaebook.domain.user.vo.Email;
import com.dongnaebook.domain.user.vo.Nickname;
import com.dongnaebook.domain.user.vo.Password;
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

//    @Column(nullable=false, unique=true)
//    private String email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = true))
    private Password password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname", nullable = true))
    private Nickname nickname;

    @Column(nullable=true)
    private String name;

    @Column(nullable=true)
    private String phoneNumber;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "birthdate", nullable = true))
    private BirthDate birthdate;

    @Column(unique=true)
    private String kakaoId;

    @Column(unique = true)
    private String googleId;

    @Column(nullable = false)
    private Integer adminLevel = 1;

    public void setProfile(UserRequestDTO userRequestDTO) {
        this.nickname = new Nickname(userRequestDTO.getNickname());
        this.name = userRequestDTO.getName();
        this.phoneNumber = userRequestDTO.getPhoneNumber();
        this.birthdate = new BirthDate(userRequestDTO.getBirthdate());
    }
    public void passwordChange(String password) {
        this.password = new Password(password);
    }
}