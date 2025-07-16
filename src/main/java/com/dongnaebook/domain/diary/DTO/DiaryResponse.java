package com.dongnaebook.domain.diary.DTO;

import java.time.LocalDateTime;

public class DiaryResponse {
    private Long id;
    private String title;
    private String content;
    private String region;
    private String keyword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DiaryResponse() {}
}
