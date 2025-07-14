package com.dongnaebook.domain.album_group;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumMapper;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.album_group.DTO.AlbumGroupRequestDTO;
import com.dongnaebook.domain.album_group.DTO.AlbumGroupResponseDTO;
import com.dongnaebook.domain.user.UserMapper;

public class AlbumGroupMapper {
   public static AlbumGroupResponseDTO toResponseDTO(AlbumGroup albumGroup) {
        return AlbumGroupResponseDTO.builder()
                .id(albumGroup.getId())
                .user(UserMapper.toResponseDto(albumGroup.getUser()))
                .album(AlbumMapper.toResponseDto(albumGroup.getAlbum()))
                .createdAt(albumGroup.getCreatedAt())
                .modifiedAt(albumGroup.getModifiedAt())
                .build();

   }
}
