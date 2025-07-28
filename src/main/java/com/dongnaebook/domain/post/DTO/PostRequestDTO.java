package com.dongnaebook.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostRequestDTO {
    private String content;
    private LocalDate visitDate;
    private List<String> images;
    private Boolean isPublic;
    private Long placeId;
    private Long albumId;
}
