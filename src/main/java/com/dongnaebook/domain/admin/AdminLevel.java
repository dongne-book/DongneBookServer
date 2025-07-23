package com.dongnaebook.domain.admin;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AdminLevel {
    USER(1),
    ADMIN(2);

    private final int level;

    AdminLevel(int level) {
        this.level = level;
    }

    public static AdminLevel fromLevel(int level) {
        return Arrays.stream(AdminLevel.values())
                .filter(l -> l.level == level)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin level: " + level));
    }
}
