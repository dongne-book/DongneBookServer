package com.dongnaebook.domain.album;

import com.dongnaebook.domain.album.DTO.AlbumDetailDTO;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.post.DTO.PostDetailDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static AlbumDetailDTO toDetailDTO(Album album, List<PostDetailDTO> posts) {
        return AlbumDetailDTO.builder()
                .id(album.getId())
                .name(album.getName())
                .startDate(album.getStartDate())
                .endDate(album.getEndDate())
                .posts(posts)
                .createdBy(album.getCreatedBy())
                .createdAt(album.getCreatedAt())
                .modifiedBy(album.getModifiedBy())
                .modifiedAt(album.getModifiedAt())
                .build();
    }

   public static Album toEntity(AlbumRequestDTO albumRequestDto) {
         return Album.builder()
                 .name(albumRequestDto.getName())
                 .startDate(albumRequestDto.getStartDate())
                    .endDate(albumRequestDto.getEndDate())
                .build();
   }
}
