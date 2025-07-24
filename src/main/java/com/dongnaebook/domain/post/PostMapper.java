package com.dongnaebook.domain.post;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceMapper;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDetailDTO;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserMapper;

public class PostMapper {
   public static PostResponseDTO toResponseDto(Post post) {
       return PostResponseDTO.builder()
               .id(post.getId())
               .content(post.getContent())
               .visitDate(post.getVisitDate())
               .imageUrl(post.getImageUrl())
               .isPublic(post.getIsPublic())
               .place(PlaceMapper.toResponseDTO(post.getPlace()))
               .createdAt(post.getCreatedAt())
               .createdBy(post.getCreatedBy())
               .modifiedAt(post.getModifiedAt())
               .modifiedBy(post.getModifiedBy())
               .build();
   }

    public static PostResponseDetailDTO toResponseDetailDto(Post post, User createdBy, User modifiedBy, int likeCount) {
        return PostResponseDetailDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .visitDate(post.getVisitDate())
                .imageUrl(post.getImageUrl())
                .isPublic(post.getIsPublic())
                .place(PlaceMapper.toResponseDTO(post.getPlace()))
                .createdAt(post.getCreatedAt())
                .createdBy(UserMapper.toResponseDto(createdBy))
                .modifiedAt(post.getModifiedAt())
                .modifiedBy(UserMapper.toResponseDto(modifiedBy))
                .likeCount(likeCount)
                .build();
    }

   public static Post toEntity(PostRequestDTO postRequestDTO, Place place, Album album) {
         return Post.builder()
                 .content(postRequestDTO.getContent())
                 .visitDate(postRequestDTO.getVisitDate())
                 .imageUrl(postRequestDTO.getImageUrl())
                 .isPublic(postRequestDTO.getIsPublic())
                 .place(place)
                 .album(album)
                 .build();
   }
}
