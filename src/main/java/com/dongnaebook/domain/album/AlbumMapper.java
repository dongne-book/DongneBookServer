package com.dongnaebook.domain.album;

import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;

public class AlbumMapper {
   public static AlbumResponseDTO toResponseDto(Album album) {
       return AlbumResponseDTO.builder()
               .id(album.getId())
               .name(album.getName())
               .startDate(album.getStartDate())
               .endDate(album.getEndDate())
               .createdBy(album.getCreatedBy())
               .createdAt(album.getCreatedAt())
               .modifiedBy(album.getModifiedBy())
               .modifiedAt(album.getModifiedAt())
               .build();
   }

   public static Album toAlbum(AlbumRequestDTO albumRequestDto) {
         return Album.builder()
                 .name(albumRequestDto.getName())
                 .startDate(albumRequestDto.getStartDate())
                    .endDate(albumRequestDto.getEndDate())
                .build();
   }
}
