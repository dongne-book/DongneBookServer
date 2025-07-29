package com.dongnaebook.domain.user.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@Embeddable
@Getter
@EqualsAndHashCode
public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");

    private String value;

    protected Email() {}

    public Email(String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}