package com.dongnaebook.domain.post;

import com.dongnaebook.domain.album_group.AlbumGroup;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAlbum_Id(Long albumId);

    List<Post> findByPlace_Id(Long placeId);

    List<Post> findByPlace_IdAndVisitDateBetween(Long placeId, LocalDate startDate, LocalDate endDate);

    List<Post> findByCreatedBy(String email);

    List<Post> findByAlbum_IdAndCreatedBy(Long albumId, String email);

}
