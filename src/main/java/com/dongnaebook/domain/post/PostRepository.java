package com.dongnaebook.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAlbum_Id(Long albumId);

    List<Post> findByPlace_Id(Long placeId);

    List<Post> findByPlace_IdAndVisitDateBetween(Long placeId, LocalDate startDate, LocalDate endDate);

    List<Post> findByCreatedBy(String email);

    List<Post> findByAlbum_IdAndCreatedBy(Long albumId, String email);

    @NonNull
    Page<Post> findAll(@NonNull  Pageable pageable);
}
