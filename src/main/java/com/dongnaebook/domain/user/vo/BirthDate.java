package com.dongnaebook.domain.user.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
@EqualsAndHashCode
public class BirthDate {

    private LocalDate value;

    protected BirthDate() {}

    public BirthDate(LocalDate value) {
        if (value == null || value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("유효하지 않은 생년월일입니다.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}