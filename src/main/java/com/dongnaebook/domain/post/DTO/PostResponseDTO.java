package com.dongnaebook.domain.post.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO extends BaseDTO {
    private Long id;
    private String content;
    private LocalDate visitDate;
    private String imageUrl;
    private Boolean isPublic;
    private PlaceResponseDTO place;

}
