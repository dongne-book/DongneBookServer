package com.dongnaebook.domain.diary.DTO;

import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDetailDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDate date;
    private List<PostResponseDTO> posts; // 자동 생성 시 해당 일기에 포함된 포스트들
}
