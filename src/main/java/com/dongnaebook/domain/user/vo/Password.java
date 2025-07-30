package com.dongnaebook.domain.user.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@EqualsAndHashCode
public class Password {
    private String value;

    protected Password() {}

    // 인코딩된 비밀번호만 받음
    public Password(String encodedPassword) {
        this.value = encodedPassword;
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }

    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.value);
    }
}