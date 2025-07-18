package com.dongnaebook.domain.diary.DTO;

import com.dongnaebook.domain.diary.Diary;
import com.fasterxml.jackson.core.JsonToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class DiaryResponse {
    private Long id;
    private String title;
    private String content;
    private String region;
    private String keyword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DiaryResponse from(Diary diary) {
        return new DiaryResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getRegion(),
                diary.getKeywords(),
                diary.getCreatedAt(),
                diary.getUpdatedAt()
        );
    }
}
