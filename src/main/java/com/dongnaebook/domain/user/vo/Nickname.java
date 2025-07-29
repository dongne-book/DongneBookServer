package com.dongnaebook.domain.user.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class Nickname {
    private static final int MAX_LENGTH = 20;

    private String value;

    protected Nickname() {}

    public Nickname(String value) {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("유효하지 않은 닉네임입니다.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
