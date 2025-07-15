package com.dongnaebook.domain.post;

import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceMapper;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;

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

   public static Post toEntity(PostRequestDTO postRequestDTO, Place place) {
         return Post.builder()
                 .content(postRequestDTO.getContent())
                 .visitDate(postRequestDTO.getVisitDate())
                 .imageUrl(postRequestDTO.getImageUrl())
                 .isPublic(postRequestDTO.getIsPublic())
                 .place(place)
                 .build();
   }
}
