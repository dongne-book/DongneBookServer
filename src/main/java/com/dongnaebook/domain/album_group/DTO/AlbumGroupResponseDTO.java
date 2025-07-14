package com.dongnaebook.domain.album_group.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
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
public class AlbumGroupResponseDTO extends BaseDTO {
    private Long id;

    private UserResponseDTO user;
    private AlbumResponseDTO album;

}
