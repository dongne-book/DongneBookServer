package com.dongnaebook.domain.post.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDetailDTO {
    private Long id;
    private String content;
    private LocalDate visitDate;
    private String imageUrl;
    private Boolean isPublic;
    private PlaceResponseDTO place;

    private UserResponseDTO createdBy;
    private LocalDateTime createdAt;
    private UserResponseDTO modifiedBy;
    private LocalDateTime modifiedAt;

    private int likeCount;

}
