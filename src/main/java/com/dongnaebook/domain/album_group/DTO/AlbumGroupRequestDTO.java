package com.dongnaebook.domain.album_group.DTO;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumGroupRequestDTO {
    private Long albumId;
}
