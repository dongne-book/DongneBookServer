package com.dongnaebook.domain.album.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseDTO extends BaseDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

}
