package com.dongnaebook.domain.post.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostRequestDTO {
    private String content;
    private LocalDate visitDate;
    private String imageUrl;
    private Boolean isPublic;
    private Long placeId;
    private Long albumId;
}
